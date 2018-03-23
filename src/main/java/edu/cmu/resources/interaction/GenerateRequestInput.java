package edu.cmu.resources.interaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

/**
 * POJO representing the input we receive from the frontend when a request is generated from the form.
 */
public class GenerateRequestInput {
    private long caseNumber;
    private long userID;
    private String suspectUserName;
    private String caseType;

    public GenerateRequestInput() {
        // Jackson deserialization
    }

    public GenerateRequestInput(long caseNumber, String suspectUserName, long userID) {
        this.caseNumber = caseNumber;
        this.userID = userID;
        this.suspectUserName = suspectUserName;
    }

    @JsonProperty
    public long getCaseNumber() {
        return caseNumber;
    }

    @JsonProperty
    public long getUserID() {
        return userID;
    }

    @JsonProperty
    public String getSuspectUserName() {
        return suspectUserName;
    }

    @JsonProperty
    public String getCaseType() {
        return caseType;
    }
}

