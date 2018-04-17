package edu.cmu.db.entities;

import javax.persistence.*;
import java.time.Instant;
import java.sql.Blob;

@Entity
@Table(name = "result")
@NamedQueries({
        @NamedQuery(name = "edu.cmu.db.entities.result.findAll",
                query = "select r from RetentionType r")
})


public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int resultID;

    @OneToOne
    @JoinColumn(name = "SMEUserID")
    private User SMEUser;

    @Column(name = "retentionID")
    private int retentionID;

    public Result() {
    }

    public int getResultID() {
        return resultID;
    }

    public void setResultID(int resultID) {
        this.resultID = resultID;
    }

    public User getSMEUser() {
        return SMEUser;
    }

    public void setSMEUser(User SMEUser) {
        this.SMEUser = SMEUser;
    }

    public int getRetentionID() {
        return retentionID;
    }

    public void setRetentionID(int retentionID) {
        this.retentionID = retentionID;
    }
}
