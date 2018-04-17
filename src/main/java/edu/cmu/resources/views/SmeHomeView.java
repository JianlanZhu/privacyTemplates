package edu.cmu.resources.views;

import edu.cmu.db.entities.Request;
import io.dropwizard.views.View;

import java.util.List;

public class SmeHomeView extends View {

    private List<Request> allRequests;

    public SmeHomeView(List<Request> allRequests) {
        super("smehome.mustache");
        this.allRequests = allRequests;
    }

    public List<Request> getAllRequests() {
        return allRequests;
    }
}
