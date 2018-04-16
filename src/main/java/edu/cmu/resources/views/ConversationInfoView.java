package edu.cmu.resources.views;

import edu.cmu.db.entities.Conversation;
import io.dropwizard.views.View;

import java.util.List;

public class ConversationInfoView extends View {
    private List<Conversation> conversations;

    public ConversationInfoView(List<Conversation> conversations) {
        super(".mustache");
        this.conversations = conversations;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }
}
