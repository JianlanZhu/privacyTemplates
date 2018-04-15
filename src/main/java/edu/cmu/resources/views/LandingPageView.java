package edu.cmu.resources.views;

import io.dropwizard.views.View;

public class LandingPageView extends View {
    public LandingPageView(){
        super("landingPage.mustache");
    }
}
