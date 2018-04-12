package edu.cmu.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Blob;
import java.time.Instant;

/**
 * This class is used by Hibernate to map a Java object to the corresponding table in the database.
 */
@Entity
@Table(name = "request")
@NamedQueries({
        @NamedQuery(name = "edu.cmu.db.entities.Request.findAll",
                query = "select r from Request r")
})
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long requestID;

    @Column(name = "requestCreatedDate")
    private Instant requestCreatedDate;

    @Column(name = "createdByID")
    private long createdByID;

    @Column(name = "caseID")
    private long caseID;

    @Column(name = "caseType")
    private String caseType;

    @Column(name = "suspectUserName")
    private String suspectUserName;

    @Column(name = "suspectLastName")
    private String suspectLastName;

    @Column(name = "suspectFirstName")
    private String suspectFirstName;

    @Column(name = "suspectMiddleName")
    private String suspectMiddleName;

    @Column(name = "suspectRegisteredEmailAddress")
    private String suspectRegisteredEmailAddress;

    @Column(name = "suspectRegisteredPhoneNumber")
    private String suspectRegisteredPhoneNumber;

    @Column(name = "requestedDataStartDate")
    private Instant requestedDataStartDate;

    @Column(name = "requestedDataEndDate")
    private Instant requestedDataEndDate;

    @Column(name = "isContactInformationRequested")
    private boolean isContactInformationRequested;

    @Column(name = "isMiniFeedRequested")
    private boolean isMiniFeedRequested;

    @Column(name = "isStatusHistoryRequested")
    private boolean isStatusHistoryRequested;

    @Column(name = "isSharesRequested")
    private boolean isSharesRequested;

    @Column(name = "isNotesRequested")
    private boolean isNotesRequested;

    @Column(name = "isWallPostingsRequested")
    private boolean isWallPostingsRequested;

    @Column(name = "isFriendListRequested")
    private boolean isFriendListRequested;

    @Column(name = "isVideosRequested")
    private boolean isVideosRequested;

    @Column(name = "isGroupsRequested")
    private boolean isGroupsRequested;

    @Column(name = "isPastEventsRquested")
    private boolean isPastEventsRquested;

    @Column(name = "isFutureEventsRequested")
    private boolean isFutureEventsRequested;

    @Column(name = "isPhotosRequested")
    private boolean isPhotosRequested;

    @Column(name = "isPrivateMessagesRequested")
    private boolean isPrivateMessagesRequested;

    @Column(name = "isGroupInfoRequested")
    private boolean isGroupInfoRequested;

    @Column(name = "isIPLogRequested")
    private boolean isIPLogRequested;

    @Column(name = "filterStartTime")
    private Instant filterStartTime;

    @Column(name = "filterEndTime")
    private Instant filterEndTime;

    // TODO what's that?
    @Column(name = "filterCommunicantsUserName")
    private String filterCommunicantsUserName;

    @Column(name = "filterKeywords")
    private String filterKeywords;

    @Column(name = "filterKeywordsCategory")
    private String filterKeywordsCategory;

    @Column(name = "filterLocation")
    private String filterLocation;

    @Column(name = "status")
    private String status;

    @JsonIgnore
    @Column(name = "warrant")
    private Blob warrant;

    public Request() {
    }

    public Request(long createdByID, long caseID, String caseType, String suspectUserName, String suspectLastName, String suspectFirstName, String suspectMiddleName, String suspectRegisteredEmailAddress, String suspectRegisteredPhoneNumber, Instant requestedDataStartDate, Instant requestedDataEndDate, boolean isContactInformationRequested, boolean isMiniFeedRequested, boolean isStatusHistoryRequested, boolean isSharesRequested, boolean isNotesRequested, boolean isWallPostingsRequested, boolean isFriendListRequested, boolean isVideosRequested, boolean isGroupsRequested, boolean isPastEventsRquested, boolean isFutureEventsRequested, boolean isPhotosRequested, boolean isPrivateMessagesRequested, boolean isGroupInfoRequested, boolean isIPLogRequested, Instant filterStartTime, Instant filterEndTime, String filterCommunicantsUserName, String filterKeywords, String filterKeywordsCategory, String filterLocation, Blob warrant) {
        this.requestCreatedDate = Instant.now();
        this.createdByID = createdByID;
        this.caseID = caseID;
        this.caseType = caseType;
        this.suspectUserName = suspectUserName;
        this.suspectLastName = suspectLastName;
        this.suspectFirstName = suspectFirstName;
        this.suspectMiddleName = suspectMiddleName;
        this.suspectRegisteredEmailAddress = suspectRegisteredEmailAddress;
        this.suspectRegisteredPhoneNumber = suspectRegisteredPhoneNumber;
        this.requestedDataStartDate = requestedDataStartDate;
        this.requestedDataEndDate = requestedDataEndDate;
        this.isContactInformationRequested = isContactInformationRequested;
        this.isMiniFeedRequested = isMiniFeedRequested;
        this.isStatusHistoryRequested = isStatusHistoryRequested;
        this.isSharesRequested = isSharesRequested;
        this.isNotesRequested = isNotesRequested;
        this.isWallPostingsRequested = isWallPostingsRequested;
        this.isFriendListRequested = isFriendListRequested;
        this.isVideosRequested = isVideosRequested;
        this.isGroupsRequested = isGroupsRequested;
        this.isPastEventsRquested = isPastEventsRquested;
        this.isFutureEventsRequested = isFutureEventsRequested;
        this.isPhotosRequested = isPhotosRequested;
        this.isPrivateMessagesRequested = isPrivateMessagesRequested;
        this.isGroupInfoRequested = isGroupInfoRequested;
        this.isIPLogRequested = isIPLogRequested;
        this.filterStartTime = filterStartTime;
        this.filterEndTime = filterEndTime;
        this.filterCommunicantsUserName = filterCommunicantsUserName;
        this.filterKeywords = filterKeywords;
        this.filterKeywordsCategory = filterKeywordsCategory;
        this.filterLocation = filterLocation;
        this.warrant = warrant;
    }

    public long getRequestID() {
        return requestID;
    }

    public void setRequestID(long requestID) {
        this.requestID = requestID;
    }

    public Instant getRequestCreatedDate() {
        return requestCreatedDate;
    }

    public void setRequestCreatedDate(Instant requestCreatedDate) {
        this.requestCreatedDate = requestCreatedDate;
    }

    public long getCreatedByID() {
        return createdByID;
    }

    public void setCreatedByID(long createdByID) {
        this.createdByID = createdByID;
    }

    public long getCaseID() {
        return caseID;
    }

    public void setCaseID(long caseID) {
        this.caseID = caseID;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getSuspectUserName() {
        return suspectUserName;
    }

    public void setSuspectUserName(String suspectUserName) {
        this.suspectUserName = suspectUserName;
    }

    public String getSuspectLastName() {
        return suspectLastName;
    }

    public void setSuspectLastName(String suspectLastName) {
        this.suspectLastName = suspectLastName;
    }

    public String getSuspectFirstName() {
        return suspectFirstName;
    }

    public void setSuspectFirstName(String suspectFirstName) {
        this.suspectFirstName = suspectFirstName;
    }

    public String getSuspectMiddleName() {
        return suspectMiddleName;
    }

    public void setSuspectMiddleName(String suspectMiddleName) {
        this.suspectMiddleName = suspectMiddleName;
    }

    public String getSusoectRegisteredEmailAddress() {
        return suspectRegisteredEmailAddress;
    }

    public void setSusoectRegisteredEmailAddress(String suspectRegisteredEmailAddress) {
        this.suspectRegisteredEmailAddress = suspectRegisteredEmailAddress;
    }

    public String getSusoectRegisteredPhoneNumber() {
        return suspectRegisteredPhoneNumber;
    }

    public void setSusoectRegisteredPhoneNumber(String suspectRegisteredPhoneNumber) {
        this.suspectRegisteredPhoneNumber = suspectRegisteredPhoneNumber;
    }

    public Instant getRequestedDataStartDate() {
        return requestedDataStartDate;
    }

    public void setRequestedDataStartDate(Instant requestedDataStartDate) {
        this.requestedDataStartDate = requestedDataStartDate;
    }

    public Instant getRequestedDataEndDate() {
        return requestedDataEndDate;
    }

    public void setRequestedDataEndDate(Instant requestedDataEndDate) {
        this.requestedDataEndDate = requestedDataEndDate;
    }

    public boolean isContactInformationRequested() {
        return isContactInformationRequested;
    }

    public void setContactInformationRequested(boolean contactInformationRequested) {
        this.isContactInformationRequested = contactInformationRequested;
    }

    public boolean isMiniFeedRequested() {
        return isMiniFeedRequested;
    }

    public void setMiniFeedRequested(boolean miniFeedRequested) {
        this.isMiniFeedRequested = miniFeedRequested;
    }

    public boolean isStatusHistoryRequested() {
        return isStatusHistoryRequested;
    }

    public void setStatusHistoryRequested(boolean statusHistoryRequested) {
        this.isStatusHistoryRequested = statusHistoryRequested;
    }

    public boolean isSharesRequested() {
        return isSharesRequested;
    }

    public void setSharesRequested(boolean sharesRequested) {
        this.isSharesRequested = sharesRequested;
    }

    public boolean isNotesRequested() {
        return isNotesRequested;
    }

    public void setNotesRequested(boolean notesRequested) {
        this.isNotesRequested = notesRequested;
    }

    public boolean isWallPostingsRequested() {
        return isWallPostingsRequested;
    }

    public void setWallPostingsRequested(boolean wallPostingsRequested) {
        this.isWallPostingsRequested = wallPostingsRequested;
    }

    public boolean isFriendListRequested() {
        return isFriendListRequested;
    }

    public void setFriendListRequested(boolean friendListRequested) {
        this.isFriendListRequested = friendListRequested;
    }

    public boolean isVideosRequested() {
        return isVideosRequested;
    }

    public void setVideosRequested(boolean videosRequested) {
        this.isVideosRequested = videosRequested;
    }

    public boolean isGroupsRequested() {
        return isGroupsRequested;
    }

    public void setGroupsRequested(boolean groupsRequested) {
        this.isGroupsRequested = groupsRequested;
    }

    public boolean isPastEventsRquested() {
        return isPastEventsRquested;
    }

    public void setPastEventsRquested(boolean pastEventsRquested) {
        this.isPastEventsRquested = pastEventsRquested;
    }

    public boolean isFutureEventsRequested() {
        return isFutureEventsRequested;
    }

    public void setFutureEventsRequested(boolean futureEventsRequested) {
        this.isFutureEventsRequested = futureEventsRequested;
    }

    public boolean isPhotosRequested() {
        return isPhotosRequested;
    }

    public void setPhotosRequested(boolean photosRequested) {
        this.isPhotosRequested = photosRequested;
    }

    public boolean isPrivateMessagesRequested() {
        return isPrivateMessagesRequested;
    }

    public void setPrivateMessagesRequested(boolean privateMessagesRequested) {
        this.isPrivateMessagesRequested = privateMessagesRequested;
    }

    public boolean isGroupInfoRequested() {
        return isGroupInfoRequested;
    }

    public void setGroupInfoRequested(boolean groupInfoRequested) {
        this.isGroupInfoRequested = groupInfoRequested;
    }

    public boolean isIPLogRequested() {
        return isIPLogRequested;
    }

    public void setIPLogRequested(boolean IPLogRequested) {
        this.isIPLogRequested = IPLogRequested;
    }

    public Instant getFilterStartTime() {
        return filterStartTime;
    }

    public void setFilterStartTime(Instant filterStartTime) {
        this.filterStartTime = filterStartTime;
    }

    public Instant getFilterEndTime() {
        return filterEndTime;
    }

    public void setFilterEndTime(Instant filterEndTime) {
        this.filterEndTime = filterEndTime;
    }

    public String getFilterCommunicantsUserName() {
        return filterCommunicantsUserName;
    }

    public void setFilterCommunicantsUserName(String filterCommunicantsUserName) {
        this.filterCommunicantsUserName = filterCommunicantsUserName;
    }

    public String getFilterKeywords() {
        return filterKeywords;
    }

    public void setFilterKeywords(String filterKeywords) {
        this.filterKeywords = filterKeywords;
    }

    public String getFilterKeywordsCategory() {
        return filterKeywordsCategory;
    }

    public void setFilterKeywordsCategory(String filterKeywordsCategory) {
        this.filterKeywordsCategory = filterKeywordsCategory;
    }

    public String getFilterLocation() {
        return filterLocation;
    }

    public void setFilterLocation(String filterLocation) {
        this.filterLocation = filterLocation;
    }

    public String getSuspectRegisteredEmailAddress() {
        return suspectRegisteredEmailAddress;
    }

    public void setSuspectRegisteredEmailAddress(String suspectRegisteredEmailAddress) {
        this.suspectRegisteredEmailAddress = suspectRegisteredEmailAddress;
    }

    public String getSuspectRegisteredPhoneNumber() {
        return suspectRegisteredPhoneNumber;
    }

    public void setSuspectRegisteredPhoneNumber(String suspectRegisteredPhoneNumber) {
        this.suspectRegisteredPhoneNumber = suspectRegisteredPhoneNumber;
    }

    public Blob getWarrant() {
        return warrant;
    }

    public void setWarrant(Blob warrant) {
        this.warrant = warrant;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
