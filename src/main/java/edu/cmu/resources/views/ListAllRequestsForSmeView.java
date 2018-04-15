package edu.cmu.resources.views;

import edu.cmu.db.entities.Request;
import io.dropwizard.views.View;

import java.util.List;

public class ListAllRequestsForSmeView extends View {

    private List<Request> pendingRequests;
    private List<Request> rejectedRequests;
    private List<Request> answeredRequests;
    private List<Request> closedRequests;

    public ListAllRequestsForSmeView(List<Request> pendingRequests, List<Request> rejectedRequests, List<Request> answeredRequests, List<Request> closedRequests) {
        super("listAllRequestsForSme.mustache");
        this.pendingRequests = pendingRequests;
        this.rejectedRequests = rejectedRequests;
        this.answeredRequests = answeredRequests;
        this.closedRequests = closedRequests;
    }

    /* Getter for mustache templates */

    public List<Request> getPendingRequests() {
        return pendingRequests;
    }

    public List<Request> getRejectedRequests() {
        return rejectedRequests;
    }

    public List<Request> getAnsweredRequests() {
        return answeredRequests;
    }

    public List<Request> getClosedRequests() {
        return closedRequests;
    }
}
