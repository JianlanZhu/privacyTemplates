package edu.cmu.db.entities;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "casetype")
@NamedQueries({
        @NamedQuery(name = "edu.cmu.db.entities.Request.findAll",
                query = "select e from request e")
})
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long requestID;

    // TODO change name
    @Column(name = "requestDate")
    private Instant requestDate;

    @Column(name = "createdByID")
    private long createdByID;

    @Column(name = "caseNumber")
    private long caseNumber;

    @ManyToOne
    @JoinColumn(name = "caseTypeId")
    private CaseType caseType;

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

    // TODO change name
    @Column(name = "requestSearchFromDate")
    private Instant requestSearchFromDate;

    // TODO change name
    @Column(name = "requestSearchThroughDate")
    private Instant requestSearchThroughDate;

    // TODO rename
    @Column(name = "suspectContactInformation")
    private boolean suspectContactInformation;

    // TODO rename
    @Column(name = "suspectMiniFeed")
    private boolean suspectMiniFeed;

    // TODO rename
    @Column(name = "suspectStatusHistory")
    private boolean suspectStatusHistory;

    // TODO rename
    @Column(name = "suspectShares")
    private boolean suspectShares;

    // TODO rename
    @Column(name = "suspectNotes")
    private boolean suspectNotes;

    // TODO rename
    @Column(name = "suspectWallPostings")
    private boolean suspectWallPostings;

    // TODO rename
    @Column(name = "suspectFriendList")
    private boolean suspectFriendList;

    // TODO rename
    @Column(name = "suspectVideoList")
    private boolean suspectVideoList;

    // TODO rename
    @Column(name = "suspectGroupList")
    private boolean suspectGroupList;

    // TODO rename
    @Column(name = "suspectPastEvents")
    private boolean suspectPastEvents;

    // TODO rename
    @Column(name = "suspectFutureEvents")
    private boolean suspectFutureEvents;

    // TODO rename
    @Column(name = "suspectPhotos")
    private boolean suspectPhotos;

    // TODO rename
    @Column(name = "suspectPrivateMessages")
    private boolean suspectPrivateMessages;

    // TODO rename
    @Column(name = "suspectGroupInfo")
    private boolean suspectGroupInfo;

    // TODO rename
    @Column(name = "suspectIPLogs")
    private boolean suspectIPLogs;

    // TODO difference to searchDateFrom?
    @Column(name = "filterStartTime")
    private Instant filterStartTime;

    // TODO difference to searchThroughDate?
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
    
    public Request() {
    }

    public Request(Instant requestDate, long createdByID, long caseNumber, CaseType caseType, String suspectUserName, String suspectLastName, String suspectFirstName, String suspectMiddleName, String suspectRegisteredEmailAddress, String suspectRegisteredPhoneNumber, Instant requestSearchFromDate, Instant requestSearchThroughDate, boolean suspectContactInformation, boolean suspectMiniFeed, boolean suspectStatusHistory, boolean suspectShares, boolean suspectNotes, boolean suspectWallPostings, boolean suspectFriendList, boolean suspectVideoList, boolean suspectGroupList, boolean suspectPastEvents, boolean suspectFutureEvents, boolean suspectPhotos, boolean suspectPrivateMessages, boolean suspectGroupInfo, boolean suspectIPLogs, Instant filterStartTime, Instant filterEndTime, String filterCommunicantsUserName, String filterKeywords, String filterKeywordsCategory, String filterLocation) {
        this.requestDate = requestDate;
        this.createdByID = createdByID;
        this.caseNumber = caseNumber;
        this.caseType = caseType;
        this.suspectUserName = suspectUserName;
        this.suspectLastName = suspectLastName;
        this.suspectFirstName = suspectFirstName;
        this.suspectMiddleName = suspectMiddleName;
        this.suspectRegisteredEmailAddress = suspectRegisteredEmailAddress;
        this.suspectRegisteredPhoneNumber = suspectRegisteredPhoneNumber;
        this.requestSearchFromDate = requestSearchFromDate;
        this.requestSearchThroughDate = requestSearchThroughDate;
        this.suspectContactInformation = suspectContactInformation;
        this.suspectMiniFeed = suspectMiniFeed;
        this.suspectStatusHistory = suspectStatusHistory;
        this.suspectShares = suspectShares;
        this.suspectNotes = suspectNotes;
        this.suspectWallPostings = suspectWallPostings;
        this.suspectFriendList = suspectFriendList;
        this.suspectVideoList = suspectVideoList;
        this.suspectGroupList = suspectGroupList;
        this.suspectPastEvents = suspectPastEvents;
        this.suspectFutureEvents = suspectFutureEvents;
        this.suspectPhotos = suspectPhotos;
        this.suspectPrivateMessages = suspectPrivateMessages;
        this.suspectGroupInfo = suspectGroupInfo;
        this.suspectIPLogs = suspectIPLogs;
        this.filterStartTime = filterStartTime;
        this.filterEndTime = filterEndTime;
        this.filterCommunicantsUserName = filterCommunicantsUserName;
        this.filterKeywords = filterKeywords;
        this.filterKeywordsCategory = filterKeywordsCategory;
        this.filterLocation = filterLocation;
    }

    public long getRequestID() {
        return requestID;
    }

    public void setRequestID(long requestID) {
        this.requestID = requestID;
    }

    public Instant getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Instant requestDate) {
        this.requestDate = requestDate;
    }

    public long getCreatedByID() {
        return createdByID;
    }

    public void setCreatedByID(long createdByID) {
        this.createdByID = createdByID;
    }

    public long getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(long caseNumber) {
        this.caseNumber = caseNumber;
    }

    public CaseType getCaseType() {
        return caseType;
    }

    public void setCaseType(CaseType caseType) {
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

    public Instant getRequestSearchFromDate() {
        return requestSearchFromDate;
    }

    public void setRequestSearchFromDate(Instant requestSearchFromDate) {
        this.requestSearchFromDate = requestSearchFromDate;
    }

    public Instant getRequestSearchThroughDate() {
        return requestSearchThroughDate;
    }

    public void setRequestSearchThroughDate(Instant requestSearchThroughDate) {
        this.requestSearchThroughDate = requestSearchThroughDate;
    }

    public boolean isSuspectContactInformation() {
        return suspectContactInformation;
    }

    public void setSuspectContactInformation(boolean suspectContactInformation) {
        this.suspectContactInformation = suspectContactInformation;
    }

    public boolean isSuspectMiniFeed() {
        return suspectMiniFeed;
    }

    public void setSuspectMiniFeed(boolean suspectMiniFeed) {
        this.suspectMiniFeed = suspectMiniFeed;
    }

    public boolean isSuspectStatusHistory() {
        return suspectStatusHistory;
    }

    public void setSuspectStatusHistory(boolean suspectStatusHistory) {
        this.suspectStatusHistory = suspectStatusHistory;
    }

    public boolean isSuspectShares() {
        return suspectShares;
    }

    public void setSuspectShares(boolean suspectShares) {
        this.suspectShares = suspectShares;
    }

    public boolean isSuspectNotes() {
        return suspectNotes;
    }

    public void setSuspectNotes(boolean suspectNotes) {
        this.suspectNotes = suspectNotes;
    }

    public boolean isSuspectWallPostings() {
        return suspectWallPostings;
    }

    public void setSuspectWallPostings(boolean suspectWallPostings) {
        this.suspectWallPostings = suspectWallPostings;
    }

    public boolean isSuspectFriendList() {
        return suspectFriendList;
    }

    public void setSuspectFriendList(boolean suspectFriendList) {
        this.suspectFriendList = suspectFriendList;
    }

    public boolean isSuspectVideoList() {
        return suspectVideoList;
    }

    public void setSuspectVideoList(boolean suspectVideoList) {
        this.suspectVideoList = suspectVideoList;
    }

    public boolean isSuspectGroupList() {
        return suspectGroupList;
    }

    public void setSuspectGroupList(boolean suspectGroupList) {
        this.suspectGroupList = suspectGroupList;
    }

    public boolean isSuspectPastEvents() {
        return suspectPastEvents;
    }

    public void setSuspectPastEvents(boolean suspectPastEvents) {
        this.suspectPastEvents = suspectPastEvents;
    }

    public boolean isSuspectFutureEvents() {
        return suspectFutureEvents;
    }

    public void setSuspectFutureEvents(boolean suspectFutureEvents) {
        this.suspectFutureEvents = suspectFutureEvents;
    }

    public boolean isSuspectPhotos() {
        return suspectPhotos;
    }

    public void setSuspectPhotos(boolean suspectPhotos) {
        this.suspectPhotos = suspectPhotos;
    }

    public boolean isSuspectPrivateMessages() {
        return suspectPrivateMessages;
    }

    public void setSuspectPrivateMessages(boolean suspectPrivateMessages) {
        this.suspectPrivateMessages = suspectPrivateMessages;
    }

    public boolean isSuspectGroupInfo() {
        return suspectGroupInfo;
    }

    public void setSuspectGroupInfo(boolean suspectGroupInfo) {
        this.suspectGroupInfo = suspectGroupInfo;
    }

    public boolean isSuspectIPLogs() {
        return suspectIPLogs;
    }

    public void setSuspectIPLogs(boolean suspectIPLogs) {
        this.suspectIPLogs = suspectIPLogs;
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
}
