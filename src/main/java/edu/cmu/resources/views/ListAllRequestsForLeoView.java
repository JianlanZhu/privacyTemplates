package edu.cmu.resources.views;

import edu.cmu.db.entities.Request;
import io.dropwizard.views.View;

import java.util.List;

public class ListAllRequestsForLeoView extends View {

    private List<Request> requests;

    public ListAllRequestsForLeoView(List<Request> requests){
        super("listAllRequestsForLeo.mustache");
        this.requests = requests;
    }

    public List<Request> getRequests() {
        return requests;
    }
}
