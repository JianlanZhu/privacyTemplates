package edu.cmu.resources.views;

import edu.cmu.db.entities.Conversation;
import io.dropwizard.views.View;

import java.util.List;

public class RequestDetailsView extends View {
    private List<Conversation> conversations;
    private long id;

    public RequestDetailsView(List<Conversation> conversations, long id) {
        super("requestDetails.mustache");
        this.conversations = conversations;
        this.id = id;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public long getId() {
        return id;
    }
}
