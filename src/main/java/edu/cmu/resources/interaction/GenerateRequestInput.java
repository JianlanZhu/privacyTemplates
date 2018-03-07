package edu.cmu.resources.interaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

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

