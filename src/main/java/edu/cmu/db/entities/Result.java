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

    @Column(name = "SMEUserID")
    private int SMEUserID;

    @Column(name = "rawResult")
    private Blob rawResult;

    @Column(name = "retentionID")
    private int retentionID;

    public Result() {
    }

    public Result(int SMEUserID, Blob rawResult, int retentionID) {
        this.SMEUserID = SMEUserID;
        this.rawResult = rawResult;
        this.retentionID = retentionID;
    }

    public int getResultID() {
        return resultID;
    }

    public void setResultID(int resultID) {
        this.resultID = resultID;
    }

    public int getSMEUserID() {
        return SMEUserID;
    }

    public void setSMEUserID(int SMEUserID) {
        this.SMEUserID = SMEUserID;
    }

    public Blob getRawResult() {
        return rawResult;
    }

    public void setRawResult(Blob rawResult) {
        this.rawResult = rawResult;
    }

    public int getRetentionID() {
        return retentionID;
    }

    public void setRetentionID(int retentionID) {
        this.retentionID = retentionID;
    }
}
