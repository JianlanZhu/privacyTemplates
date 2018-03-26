package edu.cmu.resources.views;

import io.dropwizard.views.View;

public class UnansweredRequestsView extends View {
    public UnansweredRequestsView(){
        super("unansweredRequests.mustache");
    }
}
