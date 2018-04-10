package edu.cmu.db.dao;

import edu.cmu.db.entities.Conversation;
import edu.cmu.db.entities.Message;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

/**
 * Responsible for database interaction regarding conversations.
 */
public class ConversationDAO extends AbstractDAO<Conversation> {
    public ConversationDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Message> findAll() {
        return list(namedQuery("edu.cmu.db.entities.Conversation.findAll"));
    }

    public Optional<Conversation> findById(int id) {
        return Optional.ofNullable(get(id));
    }

    public Conversation persistNewConveration(Conversation conversation) {
        persist(conversation);
        return conversation;
    }
}
