package edu.cmu.resources;

import edu.cmu.db.dao.RequestDAO;
import edu.cmu.db.entities.Request;
import edu.cmu.db.entities.User;
import edu.cmu.resources.views.LeoHomeView;
import edu.cmu.resources.views.LoginView;
import edu.cmu.resources.views.SmeHomeView;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.views.View;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;
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
    @RolesAllowed("SOCIAL_MEDIA_EMPLOYEE")
    @UnitOfWork
    @Path("smeHome")
    public View smeHome(@Auth User user) {
        List<Request> allRequestsToUser = requestDAO.findAll();
        return new SmeHomeView(allRequestsToUser);
    }

    @GET
    public View showLandingPage() {
        return new LoginView();
    }

}
