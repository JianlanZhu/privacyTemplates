package edu.cmu.resources.views;

import edu.cmu.db.entities.Conversation;
import io.dropwizard.views.View;

import java.util.List;

public class RequestDetailsView extends View {
    private List<Conversation> conversations;

    public RequestDetailsView(List<Conversation> conversations) {
        super("requestDetails.mustache");
        this.conversations = conversations;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }
}
