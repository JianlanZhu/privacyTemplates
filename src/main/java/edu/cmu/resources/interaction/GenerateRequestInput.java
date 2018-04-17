package edu.cmu.resources.interaction;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

/**
 * POJO representing the input we receive from the frontend when a request is generated from the form.
 */
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
    private Instant requestedDataStartDate;
    private Instant requestedDataEndDate;
    private boolean isSharesRequested;
    private boolean isNotesRequested;
    private boolean isWallPostingsRequested;
    private boolean isFriendListRequested;
    private boolean isVideosRequested;
    private boolean isGroupsRequested;
    private boolean isPastEventsRequested;
    private boolean isFutureEventsRequested;
    private boolean isPhotosRequested;
    private boolean isGroupInfoRequested;
    private boolean isPrivateMessagesRequested;
    private boolean isIPLogRequested;
    private boolean isContactInformationRequested;
    private boolean isMiniFeedRequested;
    private boolean isStatusHistoryReuested;
    private String communicantsUserNames;
    private String keywords;
    private String keywordCategories;
    private String locationZipCode;
    private String startTime;
    private String endTime;

    public GenerateRequestInput() {
        // Jackson deserialization
    }

    public GenerateRequestInput(long caseID, String suspectUserName, String caseType, String profileLink, String firstName, String middleName, String lastName, String email, String phoneNumber, Instant requestedDataStartDate, Instant requestedDataEndDate, boolean isSharesRequested, boolean isNotesRequested, boolean isWallPostingsRequested, boolean isFriendListRequested, boolean isVideosRequested, boolean isGroupsRequested, boolean isPastEventsRequested, boolean isFutureEventsRequested, boolean isPhotosRequested, boolean isGroupInfoRequested, boolean isPrivateMessagesRequested, boolean isIPLogRequested, boolean isContactInformationRequested, boolean isMiniFeedRequested, boolean isStatusHistoryReuested, String communicantsUserNames, String keywords, String keywordCategories, String locationZipCode, String startTime, String endTime) {
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
        this.isSharesRequested = isSharesRequested;
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
    public Instant getRequestedDataStartDate() {
        return requestedDataStartDate;
    }

    @JsonProperty
    public Instant getRequestedDataEndDate() {
        return requestedDataEndDate;
    }

    @JsonProperty
    public boolean isSharesRequested() {
        return isSharesRequested;
    }

    @JsonProperty
    public boolean isNotesRequested() {
        return isNotesRequested;
    }

    @JsonProperty
    public boolean isWallPostingsRequested() {
        return isWallPostingsRequested;
    }

    @JsonProperty
    public boolean isFriendListRequested() {
        return isFriendListRequested;
    }

    @JsonProperty
    public boolean isVideosRequested() {
        return isVideosRequested;
    }

    @JsonProperty
    public boolean isGroupsRequested() {
        return isGroupsRequested;
    }

    @JsonProperty
    public boolean isPastEventsRequested() {
        return isPastEventsRequested;
    }

    @JsonProperty
    public boolean isFutureEventsRequested() {
        return isFutureEventsRequested;
    }

    @JsonProperty
    public boolean isPhotosRequested() {
        return isPhotosRequested;
    }

    @JsonProperty
    public boolean isGroupInfoRequested() {
        return isGroupInfoRequested;
    }

    @JsonProperty
    public boolean isPrivateMessagesRequested() {
        return isPrivateMessagesRequested;
    }

    @JsonProperty
    public boolean isIPLogRequested() {
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
    public String getKeywordCategories() {
        return keywordCategories;
    }

    @JsonProperty
    public String getLocationZipCode() {
        return locationZipCode;
    }

    @JsonProperty
    public boolean isContactInformationRequested() {
        return isContactInformationRequested;
    }

    @JsonProperty
    public boolean isMiniFeedRequested() {
        return isMiniFeedRequested;
    }

    @JsonProperty
    public boolean isStatusHistoryRequested() {
        return isStatusHistoryReuested;
    }
}

