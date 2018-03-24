package edu.cmu.resources;

import com.codahale.metrics.annotation.Timed;
import edu.cmu.db.dao.RequestDAO;
import edu.cmu.db.enums.CaseType;
import edu.cmu.db.entities.Request;
import edu.cmu.resources.interaction.GenerateRequestInput;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * This class is used for registering endpoints regarding requests.
 */
//TODO: change path names to be RESTful
@Path("/request")
public class RequestResource {

    /**
     * Responsible for accessing the database.
     */
    private RequestDAO requestDAO;
    private RequestDAO caseTypeDAO;

    public RequestResource(RequestDAO requestDAO) {
        this.requestDAO = requestDAO;
    }

    /**
     * Endpoint for creating a new request.
     * @param generateRequestInput Input parameters
     * @return the generated request including generated fields like ID.
     */
    @POST
    @Path("/generate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @Timed
    public Request generateRequest(GenerateRequestInput generateRequestInput) {
        checkInputValidity(generateRequestInput);

        try {
            // TODO replace hard coded userID 1 by userID of currently authenticated user
            Request request = new Request(1, generateRequestInput.getCaseID(), generateRequestInput.getCaseType(), generateRequestInput.getSuspectUserName(), generateRequestInput.getLastName(), generateRequestInput.getFirstName(), generateRequestInput.getMiddleName(), generateRequestInput.getEmail(), generateRequestInput.getPhoneNumber(), generateRequestInput.getRequestedDataStartDate(), generateRequestInput.getRequestedDataEndDate(), generateRequestInput.isContactInformationRequested(), generateRequestInput.isMiniFeedRequested(), generateRequestInput.isStatusHistoryRequested(), generateRequestInput.isSharesRequested(), generateRequestInput.isNotesRequested(), generateRequestInput.isWallPostingsRequested(), generateRequestInput.isFriendListRequested(), generateRequestInput.isVideosRequested(), generateRequestInput.isGroupsRequested(), generateRequestInput.isPastEventsRequested(), generateRequestInput.isFutureEventsRequested(), generateRequestInput.isPhotosRequested(), generateRequestInput.isPrivateMessagesRequested(), generateRequestInput.isGroupInfoRequested(), generateRequestInput.isIPLogRequested(), null, null, generateRequestInput.getCommunicantsUserNames(), generateRequestInput.getKeywords(), generateRequestInput.getKeywordCategories(), generateRequestInput.getLocationZipCode());
            request = requestDAO.persistNewRequest(request);
            return request;
        } catch (IllegalArgumentException e){
            throw new BadRequestException("Malformed Request");
        }
    }

    /**
     * Respsonsible for backend mediation. Should check fo data formats etc.. NOT for business logic!
     * @param input the incoming request.
     * @return true if data format is valid, false otherwise.
     */
    private static void checkInputValidity(GenerateRequestInput input) {
        if(input.getSuspectUserName() == null){
            throw new BadRequestException("Invalid user name.");
        }

        if(input.getCaseID() <= 0){
            // TODO maybe replace with actual check whether case is present in data base?
            throw new BadRequestException("Invalid case ID.");
        }

        try{
            // for now, case type can either be null, or oe of the values specified in the enum
            if(input.getCaseType() != null){
                // will throw exception if case type is invalid
                CaseType.valueOf(input.getCaseType());
            }
        } catch (IllegalArgumentException e){
            throw new BadRequestException("Invalid Case Type.");
        }

        //TODO additional checks
    }
}

