package edu.cmu.db.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

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

    @Column(name = "resultID")
    private int resultID;

    @Column(name = "participants")
    private String participants;

    public Conversation() {
    }

    public Conversation(int resultID) {
        this.resultID = resultID;
    }

    public int getConversationID() {
        return conversationID;
    }

    public void setConversationID(int conversationID) {
        this.conversationID = conversationID;
    }

    public int getResultID() {
        return resultID;
    }

    public void setResultID(int resultID) {
        this.resultID = resultID;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }
}
