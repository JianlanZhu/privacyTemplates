package edu.cmu.resources;

import com.codahale.metrics.annotation.Timed;
import edu.cmu.core.util.Parser;
import edu.cmu.db.dao.ConversationDAO;
import edu.cmu.db.dao.MessageDAO;
import edu.cmu.db.dao.RequestDAO;
import edu.cmu.db.dao.ResultDAO;
import edu.cmu.db.entities.Request;
import edu.cmu.db.entities.Result;
import edu.cmu.db.entities.User;
import edu.cmu.db.enums.RequestState;
import edu.cmu.resources.views.ListAllRequestsForSmeView;
import edu.cmu.resources.views.UploadDataFormView;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.views.View;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipInputStream;

@Path("/socialMedia")
@RolesAllowed("SOCIAL_MEDIA_EMPLOYEE")
public class SocialMediaResource {

    private static Logger LOG = LoggerFactory.getLogger(SocialMediaResource.class);

    private RequestDAO requestDAO;
    private ResultDAO resultDAO;
    private ConversationDAO conversationDAO;
    private MessageDAO messageDAO;

    public SocialMediaResource(RequestDAO requestDAO, ResultDAO resultDAO,
                               ConversationDAO conversationDAO, MessageDAO messageDAO) {
        this.requestDAO = requestDAO;
        this.resultDAO = resultDAO;
        this.conversationDAO = conversationDAO;
        this.messageDAO = messageDAO;
    }

    @GET
    @Path("/requests")
    @UnitOfWork
    public View listAllUnansweredRequests() {
        List<Request> pendingRequests = requestDAO.findAllWithStatus(RequestState.PENDING);
        List<Request> rejectedRequests = requestDAO.findAllWithStatus(RequestState.REJECTED);
        List<Request> answeredRequests = requestDAO.findAllWithStatus(RequestState.ANSWERED);
        List<Request> closedRequests = requestDAO.findAllWithStatus(RequestState.CLOSED);
        return new ListAllRequestsForSmeView(pendingRequests, rejectedRequests, answeredRequests, closedRequests);
    }

    @POST
    @Path("DataUploadForm")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @UnitOfWork
    @Timed
    public void uploadData(@Auth User user,
                           @FormDataParam("data") final FormDataBodyPart fileField,
                           @FormDataParam("caseID") FormDataBodyPart requestId,
                           @FormDataParam("comment") FormDataBodyPart comment) {
        InputStream dataZipFileInputStream = fileField.getValueAs(InputStream.class);
        checkIfZipFile(dataZipFileInputStream);

        requestId.setMediaType(MediaType.TEXT_PLAIN_TYPE);
        int requestIdNumber = requestId.getValueAs(Integer.class);
        comment.setMediaType(MediaType.TEXT_PLAIN_TYPE);

        Optional<Request> requestOptional = requestDAO.findById(requestIdNumber);
        if (requestOptional.isPresent()) {
            Request request = requestOptional.get();
            if (request.getStatus() == null || request.getStatus().equals(RequestState.PENDING.name())) {
                // handle uploaded data
                Result result = new Result();
                result.setSMEUser(user);
                // TODO: This is the place to set a retention policy id (e.g. depending on caseType) as soon as retention policies are incorporated.

                result = resultDAO.persistNewResult(result);
                request.setResult(result);
                result.setRequest(request);

                parseUploadedData(result, dataZipFileInputStream);

                LOG.info("parse successfully!");
                boolean success = requestDAO.updateStatus(request.getRequestID(), RequestState.ANSWERED);
                if (!success) {
                    LOG.warn(String.format("Could not update status of request %s after data upload.", requestIdNumber));
                }
            } else {
                throw new BadRequestException("Request has already been dealt with.");
            }
        } else {
            throw new BadRequestException("Request ID invalid");
        }
        LOG.info("upload over");
    }

    private void checkIfZipFile(InputStream uploadedFile) {
        if (uploadedFile == null) {
            throw new BadRequestException("File upload failed");
        }

        uploadedFile = new BufferedInputStream(uploadedFile);
        try {
            // getNextEntry returns null if the InputStream is not a zip file
            if (new ZipInputStream(uploadedFile).getNextEntry() == null) {
                throw new BadRequestException("Uploaded file was not a zip file!");
            }
        } catch (IOException e) {
            throw new BadRequestException("Uploaded file was not a zip file!");
        }
    }

    private void parseUploadedData(Result result, InputStream dataZipFileInputStream) {
        Parser parser = new Parser(conversationDAO, messageDAO, result);
        try {
            parser.parseProfile(dataZipFileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            resultDAO.deleteResultByID(result.getResultID());
        }
        // delete files under data folder
        java.nio.file.Path path = Paths.get(Parser.getDestinationPath());
        try {
            Parser.deleteFileOrFolder(path);
        } catch (IOException e) {
            LOG.warn("delete file error!");
            e.printStackTrace();
        }
    }

    @GET
    @Path("DataUploadForm")
    @UnitOfWork
    @Timed
    public UploadDataFormView getUploadForm(@QueryParam("requestId") int requestId) {
        return new UploadDataFormView(requestId);
    }

}
