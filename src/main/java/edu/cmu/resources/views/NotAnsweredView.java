package edu.cmu.resources.views;

import io.dropwizard.views.View;

public class NotAnsweredView extends View {
    public NotAnsweredView() {
        super("unansweredRequest.mustache");
    }
}
