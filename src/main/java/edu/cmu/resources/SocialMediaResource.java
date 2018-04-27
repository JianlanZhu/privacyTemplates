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
import edu.cmu.resources.views.SmeHomeView;
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
    public View uploadData(@Auth User user,
                           @FormDataParam("submit") String actionTaken,
                           @FormDataParam("data") final FormDataBodyPart fileField,
                           @FormDataParam("caseID") FormDataBodyPart requestId,
                           @FormDataParam("comment") FormDataBodyPart comment) {

        requestId.setMediaType(MediaType.TEXT_PLAIN_TYPE);
        int requestIdNumber = requestId.getValueAs(Integer.class);
        comment.setMediaType(MediaType.TEXT_PLAIN_TYPE);

        Optional<Request> requestOptional = requestDAO.findById(requestIdNumber);
        if (!requestOptional.isPresent()) {
            throw new BadRequestException("Request ID invalid");
        }

        Request request = requestOptional.get();
        if (request.getStatus() != null && !request.getStatus().equals(RequestState.PENDING.name())) {
            throw new BadRequestException("Request has already been dealt with.");
        }

        boolean success = false;
        if (actionTaken.equalsIgnoreCase("reject")) {
            success = requestDAO.updateStatus(request.getRequestID(), RequestState.REJECTED);
        } else if (actionTaken.equalsIgnoreCase("submit")) {
            if (fileField == null) {
                throw new BadRequestException("no data uploaded.");
            }

            InputStream dataZipFileInputStream = fileField.getValueAs(InputStream.class);
            checkIfZipFile(dataZipFileInputStream);

            // handle uploaded data
            Result result = createNewResult(user, request);
            request.setResult(result);

            parseUploadedData(result, fileField.getValueAs(InputStream.class));

            success = requestDAO.updateStatus(request.getRequestID(), RequestState.ANSWERED);
        }

        if (!success) {
            LOG.warn(String.format("Could not update status of request %s after data upload / rejection.", requestIdNumber));
        }
        LOG.info("upload over");

        return new SmeHomeView(requestDAO.findAll());
    }

    private Result createNewResult(User user, Request request) {
        Result result = new Result();
        result.setSMEUser(user);
        result.setRequest(request);
        // TODO: This is the place to set a retention policy id (e.g. depending on caseType) as soon as retention policies are incorporated.
        result.setRetentionID(1);
        result = resultDAO.persistNewResult(result);

        return result;
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
            throw new BadRequestException("Uploaded file corrupt.");
        }
    }

    private void parseUploadedData(Result result, InputStream dataZipFileInputStream) {
        Parser parser = new Parser(conversationDAO, messageDAO, result);
        try {
            parser.parseProfile(dataZipFileInputStream);
            LOG.info("parsing successfully completed!");
        } catch (IOException e) {
            e.printStackTrace();
            resultDAO.deleteResultByID(result.getResultID());
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
