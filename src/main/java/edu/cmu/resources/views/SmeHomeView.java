package edu.cmu.resources.views;

import edu.cmu.db.entities.Request;
import edu.cmu.db.enums.RequestState;
import io.dropwizard.views.View;

import java.util.List;
import java.util.stream.Collectors;

public class SmeHomeView extends View {

    private List<Request> rejectedRequests;
    private List<Request> answeredRequests;
    private List<Request> pendingRequests;
    private List<Request> closedRequests;

    public SmeHomeView(List<Request> requests) {
        super("smehome.mustache");
        List<Request> rejectedRequests = requests.stream().filter(r -> r.getStatus().equals(RequestState.REJECTED.name())).collect(Collectors.toList());
        List<Request> answeredRequests = requests.stream().filter(r -> r.getStatus().equals(RequestState.ANSWERED.name())).collect(Collectors.toList());
        List<Request> pendingRequests = requests.stream().filter(r -> r.getStatus().equals(RequestState.PENDING.name())).collect(Collectors.toList());
        List<Request> closedRequests = requests.stream().filter(r -> r.getStatus().equals(RequestState.CLOSED.name())).collect(Collectors.toList());
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
