package edu.cmu.resources;

import edu.cmu.resources.views.LandingPageView;
import io.dropwizard.views.View;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class LandingPageResource {

    @GET
    public View showLandingPage(){
        return new LandingPageView();
    }

}
