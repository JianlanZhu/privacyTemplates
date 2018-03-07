package edu.cmu.resources.interaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

/**
 * POJO representing the input we receive from the frontend when a request is generated from the form.
 */
public class GenerateRequestInput {
    private long caseNumber;

    private String suspectUserName;

    public GenerateRequestInput() {
        // Jackson deserialization
    }

    public GenerateRequestInput(long caseNumber, String suspectUserName) {
        this.caseNumber = caseNumber;
        this.suspectUserName = suspectUserName;
    }

    @JsonProperty
    public long getCaseNumber() {
        return caseNumber;
    }

    @JsonProperty
    public String getSuspectUserName() {
        return suspectUserName;
    }
}

