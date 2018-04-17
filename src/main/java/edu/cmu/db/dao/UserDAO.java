package edu.cmu.db.dao;

import edu.cmu.db.entities.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

/**
 * Responsible for database interaction regarding requests.
 */
public class UserDAO extends AbstractDAO<User> {

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<User> findAll() {
        return list(namedQuery("edu.cmu.db.entities.User.findAll"));
    }

    public Optional<User> findById(long id) {
        return Optional.ofNullable(get(id));
    }

    public User persistNewUser(User user) {
        persist(user);
        return user;
    }

    public Optional<User> findByUsername(String userName) {
        return namedQuery("edu.cmu.db.entities.User.findByUsername").setParameter("userName", userName).uniqueResultOptional();
    }
}
