package edu.cmu.db.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "message")
@NamedQueries({
        @NamedQuery(name = "edu.cmu.db.entities.Message.findAll",
                query = "select m from Message m")
})

public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long messageID;

    @Column(name = "messageContent")
    private String messageContent;

    @Column(name = "startingTime")
    private Timestamp startingTime;

    @Column(name = "messageSender")
    private String messageSender;

    @Column(name = "conversationID")
    private int conversationID;

    public Message() {
    }

    public Message(String messageContent, Timestamp startingTime, String messageSender, int conversationID) {
        this.messageContent = messageContent;
        this.startingTime = startingTime;
        this.messageSender = messageSender;
        this.conversationID = conversationID;
    }

    public long getMessageID() {
        return messageID;
    }

    public void setMessageID(long messageID) {
        this.messageID = messageID;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Timestamp getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(Timestamp startingTime) {
        this.startingTime = startingTime;
    }

    public String getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(String messageSender) {
        this.messageSender = messageSender;
    }

    public int getConversationID() {
        return conversationID;
    }

    public void setConversationID(int conversationID) {
        this.conversationID = conversationID;
    }
}
