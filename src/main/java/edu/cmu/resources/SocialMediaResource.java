package edu.cmu.resources;

import edu.cmu.resources.views.UnansweredRequestsView;
import io.dropwizard.views.View;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/socialMedia")
@RolesAllowed("SOCIAL_MEDIA_EMPLOYEE")
public class SocialMediaResource {

    @GET
    @Path("/unansweredRequests")
    public View listAllUnansweredRequests(){
        return new UnansweredRequestsView();
    }

}
