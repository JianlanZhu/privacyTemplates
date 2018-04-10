package edu.cmu.db.dao;

import edu.cmu.db.entities.Request;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

/**
 * Responsible for database interaction regarding requests.
 */
public class RequestDAO extends AbstractDAO<Request> {

    public RequestDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Request> findAll() {
        return list(namedQuery("edu.cmu.db.entities.Request.findAll"));
    }

    public Optional<Request> findById(long id) {
        return Optional.ofNullable(get(id));
    }

    public Request persistNewRequest(Request request) {
        persist(request);
        return request;
    }
}
