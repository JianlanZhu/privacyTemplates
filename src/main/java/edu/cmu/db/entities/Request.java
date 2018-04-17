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
                query = "select r from Request r"),
        @NamedQuery(name = "edu.cmu.db.entities.Request.findAllForUser",
                query = "select r from Request r where createdByID = :userId"),
        @NamedQuery(name = "edu.cmu.db.entities.Request.findAllWithStatus",
                query = "select r from Request r where status    = :status")
})
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long requestID;

    @Column(name = "requestCreatedDate")
    private Instant requestCreatedDate;

    @ManyToOne
    @JoinColumn(name = "createdByID")
    private User createdBy;

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
    private Boolean isContactInformationRequested;

    @Column(name = "isMiniFeedRequested")
    private Boolean isMiniFeedRequested;

    @Column(name = "isStatusHistoryRequested")
    private Boolean isStatusHistoryRequested;

    @Column(name = "isSharesRequested")
    private Boolean isSharesRequested;

    @Column(name = "isNotesRequested")
    private Boolean isNotesRequested;

    @Column(name = "isWallPostingsRequested")
    private Boolean isWallPostingsRequested;

    @Column(name = "isFriendListRequested")
    private Boolean isFriendListRequested;

    @Column(name = "isVideosRequested")
    private Boolean isVideosRequested;

    @Column(name = "isGroupsRequested")
    private Boolean isGroupsRequested;

    @Column(name = "isPastEventsRquested")
    private Boolean isPastEventsRquested;

    @Column(name = "isFutureEventsRequested")
    private Boolean isFutureEventsRequested;

    @Column(name = "isPhotosRequested")
    private Boolean isPhotosRequested;

    @Column(name = "isPrivateMessagesRequested")
    private Boolean isPrivateMessagesRequested;

    @Column(name = "isGroupInfoRequested")
    private Boolean isGroupInfoRequested;

    @Column(name = "isIPLogRequested")
    private Boolean isIPLogRequested;

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
    private Integer filterKeywordsCategory;

    @Column(name = "filterLocation")
    private String filterLocation;

    @Column(name = "status")
    private String status;

    @JsonIgnore
    @Column(name = "warrant")
    private Blob warrant;

    @OneToOne
    @JoinColumn(name = "resultID")
    Result result;

    public Request() {
    }

    public Request(User createdBy, long caseID, String caseType, String suspectUserName, String suspectLastName, String suspectFirstName, String suspectMiddleName, String suspectRegisteredEmailAddress, String suspectRegisteredPhoneNumber, Instant requestedDataStartDate, Instant requestedDataEndDate, Boolean isContactInformationRequested, Boolean isMiniFeedRequested, Boolean isStatusHistoryRequested, Boolean isSharesRequested, Boolean isNotesRequested, Boolean isWallPostingsRequested, Boolean isFriendListRequested, Boolean isVideosRequested, Boolean isGroupsRequested, Boolean isPastEventsRquested, Boolean isFutureEventsRequested, Boolean isPhotosRequested, Boolean isPrivateMessagesRequested, Boolean isGroupInfoRequested, Boolean isIPLogRequested, Instant filterStartTime, Instant filterEndTime, String filterCommunicantsUserName, String filterKeywords, Integer filterKeywordsCategory, String filterLocation, Blob warrant) {
        this.requestCreatedDate = Instant.now();
        this.createdBy = createdBy;
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedByID(User createdBy) {
        this.createdBy = createdBy;
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

    public Boolean isContactInformationRequested() {
        return isContactInformationRequested;
    }

    public void setContactInformationRequested(Boolean contactInformationRequested) {
        this.isContactInformationRequested = contactInformationRequested;
    }

    public Boolean isMiniFeedRequested() {
        return isMiniFeedRequested;
    }

    public void setMiniFeedRequested(Boolean miniFeedRequested) {
        this.isMiniFeedRequested = miniFeedRequested;
    }

    public Boolean isStatusHistoryRequested() {
        return isStatusHistoryRequested;
    }

    public void setStatusHistoryRequested(Boolean statusHistoryRequested) {
        this.isStatusHistoryRequested = statusHistoryRequested;
    }

    public Boolean isSharesRequested() {
        return isSharesRequested;
    }

    public void setSharesRequested(Boolean sharesRequested) {
        this.isSharesRequested = sharesRequested;
    }

    public Boolean isNotesRequested() {
        return isNotesRequested;
    }

    public void setNotesRequested(Boolean notesRequested) {
        this.isNotesRequested = notesRequested;
    }

    public Boolean isWallPostingsRequested() {
        return isWallPostingsRequested;
    }

    public void setWallPostingsRequested(Boolean wallPostingsRequested) {
        this.isWallPostingsRequested = wallPostingsRequested;
    }

    public Boolean isFriendListRequested() {
        return isFriendListRequested;
    }

    public void setFriendListRequested(Boolean friendListRequested) {
        this.isFriendListRequested = friendListRequested;
    }

    public Boolean isVideosRequested() {
        return isVideosRequested;
    }

    public void setVideosRequested(Boolean videosRequested) {
        this.isVideosRequested = videosRequested;
    }

    public Boolean isGroupsRequested() {
        return isGroupsRequested;
    }

    public void setGroupsRequested(Boolean groupsRequested) {
        this.isGroupsRequested = groupsRequested;
    }

    public Boolean isPastEventsRquested() {
        return isPastEventsRquested;
    }

    public void setPastEventsRquested(Boolean pastEventsRquested) {
        this.isPastEventsRquested = pastEventsRquested;
    }

    public Boolean isFutureEventsRequested() {
        return isFutureEventsRequested;
    }

    public void setFutureEventsRequested(Boolean futureEventsRequested) {
        this.isFutureEventsRequested = futureEventsRequested;
    }

    public Boolean isPhotosRequested() {
        return isPhotosRequested;
    }

    public void setPhotosRequested(Boolean photosRequested) {
        this.isPhotosRequested = photosRequested;
    }

    public Boolean isPrivateMessagesRequested() {
        return isPrivateMessagesRequested;
    }

    public void setPrivateMessagesRequested(Boolean privateMessagesRequested) {
        this.isPrivateMessagesRequested = privateMessagesRequested;
    }

    public Boolean isGroupInfoRequested() {
        return isGroupInfoRequested;
    }

    public void setGroupInfoRequested(Boolean groupInfoRequested) {
        this.isGroupInfoRequested = groupInfoRequested;
    }

    public Boolean isIPLogRequested() {
        return isIPLogRequested;
    }

    public void setIPLogRequested(Boolean IPLogRequested) {
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

    public Integer getFilterKeywordsCategory() {
        return filterKeywordsCategory;
    }

    public void setFilterKeywordsCategory(Integer filterKeywordsCategory) {
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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
