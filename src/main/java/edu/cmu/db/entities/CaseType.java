package edu.cmu.db.entities;

import javax.persistence.*;

@Entity
@Table(name = "casetype")
@NamedQueries({
        @NamedQuery(name = "edu.cmu.db.entities.CaseType.findAll",
                query = "select e from casetype e")
})
public class CaseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long caseTypeID;

    @Column(name = "caseTypeName")
    private String caseTypeName;

    @Column(name = "caseTypeDescription")
    private String caseTypeDescription;

    public CaseType() {
    }

    public CaseType(String caseTypeName, String caseTypeDescription) {
        this.caseTypeName = caseTypeName;
        this.caseTypeDescription = caseTypeDescription;
    }

    public long getCaseTypeID() {
        return caseTypeID;
    }

    public void setCaseTypeID(long caseTypeID) {
        this.caseTypeID = caseTypeID;
    }

    public String getCaseTypeName() {
        return caseTypeName;
    }

    public void setCaseTypeName(String caseTypeName) {
        this.caseTypeName = caseTypeName;
    }

    public String getCaseTypeDescription() {
        return caseTypeDescription;
    }

    public void setCaseTypeDescription(String caseTypeDescription) {
        this.caseTypeDescription = caseTypeDescription;
    }
}
