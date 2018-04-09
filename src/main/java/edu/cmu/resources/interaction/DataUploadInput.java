package edu.cmu.resources.interaction;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO representing the input we receive from the frontend when a request is generated from the form.
 */
public class DataUploadInput {

    private int requestId;
    private String comment;

    public DataUploadInput() {
        // Jackson deserialization
    }

    public DataUploadInput(int requestId, String comment) {
        this.requestId = requestId;
        this.comment = comment;
    }

    @JsonProperty
    public int getRequestId() {
        return requestId;
    }

    @JsonProperty
    public String getComment() {
        return comment;
    }
}

