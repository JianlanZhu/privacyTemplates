package edu.cmu.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.io.ByteStreams;
import edu.cmu.db.dao.ConversationDAO;
import edu.cmu.db.dao.MessageDAO;
import edu.cmu.db.dao.RequestDAO;
import edu.cmu.db.entities.Conversation;
import edu.cmu.db.entities.Message;
import edu.cmu.db.entities.Request;
import edu.cmu.db.entities.Result;
import edu.cmu.db.entities.User;
import edu.cmu.db.enums.CaseType;
import edu.cmu.db.enums.RequestState;
import edu.cmu.resources.interaction.GenerateRequestInput;
import edu.cmu.resources.views.ConversationInfoView;
import edu.cmu.resources.views.GenerateRequestView;
import edu.cmu.resources.views.ListAllRequestsForLeoView;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.views.View;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Blob;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used for registering endpoints regarding requests.
 */
@Path("/request")
public class RequestResource {

    /**
     * Responsible for accessing the database.
     */
    private RequestDAO requestDAO;
    private ConversationDAO conversationDAO;
    private MessageDAO messageDAO;

//    public RequestResource(RequestDAO requestDAO) {
//        this.requestDAO = requestDAO;
//    }

    public RequestResource(RequestDAO requestDAO, ConversationDAO conversationDAO, MessageDAO messageDAO) {
        this.requestDAO = requestDAO;
        this.conversationDAO = conversationDAO;
        this.messageDAO = messageDAO;
    }

    /**
     * Respsonsible for backend mediation. Should check fo data formats etc.. NOT for business logic!
     *
     * @param input the incoming request.
     * @return true if data format is valid, false otherwise.
     */
    private static void checkInputValidity(GenerateRequestInput input) {
        if (input.getSuspectUserName() == null) {
            throw new BadRequestException("Invalid user name.");
        }

        if (input.getCaseID() <= 0) {
            // TODO maybe replace with actual check whether case is present in data base?
            throw new BadRequestException("Invalid case ID.");
        }

        try {
            // for now, case type can either be null, or oe of the values specified in the enum
            if (input.getCaseType() != null) {
                // will throw exception if case type is invalid
                CaseType.valueOf(input.getCaseType().toUpperCase());
            }
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid Case Type.");
        }

        //TODO additional checks
    }

    /**
     * Endpoint for creating a new request.
     *
     * @param generateRequestInput Parameters for the generated request
     * @param fileField            The warrant file
     * @return the generated request including generated fields like ID.
     */
    @POST
    @Path("/requestForm")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("LAW_ENFORCEMENT_OFFICER")
    @UnitOfWork
    @Timed
    public Request generateRequest(@Auth User user,
                                   GenerateRequestInput parsedInput) {

        checkInputValidity(parsedInput);

        Blob warrantBlob = null;
       /* if (fileField != null) {
            InputStream warrantFileInputStream = fileField.getValueAs(InputStream.class);

            try {
                byte[] warrantBytes = ByteStreams.toByteArray(warrantFileInputStream);
                warrantBlob = new SerialBlob(warrantBytes);
            } catch (IOException e) {
                throw new BadRequestException("File not readable.");
            } catch (SQLException e) {
                throw new InternalServerErrorException("Failed to handle uploaded warrant.");
            }
        }
        */
        try {
            Request request = new Request(user, parsedInput.getCaseID(), parsedInput.getCaseType(), parsedInput.getSuspectUserName(), parsedInput.getLastName(), parsedInput.getFirstName(), parsedInput.getMiddleName(), parsedInput.getEmail(), parsedInput.getPhoneNumber(), Instant.EPOCH, Instant.EPOCH, parsedInput.isContactInformationRequested(), parsedInput.isMiniFeedRequested(), parsedInput.isStatusHistoryRequested(), parsedInput.isSharesRequested(), parsedInput.isNotesRequested(), parsedInput.isWallPostingsRequested(), parsedInput.isFriendListRequested(), parsedInput.isVideosRequested(), parsedInput.isGroupsRequested(), parsedInput.isPastEventsRequested(), parsedInput.isFutureEventsRequested(), parsedInput.isPhotosRequested(), parsedInput.isPrivateMessagesRequested(), parsedInput.isGroupInfoRequested(), parsedInput.isIPLogRequested(), null, null, parsedInput.getCommunicantsUserNames(), parsedInput.getKeywords(), parsedInput.getKeywordCategories(), parsedInput.getLocationZipCode(), warrantBlob, RequestState.PENDING);
            request = requestDAO.persistNewRequest(request);
            return request;
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Malformed Request");
        }

    }

    @GET
    @RolesAllowed("LAW_ENFORCEMENT_OFFICER")
    @Path("/requestForm")
    public View showGenerateRequestView() {
        return new GenerateRequestView();
    }

    @GET
    @RolesAllowed("LAW_ENFORCEMENT_OFFICER")
    @Path("/all")
    @UnitOfWork
    public View listAllRequestsForLeo(@Auth User user) {
        return new ListAllRequestsForLeoView(requestDAO.findAllForUser(user.getUserID()));
    }

    @GET
    @RolesAllowed("LAW_ENFORCEMENT_OFFICER")
    @UnitOfWork
    @Path("/{id}")
    public View getRequestDetails(@PathParam("id") int id) {
        List<Conversation> conversations = requestDAO.findById(id).map(Request::getResult).map(Result::getConversations).orElse(new ArrayList<>());
        return new ConversationInfoView(conversations);
    }

    @POST
    @RolesAllowed("LAW_ENFORCEMENT_OFFICER")
    @UnitOfWork
    @Path("/{id}/conversations")
    public View getfilteredConversationInfo(@PathParam("id") int id,
                                            @FormDataParam("sender") FormDataBodyPart senderName) {
        String name = null;
        if (senderName != null) {
            name = senderName.getValueAs(String.class);
        }
        List<Conversation> conversations = conversationDAO.findByParticipant(id, name);
        return new ConversationInfoView(conversations);
    }
}

