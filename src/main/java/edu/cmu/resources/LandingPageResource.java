package edu.cmu.resources;

import edu.cmu.resources.views.LandingPageView;
import edu.cmu.resources.views.LeoHomeView;
import io.dropwizard.views.View;
import org.hibernate.validator.constraints.br.TituloEleitoral;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class LandingPageResource {

    @GET
    @Path("leoHome")
    public View leoHome(){
        return new LeoHomeView();
    }

    @GET
    public View showLandingPage() {
        return new LandingPageView();
    }

}
