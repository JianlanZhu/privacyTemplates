package edu.cmu.db.entities;

import javax.persistence.*;
import java.sql.Blob;
import java.util.ArrayList;
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

    @OneToOne
    @JoinColumn(name = "SMEUserID")
    private User SMEUser;

    @Column(name = "retentionID")
    private int retentionID;

    @OneToMany
    @JoinColumn(name = "resultID")
    private List<Conversation> conversations;

    public Result() {
        this.conversations = new ArrayList<>();
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

    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }
}
