package edu.cmu.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Strings;
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

        Request request = setupRequest(user, parsedInput);
        request = requestDAO.persistNewRequest(request);
        return request;
    }

    private Request setupRequest(User user, GenerateRequestInput parsedInput) {
        Request request = new Request(user, parsedInput.getCaseID(), parsedInput.getCaseType(), parseDate(parsedInput.getRequestedDataStartDate()), parseDate(parsedInput.getRequestedDataEndDate()), RequestState.PENDING);

        request.setSuspectUserName(parsedInput.getSuspectUserName());
        request.setSuspectLastName(parsedInput.getLastName());
        request.setSuspectFirstName(parsedInput.getFirstName());
        request.setSuspectMiddleName(parsedInput.getMiddleName());
        request.setSuspectRegisteredEmailAddress(parsedInput.getEmail());
        request.setSuspectRegisteredPhoneNumber(parsedInput.getPhoneNumber());

        request.setContactInformationRequested(parsedInput.isContactInformationRequested());
        request.setMiniFeedRequested(parsedInput.isMiniFeedRequested());
        request.setStatusHistoryRequested(parsedInput.isStatusHistoryRequested());
        request.setSharesRequested(parsedInput.isSharesRequested());
        request.setNotesRequested(parsedInput.isNotesRequested());
        request.setWallPostingsRequested(parsedInput.isWallPostingsRequested());
        request.setFriendListRequested(parsedInput.isFriendListRequested());
        request.setVideosRequested(parsedInput.isVideosRequested());
        request.setGroupsRequested(parsedInput.isGroupsRequested());
        request.setPastEventsRquested(parsedInput.isPastEventsRequested());
        request.setFutureEventsRequested(parsedInput.isFutureEventsRequested());
        request.setPhotosRequested(parsedInput.isPhotosRequested());
        request.setPrivateMessagesRequested(parsedInput.isPrivateMessagesRequested());
        request.setGroupInfoRequested(parsedInput.isGroupInfoRequested());
        request.setIPLogRequested(parsedInput.isIPLogRequested());
        request.setFilterCommunicantsUserName(parsedInput.getCommunicantsUserNames());
        request.setFilterKeywords(parsedInput.getKeywords());
        request.setFilterKeywordsCategory(parsedInput.getKeywordCategories());
        request.setFilterLocation(parsedInput.getLocationZipCode());
        return request;
    }

    @PUT
    @RolesAllowed("LAW_ENFORCEMENT_OFFICER")
    @UnitOfWork
    @Path("/{id}")
    public void updateRequestStatus(@Auth User user,
                                    @PathParam("id") int id,
                                    @QueryParam("newStatus") String newStatus) {
        Request request = getRequestOrThrowNotFound(user, id);
        request.setStatus(newStatus);
    }

    private Request getRequestOrThrowNotFound(User user, int id) {
        Optional<Request> requestOptional = requestDAO.findById(id);
        // return 404 in case request exists but user is not authorized to see it in order to prevent inference about existence of requests
        if (!requestOptional.isPresent() || !requestOptional.get().getCreatedBy().equals(user)) {
            throw new NotFoundException(String.format("No request with id %d found.", id));
        }
        return requestOptional.get();
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
    public View getRequestDetails(@Auth User user,
                                  @PathParam("id") int id,
                                  @QueryParam("sender") String senderName) {
        Request request = getRequestOrThrowNotFound(user, id);

        Result result = request.getResult();

        if (result == null) {
            return new NotAnsweredView();
        }

        List<Conversation> conversations = requestDAO.findById(id).map(Request::getResult).map(Result::getConversations).orElse(new ArrayList<>());
        if (senderName != null && senderName.length() > 0) {
            conversations = conversations.stream().filter(c -> c.getParticipants().contains(senderName)).collect(Collectors.toList());
        }
        return new RequestDetailsView(conversations, id);
    }

    private Date parseDate(String dateInput) {
        DateFormat formatter = new SimpleDateFormat("MM/DD/yyyy");
        try {
            return dateInput == null ? null : new Date(formatter.parse(dateInput).getTime());
        } catch (ParseException e) {
            throw new BadRequestException("Date input has wrong format.");
        }
    }

    /**
     * Responsible for backend mediation. Should check fo data formats etc.. NOT for business logic!
     *
     * @param input the incoming request.
     * @return true if data format is valid, false otherwise.
     */
    private static void checkInputValidity(GenerateRequestInput input) {
        if (input.getCaseID() <= 0) {
            throw new BadRequestException("Invalid case ID.");
        }

        if (Strings.isNullOrEmpty(input.getSuspectUserName()) && (Strings.isNullOrEmpty(input.getFirstName()) || Strings.isNullOrEmpty(input.getLastName())) && Strings.isNullOrEmpty(input.getProfileLink())) {
            throw new BadRequestException("Basic identification needs to be provided. Specify at least one of [FB username, first + last name, FB profile link].");
        }

        if (Strings.isNullOrEmpty(input.getCaseType())) {
            throw new BadRequestException("Specification of case type is mandatory.");
        }

        try {
            // will throw exception if case type is invalid
            CaseType.valueOf(input.getCaseType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid case Type.");
        }

        if (Strings.isNullOrEmpty(input.getRequestedDataStartDate()) || Strings.isNullOrEmpty(input.getRequestedDataEndDate())) {
            throw new BadRequestException("Request start and end dates are mandatory.");
        }

        if (input.isIPLogRequested() && !input.getCaseType().equalsIgnoreCase(CaseType.FELONY.name())) {
            throw new BadRequestException("IP logs cannot only be requested for felony cases");
        }

    }
}

