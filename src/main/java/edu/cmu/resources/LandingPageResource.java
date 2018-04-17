package edu.cmu.resources;

import edu.cmu.db.dao.RequestDAO;
import edu.cmu.db.dao.ResultDAO;
import edu.cmu.db.entities.User;
import edu.cmu.resources.views.LandingPageView;
import edu.cmu.resources.views.LeoHomeView;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.views.View;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.stream.Collectors;

@Path("/")
public class LandingPageResource {

    private RequestDAO requestDAO;

    public LandingPageResource(RequestDAO requestDAO) {
        this.requestDAO = requestDAO;
    }

    @GET
    @RolesAllowed("LAW_ENFORCEMENT_OFFICER")
    @UnitOfWork
    @Path("leoHome")
    public View leoHome(@Auth User user){
        return new LeoHomeView(requestDAO.findAll().stream().filter(request -> request.getCreatedBy().getUserID() == user.getUserID()).collect(Collectors.toList()));
    }

    @GET
    public View showLandingPage() {
        return new LandingPageView();
    }

}
