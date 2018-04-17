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

    @Column(name = "startingTime")
    private Timestamp startingTime;

    @Column(name = "resultID")
    private int resultID;

    public Conversation() {
    }

    public Conversation(Timestamp startingTime, int resultID) {
        this.startingTime = startingTime;
        this.resultID = resultID;
    }

    public int getConversationID() {
        return conversationID;
    }

    public void setConversationID(int conversationID) {
        this.conversationID = conversationID;
    }

    public Timestamp getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(Timestamp startingTime) {
        this.startingTime = startingTime;
    }

    public int getResultID() {
        return resultID;
    }

    public void setResultID(int resultID) {
        this.resultID = resultID;
    }
}
