package edu.cmu.resources.views;

import edu.cmu.db.entities.Request;
import io.dropwizard.views.View;

import java.util.List;

public class LeoHomeView extends View {
    private List<Request> rejectedRequests;
    private List<Request> answeredRequests;
    private List<Request> pendingRequests;
    private List<Request> closedRequests;

    public LeoHomeView(List<Request> rejectedRequests, List<Request> answeredRequests, List<Request> pendingRequests, List<Request> closedRequests) {
        super("leohome.mustache");
        this.rejectedRequests = rejectedRequests;
        this.answeredRequests = answeredRequests;
        this.pendingRequests = pendingRequests;
        this.closedRequests = closedRequests;
    }

    public List<Request> getRejectedRequests() {
        return rejectedRequests;
    }

    public List<Request> getAnsweredRequests() {
        return answeredRequests;
    }

    public List<Request> getPendingRequests() {
        return pendingRequests;
    }

    public List<Request> getClosedRequests() {
        return closedRequests;
    }
}
