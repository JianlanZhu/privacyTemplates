package edu.cmu.resources.views;

import io.dropwizard.views.View;

public class GenerateRequestView extends View {
    public GenerateRequestView(){
        super("RequestForm.mustache");
    }
}
