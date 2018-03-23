package edu.cmu.resources;

import com.codahale.metrics.annotation.Timed;
import edu.cmu.db.dao.RequestDAO;
import edu.cmu.db.enums.CaseType;
import edu.cmu.db.entities.Request;
import edu.cmu.db.enums.RequestState;
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
        if (!isValidInput(generateRequestInput)) {
            throw new BadRequestException("Input for generating request is invalid.");
        }

        try {
            Request request = new Request(generateRequestInput.getCaseNumber(), generateRequestInput.getSuspectUserName(), generateRequestInput.getUserID(), CaseType.valueOf(generateRequestInput.getCaseType()), RequestState.FILED);
            request = requestDAO.persistNewRequest(request);
            return request;
        } catch (IllegalArgumentException e){
            throw new BadRequestException("Malformed Request");
        }
    }

    /**
     * Respsonsible for backend mdiation. Should check fo data formats etc.. NOT for business logic!
     * @param input the incoming request.
     * @return true if data format is valid, false otherwise.
     */
    private static boolean isValidInput(GenerateRequestInput input) {
        if(input.getSuspectUserName() == null || input.getCaseNumber() <= 0){
            return false;
        }

        try{
            CaseType.valueOf(input.getCaseType());
        } catch (IllegalArgumentException e){
            return false;
        }

        return true;
    }
}

