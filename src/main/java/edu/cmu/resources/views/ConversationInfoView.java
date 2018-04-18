package edu.cmu.resources.views;

import edu.cmu.db.entities.Conversation;
import io.dropwizard.views.View;

import java.util.List;
import java.util.Set;

public class ConversationInfoView extends View {
    private List<Conversation> conversations;
    private int id;

    public ConversationInfoView(List<Conversation> conversations, int id) {
        super("FilterResult.mustache");
        this.conversations = conversations;
        this.id = id;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public int getId() {
        return id;
    }
}
