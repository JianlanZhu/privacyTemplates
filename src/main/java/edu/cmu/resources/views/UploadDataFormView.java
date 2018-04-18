package edu.cmu.resources.views;

import io.dropwizard.views.View;

public class UploadDataFormView extends View {
    private int requestId;

    public UploadDataFormView(int requestId) {
        super("uploadDataForm.mustache");
        this.requestId = requestId;
    }

    public int getRequestId() {
        return requestId;
    }
}
