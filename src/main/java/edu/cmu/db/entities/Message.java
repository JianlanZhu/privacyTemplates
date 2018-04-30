package edu.cmu.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;

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
    private Date startingTime;

    @Column(name = "messageSender")
    private String messageSender;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "conversationID")
    private Conversation conversation;

    public Message() {
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

    public Date getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(Date startingTime) {
        this.startingTime = startingTime;
    }

    public String getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(String messageSender) {
        this.messageSender = messageSender;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}
