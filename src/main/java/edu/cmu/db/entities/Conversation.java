package edu.cmu.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "resultID")
    private Result result;

    @Column(name = "participants")
    private String participants;

    @OneToMany
    @JoinColumn(name = "conversationID")
    private List<Message> messages;

    public Conversation() {
        this.messages = new ArrayList<>();
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

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
