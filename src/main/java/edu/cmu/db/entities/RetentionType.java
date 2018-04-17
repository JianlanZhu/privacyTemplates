package edu.cmu.db.entities;

import javax.persistence.*;

@Entity
@Table(name = "retentiontype")
@NamedQueries({
        @NamedQuery(name = "edu.cmu.db.entities.RetentionType.findAll",
                query = "select rt from RetentionType rt")
})

public class RetentionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int retentionID;

    @Column(name = "retentionDays")
    private int retentionDays;

    public RetentionType() {
    }

    public RetentionType(int retentionDays) {
        this.retentionDays = retentionDays;
    }

    public int getRetentionID() {
        return retentionID;
    }

    public void setRetentionID(int retentionID) {
        this.retentionID = retentionID;
    }

    public int getRetentionDays() {
        return retentionDays;
    }

    public void setRetentionDays(int retentionDays) {
        this.retentionDays = retentionDays;
    }
}
