package edu.cmu.resources;

import com.codahale.metrics.annotation.Timed;
import edu.cmu.resources.interaction.SayingOutput;
import edu.cmu.resources.interaction.GenerateRequestInput;
import org.assertj.core.util.Strings;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/request")
public class RequestResource {

    @POST
    @Path("/generate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    public void generateRequest(GenerateRequestInput generateRequestInput) {
        if(generateRequestInput.getCaseNumber() <= 0 || Strings.isNullOrEmpty(generateRequestInput.getSuspectUserName())){
            throw new BadRequestException();
        }
    }
}

