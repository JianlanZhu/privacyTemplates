package edu.cmu.resources.interaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO representing the input we receive from the frontend when a request is generated from the form.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenerateRequestInput {
    private long caseID;
    //private long userID;
    private String suspectUserName;
    private String caseType;
    private String profileLink;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String requestedDataStartDate;
    private String requestedDataEndDate;
    private Boolean isSharesRequested;
    private Boolean isNotesRequested;
    private Boolean isWallPostingsRequested;
    private Boolean isFriendListRequested;
    private Boolean isVideosRequested;
    private Boolean isGroupsRequested;
    private Boolean isPastEventsRequested;
    private Boolean isFutureEventsRequested;
    private Boolean isPhotosRequested;
    private Boolean isGroupInfoRequested;
    private Boolean isPrivateMessagesRequested;
    private Boolean isIPLogRequested;
    private Boolean isContactInformationRequested;
    private Boolean isMiniFeedRequested;
    private Boolean isStatusHistoryReuested;
    private String communicantsUserNames;
    private String keywords;
    private Integer keywordCategories;
    private String locationZipCode;
    private String startTime;
    private String endTime;

    public GenerateRequestInput() {
        // Jackson deserialization
    }

    public GenerateRequestInput(long caseID, String suspectUserName, String caseType, String profileLink, String firstName, String middleName, String lastName, String email, String phoneNumber, String requestedDataStartDate, String requestedDataEndDate, Boolean isSharesRequested, Boolean isNotesRequested, Boolean isWallPostingsRequested, Boolean isFriendListRequested, Boolean isVideosRequested, Boolean isGroupsRequested, Boolean isPastEventsRequested, Boolean isFutureEventsRequested, Boolean isPhotosRequested, Boolean isGroupInfoRequested, Boolean isPrivateMessagesRequested, Boolean isIPLogRequested, Boolean isContactInformationRequested, Boolean isMiniFeedRequested, Boolean isStatusHistoryReuested, String communicantsUserNames, String keywords, Integer keywordCategories, String locationZipCode, String startTime, String endTime) {
        this.caseID = caseID;
        //this.userID = userID;
        this.suspectUserName = suspectUserName;
        this.caseType = caseType;
        this.profileLink = profileLink;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.requestedDataStartDate = requestedDataStartDate;
        this.requestedDataEndDate = requestedDataEndDate;
        this.isSharesRequested = (isSharesRequested);
        this.isNotesRequested = isNotesRequested;
        this.isWallPostingsRequested = isWallPostingsRequested;
        this.isFriendListRequested = isFriendListRequested;
        this.isVideosRequested = isVideosRequested;
        this.isGroupsRequested = isGroupsRequested;
        this.isPastEventsRequested = isPastEventsRequested;
        this.isFutureEventsRequested = isFutureEventsRequested;
        this.isPhotosRequested = isPhotosRequested;
        this.isGroupInfoRequested = isGroupInfoRequested;
        this.isPrivateMessagesRequested = isPrivateMessagesRequested;
        this.isIPLogRequested = isIPLogRequested;
        this.isContactInformationRequested = isContactInformationRequested;
        this.isMiniFeedRequested = isMiniFeedRequested;
        this.isStatusHistoryReuested = isStatusHistoryReuested;
        this.communicantsUserNames = communicantsUserNames;
        this.keywords = keywords;
        this.keywordCategories = keywordCategories;
        this.locationZipCode = locationZipCode;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @JsonProperty
    public long getCaseID() {
        return caseID;
    }

    /*@JsonProperty
    public long getUserID() {
        return userID;
    }*/

    @JsonProperty
    public String getSuspectUserName() {
        return suspectUserName;
    }

    @JsonProperty
    public String getStartTime() {
        return startTime;
    }

    @JsonProperty
    public String getEndTime() {
        return endTime;
    }

    @JsonProperty
    public String getCaseType() {
        return caseType;
    }

    @JsonProperty
    public String getProfileLink() {
        return profileLink;
    }

    @JsonProperty
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty
    public String getMiddleName() {
        return middleName;
    }

    @JsonProperty
    public String getLastName() {
        return lastName;
    }

    @JsonProperty
    public String getEmail() {
        return email;
    }

    @JsonProperty
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @JsonProperty
    public String getRequestedDataStartDate() {
        return requestedDataStartDate;
    }

    @JsonProperty
    public String getRequestedDataEndDate() {
        return requestedDataEndDate;
    }

    @JsonProperty
    public Boolean isSharesRequested() {
        return isSharesRequested;
    }

    @JsonProperty
    public Boolean isNotesRequested() {
        return isNotesRequested;
    }

    @JsonProperty
    public Boolean isWallPostingsRequested() {
        return isWallPostingsRequested;
    }

    @JsonProperty
    public Boolean isFriendListRequested() {
        return isFriendListRequested;
    }

    @JsonProperty
    public Boolean isVideosRequested() {
        return isVideosRequested;
    }

    @JsonProperty
    public Boolean isGroupsRequested() {
        return isGroupsRequested;
    }

    @JsonProperty
    public Boolean isPastEventsRequested() {
        return isPastEventsRequested;
    }

    @JsonProperty
    public Boolean isFutureEventsRequested() {
        return isFutureEventsRequested;
    }

    @JsonProperty
    public Boolean isPhotosRequested() {
        return isPhotosRequested;
    }

    @JsonProperty
    public Boolean isGroupInfoRequested() {
        return isGroupInfoRequested;
    }

    @JsonProperty
    public Boolean isPrivateMessagesRequested() {
        return isPrivateMessagesRequested;
    }

    @JsonProperty
    public Boolean isIPLogRequested() {
        return isIPLogRequested;
    }

    @JsonProperty
    public String getCommunicantsUserNames() {
        return communicantsUserNames;
    }

    @JsonProperty
    public String getKeywords() {
        return keywords;
    }

    @JsonProperty
    public Integer getKeywordCategories() {
        return keywordCategories;
    }

    @JsonProperty
    public String getLocationZipCode() {
        return locationZipCode;
    }

    @JsonProperty
    public Boolean isContactInformationRequested() {
        return isContactInformationRequested;
    }

    @JsonProperty
    public Boolean isMiniFeedRequested() {
        return isMiniFeedRequested;
    }

    @JsonProperty
    public Boolean isStatusHistoryRequested() {
        return isStatusHistoryReuested;
    }
}

