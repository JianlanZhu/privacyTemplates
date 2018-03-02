package edu.cmu.db.dao;

import edu.cmu.db.entities.Employee;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class CaseTypeDAO extends AbstractDAO<Employee> {
    /**
     * @param sessionFactory Hibernate session factory.
     */
    public CaseTypeDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Employee> findAll() {
        return list(namedQuery("cedu.cmu.db.entities.CaseType.findAll"));
    }

    public Optional<Employee> findById(long id) {
        return Optional.ofNullable(get(id));
    }
}
