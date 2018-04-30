package edu.cmu.resources.views;

import edu.cmu.db.entities.Conversation;
import edu.cmu.db.entities.Message;
import io.dropwizard.views.View;

import java.util.List;

public class ConversationView extends View {
    private Conversation conversation;
    private long requestId;
    private List<Message> messages;

    public ConversationView(Conversation conversation, long requestId, List<Message> messages) {
        super("conversation.mustache");
        this.conversation = conversation;
        this.requestId = requestId;
        this.messages = messages;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public long getRequestId() {
        return requestId;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
