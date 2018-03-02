package edu.cmu.resources;

import com.codahale.metrics.annotation.Timed;
import edu.cmu.resources.interaction.SayingOutput;
import edu.cmu.resources.interaction.GenerateRequestInput;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/request")
@Produces(MediaType.APPLICATION_JSON)
public class RequestResource {

    @POST
    @Path("/generate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    public SayingOutput generateRequest(GenerateRequestInput generateRequestInput) {
        final String value = String.format("Sent in : %s", generateRequestInput.getContent());
        return new SayingOutput(1, value);
    }
}

