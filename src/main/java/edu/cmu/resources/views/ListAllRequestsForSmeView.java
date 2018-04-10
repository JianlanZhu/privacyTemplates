package edu.cmu.resources.views;

import edu.cmu.db.entities.Request;
import io.dropwizard.views.View;

import java.util.List;

public class ListAllRequestsForSmeView extends View {

    private List<Request> requests;

    public ListAllRequestsForSmeView(List<Request> requests){
        super("listAllRequestsForSme.mustache");
        this.requests = requests;
    }

    public List<Request> getRequests() {
        return requests;
    }
}
