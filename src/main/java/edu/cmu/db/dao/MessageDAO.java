package edu.cmu.db.dao;

import edu.cmu.db.entities.Message;
import edu.cmu.db.entities.Request;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Responsible for database interaction regarding messages.
 */
public class MessageDAO extends AbstractDAO<Message> {
    public MessageDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Message> findAll() {
        return list(namedQuery("edu.cmu.db.entities.Message.findAll"));
    }

    public Optional<Message> findById(Long id) {
        return Optional.ofNullable(get(id));
    }

    public Message persistNewMessage(Message message) {
        persist(message);
        return message;
    }

    public void saveMessage(Message message) {

    }

}
