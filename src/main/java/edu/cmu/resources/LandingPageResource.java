package edu.cmu.resources;

import edu.cmu.db.dao.RequestDAO;
import edu.cmu.db.entities.Request;
import edu.cmu.db.entities.User;
import edu.cmu.db.enums.RequestState;
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

    /**
     * @param user automatically determined through a cookie.
     * @return a view showing all requests that have been issued by the requesting user.
     */
    @GET
    @RolesAllowed("LAW_ENFORCEMENT_OFFICER")
    @UnitOfWork
    @Path("leoHome")
    public View leoHome(@Auth User user){
        List<Request> requests = requestDAO.findAllForUser(user);
        List<Request> rejectedRequests = requests.stream().filter(r -> r.getStatus().equals(RequestState.REJECTED.name())).collect(Collectors.toList());
        List<Request> answeredRequests = requests.stream().filter(r -> r.getStatus().equals(RequestState.ANSWERED.name())).collect(Collectors.toList());
        List<Request> pendingRequests = requests.stream().filter(r -> r.getStatus().equals(RequestState.PENDING.name())).collect(Collectors.toList());
        List<Request> closedRequests = requests.stream().filter(r -> r.getStatus().equals(RequestState.CLOSED.name())).collect(Collectors.toList());
        return new LeoHomeView(rejectedRequests, answeredRequests, pendingRequests, closedRequests);
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
