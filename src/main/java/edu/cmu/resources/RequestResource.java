package edu.cmu.resources;

import com.codahale.metrics.annotation.Timed;
import edu.cmu.db.dao.RequestDAO;
import edu.cmu.db.entities.Request;
import edu.cmu.resources.interaction.GenerateRequestInput;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//TODO: change path names to be RESTful
@Path("/request")
public class RequestResource {

    private RequestDAO requestDAO;

    public RequestResource(RequestDAO requestDAO) {
        this.requestDAO = requestDAO;
    }

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

        Request request = new Request(generateRequestInput.getCaseNumber(), generateRequestInput.getSuspectUserName());
        request = requestDAO.persistNewRequest(request);
        return request;
    }

    private static boolean isValidInput(GenerateRequestInput input) {
        return input.getSuspectUserName() != null && input.getCaseNumber() > 0;
    }
}

