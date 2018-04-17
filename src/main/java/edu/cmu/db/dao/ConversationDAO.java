package edu.cmu.db.dao;

import edu.cmu.db.entities.Conversation;
import edu.cmu.db.entities.Message;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * Responsible for database interaction regarding conversations.
 */
public class ConversationDAO extends AbstractDAO<Conversation> {
    public ConversationDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Conversation> findAll() {
        return list(namedQuery("edu.cmu.db.entities.Conversation.findAll"));
    }

    public Optional<Conversation> findById(int id) {
        return Optional.ofNullable(get(id));
    }

    public Conversation persistNewConversation(Conversation conversation) {
        persist(conversation);
        return conversation;
    }

    public List<Conversation> findByParticipant(int resultID, String participantName) {
        CriteriaBuilder builder = currentSession().getCriteriaBuilder();
        Session session = currentSession().getSessionFactory().openSession();
        Query query = session.createQuery("select * from conversation where resultID =" + resultID + " and participants like \'%" + participantName + "%\'");
        List<Conversation> conversations = query.getResultList();

        return conversations;
    }
}
