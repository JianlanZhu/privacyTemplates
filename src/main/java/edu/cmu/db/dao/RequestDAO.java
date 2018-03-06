package edu.cmu.db.dao;

import edu.cmu.db.entities.Employee;
import edu.cmu.db.entities.Request;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class RequestDAO extends AbstractDAO<Request> {
    /**
     * @param sessionFactory Hibernate session factory.
     */
    public RequestDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Request> findAll() {
        return list(namedQuery("cedu.cmu.db.entities.Request.findAll"));
    }

    public Optional<Request> findById(long id) {
        return Optional.ofNullable(get(id));
    }

    public Request persistNewRequest(Request request) {
        persist(request);
        return request;
    }
}
