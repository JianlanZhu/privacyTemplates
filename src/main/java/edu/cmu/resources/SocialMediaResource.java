package edu.cmu.resources;

import com.codahale.metrics.annotation.Timed;
import edu.cmu.db.dao.ConversationDAO;
import edu.cmu.db.dao.MessageDAO;
import edu.cmu.db.dao.Parser;
import edu.cmu.db.dao.RequestDAO;
import edu.cmu.db.dao.ResultDAO;
import edu.cmu.db.entities.Conversation;
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

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@Path("/socialMedia")
@RolesAllowed("SOCIAL_MEDIA_EMPLOYEE")
public class SocialMediaResource {

    private RequestDAO requestDAO;
    private ResultDAO resultDAO;
    private ConversationDAO conversationDAO;
    private MessageDAO messageDAO;
    private Random resultIDGen = new Random();

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

        if (fileField != null) {
            InputStream warrantFileInputStream = new BufferedInputStream(fileField.getValueAs(InputStream.class));
            try {
                // getNextEntry returns null if the InputStream is not a zip file
                if (new ZipInputStream(warrantFileInputStream).getNextEntry() == null) {
                    throw new BadRequestException("Uploaded file was not a zip file!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        requestId.setMediaType(MediaType.TEXT_PLAIN_TYPE);
        int requestIdNumber = Integer.parseInt(requestId.getValue());
        comment.setMediaType(MediaType.TEXT_PLAIN_TYPE);


        Optional<Request> requestOptional = requestDAO.findById(requestIdNumber);
        if (requestOptional.isPresent()) {
            Request request = requestOptional.get();
            if (request.getStatus() == null || request.getStatus().equals(RequestState.PENDING.name())) {
                boolean success = requestDAO.updateStatus(request.getRequestID(), RequestState.ANSWERED);
                if (!success) {
                    throw new NotFoundException();
                }
                // TODO handle uploaded data participants
                if (fileField != null) {
                    int SMEUserID = user.getUserID();
                    // find a result ID
                    Result result = new Result();
                    result.setSMEUserID(SMEUserID);
                    // hard code, just for testing
                    result.setRetentionID(1);
//                    result.setResultID(1); // just for testing
                    // store to db
                    result = resultDAO.persistNewResult(result);

//                    int resultID = result.getResultID();
                    // begin parsing
                    InputStream dataZipFileInputStream = fileField.getValueAs(InputStream.class);
                    Parser parser = new Parser(conversationDAO, messageDAO, result.getResultID());
                    try {
                        // unzip and parse
                        parser.parseProfile(dataZipFileInputStream);
                    } catch (IOException e) {
                        // if parse failed
                        e.printStackTrace();
                        resultDAO.deleteResultByID(result.getResultID());
                    }
                    // delete files under data folder

                    java.nio.file.Path path = Paths.get(Parser.getDestinationPath());
                    try {
                        Parser.deleteFileOrFolder(path);
                    } catch (IOException e) {
                        System.out.println("delete file error!");
                        e.printStackTrace();
                    }
                    System.out.println("parse successfully!");
                }
            } else {
                throw new BadRequestException("Request has already been answered or rejected.");
            }
        } else {
            throw new BadRequestException("Request ID invalid");
        }
        System.out.println("upload over");
    }

    @GET
    @Path("DataUploadForm")
    @UnitOfWork
    @Timed
    public UploadDataFormView getUploadForm() {
        return new UploadDataFormView();
    }

}
