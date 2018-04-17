package edu.cmu.resources.views;

import edu.cmu.db.entities.Request;
import io.dropwizard.views.View;

import java.util.List;

public class LeoHomeView extends View {
    private List<Request> requests;

    public LeoHomeView(List<Request> requests) {
        super("leohome.mustache");
        this.requests = requests;
    }

    public List<Request> getRequests() {
        return requests;
    }
}
