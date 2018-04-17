package edu.cmu.db.entities;

import javax.persistence.*;
import java.sql.Blob;
import java.util.List;
import java.util.Set;

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

    @Column(name = "retentionID")
    private int retentionID;

    @OneToMany
    @JoinColumn(name = "resultID")
    private List<Conversation> conversations;

    public Result() {
    }

    public Result(int SMEUserID, Blob rawResult, int retentionID) {
        this.SMEUserID = SMEUserID;
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

    public int getRetentionID() {
        return retentionID;
    }

    public void setRetentionID(int retentionID) {
        this.retentionID = retentionID;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }
}
