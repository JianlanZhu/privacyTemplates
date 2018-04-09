package edu.cmu.resources.views;

import edu.cmu.db.entities.Request;
import io.dropwizard.views.View;

import java.util.List;

public class ListAllRequestsView extends View {

    private List<Request> requests;

    public ListAllRequestsView(List<Request> requests){
        super("listAllRequests.mustache");
        this.requests = requests;
    }

    public List<Request> getRequests() {
        return requests;
    }
}
