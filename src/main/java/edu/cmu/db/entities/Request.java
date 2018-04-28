package edu.cmu.db.entities;

import edu.cmu.db.enums.RequestState;

import javax.persistence.*;
import java.sql.Date;
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
    private Date requestedDataStartDate;

    @Column(name = "requestedDataEndDate")
    private Date requestedDataEndDate;

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

    @Column(name = "socialMediaComment")
    private String socialMediaComment;

    @OneToOne
    @JoinColumn(name = "resultID")
    Result result;

    public Request() {
    }

    public Request(User createdBy, long caseID, String caseType, Date requestedDataStartDate, Date requestedDataEndDate, RequestState status) {
        this.createdBy = createdBy;
        this.caseID = caseID;
        this.caseType = caseType;
        this.requestCreatedDate = Instant.now();
        this.requestedDataStartDate = requestedDataStartDate;
        this.requestedDataEndDate = requestedDataEndDate;
        this.status = status.name();
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

    public void setCreatedBy(User createdBy) {
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

    public Date getRequestedDataStartDate() {
        return requestedDataStartDate;
    }

    public void setRequestedDataStartDate(Date requestedDataStartDate) {
        this.requestedDataStartDate = requestedDataStartDate;
    }

    public Date getRequestedDataEndDate() {
        return requestedDataEndDate;
    }

    public void setRequestedDataEndDate(Date requestedDataEndDate) {
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

    public String getSocialMediaComment() {
        return socialMediaComment;
    }

    public void setSocialMediaComment(String socialMediaComment) {
        this.socialMediaComment = socialMediaComment;
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
