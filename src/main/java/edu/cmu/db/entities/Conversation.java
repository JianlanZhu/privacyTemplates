package edu.cmu.db.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "conversation")
@NamedQueries({
        @NamedQuery(name = "edu.cmu.db.entities.Conversation.findAll",
                query = "select c from Conversation c")
})

public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int conversationID;

    @OneToOne
    @JoinColumn(name = "resultID")
    private Result result;

    @Column(name = "participants")
    private String participants;

    public Conversation() {
    }

    public int getConversationID() {
        return conversationID;
    }

    public void setConversationID(int conversationID) {
        this.conversationID = conversationID;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }
}
