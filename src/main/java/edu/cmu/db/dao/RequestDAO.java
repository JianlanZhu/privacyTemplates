package edu.cmu.db.dao;

import edu.cmu.db.entities.Request;
import edu.cmu.db.enums.RequestState;
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

    public boolean updateStatus(long requestID, RequestState status) {
        Optional<Request> requestOptional = findById(requestID);
        if (!requestOptional.isPresent()) {
            return false;
        } else {
            Request request = requestOptional.get();
            request.setStatus(status.name());
            persist(request);
            return true;
        }
    }

    public List<Request> findAllForUser(long userID) {
        return list(namedQuery("edu.cmu.db.entities.Request.findAllForUser").setParameter("userId", userID));
    }

    public List<Request> findAllWithStatus(RequestState status) {
        return list(namedQuery("edu.cmu.db.entities.Request.findAllWithStatus").setParameter("status", status.name()));
    }
}
