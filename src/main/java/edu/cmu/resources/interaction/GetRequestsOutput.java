package edu.cmu.resources.interaction;


import com.fasterxml.jackson.annotation.JsonProperty;
import edu.cmu.db.entities.Request;

import java.util.ArrayList;
import java.util.List;

public class GetRequestsOutput {
    @JsonProperty
    public List<RequestSummary> requests;

    public GetRequestsOutput(List<Request> requests) {
        this.requests = new ArrayList<>();
        for (Request request : requests) {
            this.requests.add(new RequestSummary(
                    request.getCaseID(),
                    request.getStatus(),
                    new BasicInfo(request.getSuspectUserName(), request.getSuspectFirstName(), request.getSuspectLastName(), null))
            );
        }
    }

    class RequestSummary {
        @JsonProperty
        public long caseId;
        @JsonProperty
        public String status;
        @JsonProperty
        public BasicInfo basicInfo;

        public RequestSummary(long caseId, String status, BasicInfo basicInfo) {
            this.caseId = caseId;
            this.status = status;
            this.basicInfo = basicInfo;
        }
    }

    class BasicInfo {
        @JsonProperty
        public String userName;
        @JsonProperty
        public String firstName;
        @JsonProperty
        public String lastName;
        @JsonProperty
        public String profileLink;

        public BasicInfo(String userName, String firstName, String lastName, String profileLink) {
            this.userName = userName;
            this.firstName = firstName;
            this.lastName = lastName;
            this.profileLink = profileLink;
        }
    }
}

