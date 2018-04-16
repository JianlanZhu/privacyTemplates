package edu.cmu.resources;

import edu.cmu.db.dao.ConversationDAO;
import edu.cmu.db.dao.MessageDAO;
import edu.cmu.db.dao.ResultDAO;
import edu.cmu.db.entities.Conversation;
import edu.cmu.resources.views.ConversationInfoView;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.views.View;
import jdk.nashorn.internal.objects.annotations.Getter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

public class LawEnforcementResource {
    private ResultDAO resultDAO;
    private ConversationDAO conversationDAO;
    private MessageDAO messageDAO;

    public LawEnforcementResource(ResultDAO resultDAO, ConversationDAO conversationDAO, MessageDAO messageDAO) {
        this.resultDAO = resultDAO;
        this.conversationDAO = conversationDAO;
        this.messageDAO = messageDAO;
    }

    @GET
    @Path("/filter")
    @UnitOfWork
    public View listAllConversation() {
        List<Conversation> allConversations = conversationDAO.findAll();
        return new ConversationInfoView(allConversations);
    }
}
