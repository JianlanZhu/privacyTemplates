package edu.cmu.resources;

import com.codahale.metrics.annotation.Timed;
import edu.cmu.db.dao.RequestDAO;
import edu.cmu.db.entities.Conversation;
import edu.cmu.db.entities.Request;
import edu.cmu.db.entities.Result;
import edu.cmu.db.entities.User;
import edu.cmu.db.enums.CaseType;
import edu.cmu.db.enums.RequestState;
import edu.cmu.resources.interaction.GenerateRequestInput;
import edu.cmu.resources.views.GenerateRequestView;
import edu.cmu.resources.views.NotAnsweredView;
import edu.cmu.resources.views.RequestDetailsView;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.views.View;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Blob;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class is used for registering endpoints regarding requests.
 */
@Path("/request")
public class RequestResource {

    /**
     * Responsible for accessing the database.
     */
    private RequestDAO requestDAO;

    public RequestResource(RequestDAO requestDAO) {
        this.requestDAO = requestDAO;
    }

    /**
     * Respsonsible for backend mediation. Should check fo data formats etc.. NOT for business logic!
     *
     * @param input the incoming request.
     * @return true if data format is valid, false otherwise.
     */
    private static void checkInputValidity(GenerateRequestInput input) {
        if (input.getSuspectUserName() == null && input.getProfileLink() == null && (input.getFirstName() == null || input.getLastName() == null)) {
            throw new BadRequestException("Need to provide at least one of (user name, profile link, first + last name)");
        }

        if (input.getCaseID() <= 0) {
            throw new BadRequestException("Invalid case ID.");
        }

        try {
            // for now, case type can either be null, or oe of the values specified in the enum
            if (input.getCaseType() != null) {
                // will throw exception if case type is invalid
                CaseType.valueOf(input.getCaseType().toUpperCase());
            }
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid case Type.");
        }

        //TODO additional checks
    }

    /**
     * Endpoint for creating a new request.
     *
     * @param parsedInput Parameters for the generated request
     * @param user        the user who generates the request
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
        /* This will be used for file upload!
       if (fileField != null) {
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

        DateFormat formatter = new SimpleDateFormat("MM/DD/yyyy");

        Request request = null;
        try {
            request = new Request(user, parsedInput.getCaseID(), parsedInput.getCaseType(), parsedInput.getSuspectUserName(), parsedInput.getLastName(), parsedInput.getFirstName(), parsedInput.getMiddleName(), parsedInput.getEmail(), parsedInput.getPhoneNumber(), parsedInput.getRequestedDataStartDate() == null ? null : new Date(formatter.parse(parsedInput.getRequestedDataStartDate()).getTime()), parsedInput.getRequestedDataEndDate() == null ? null : new Date(formatter.parse(parsedInput.getRequestedDataEndDate()).getTime()), parsedInput.isContactInformationRequested(), parsedInput.isMiniFeedRequested(), parsedInput.isStatusHistoryRequested(), parsedInput.isSharesRequested(), parsedInput.isNotesRequested(), parsedInput.isWallPostingsRequested(), parsedInput.isFriendListRequested(), parsedInput.isVideosRequested(), parsedInput.isGroupsRequested(), parsedInput.isPastEventsRequested(), parsedInput.isFutureEventsRequested(), parsedInput.isPhotosRequested(), parsedInput.isPrivateMessagesRequested(), parsedInput.isGroupInfoRequested(), parsedInput.isIPLogRequested(), parsedInput.getCommunicantsUserNames(), parsedInput.getKeywords(), parsedInput.getKeywordCategories(), parsedInput.getLocationZipCode(), warrantBlob, RequestState.PENDING);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        request = requestDAO.persistNewRequest(request);
        return request;


    }

    @GET
    @RolesAllowed("LAW_ENFORCEMENT_OFFICER")
    @Path("/requestForm")
    public View showGenerateRequestView() {
        return new GenerateRequestView();
    }

    @GET
    @RolesAllowed("LAW_ENFORCEMENT_OFFICER")
    @UnitOfWork
    @Path("/{id}")
    public View getRequestDetails(@PathParam("id") int id,
                                  @QueryParam("sender") String senderName) {
        Optional<Request> requestOptional = requestDAO.findById(id);
        if (!requestOptional.isPresent()) {
            throw new NotFoundException(String.format("No request with id %d found.", id));
        }

        Result result = requestOptional.get().getResult();

        if (result == null) {
            return new NotAnsweredView();
        }

        List<Conversation> conversations = requestDAO.findById(id).map(Request::getResult).map(Result::getConversations).orElse(new ArrayList<>());
        if (senderName != null && senderName.length() > 0) {
            conversations = conversations.stream().filter(c -> c.getParticipants().contains(senderName)).collect(Collectors.toList());
        }
        return new RequestDetailsView(conversations, id);
    }
}

