package edu.cmu.db.dao;

import edu.cmu.db.entities.Employee;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class EmployeeDAO extends AbstractDAO<Employee> {
    /**
     * @param sessionFactory Hibernate session factory.
     */
    public EmployeeDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Employee> findAll() {
        return list(namedQuery("com.javaeeeee.dwstart.core.Employee.findAll"));
    }

    /**
     * Looks for employees whose first or last name contains the passed
     * parameter as a substring.
     *
     * @return list of employees whose first or last name contains the passed
     * parameter as a substring.
     */
    public List<Employee> findByName(String name) {
        StringBuilder builder = new StringBuilder("%");
        builder.append(name).append("%");
        return list(
                namedQuery("com.javaeeeee.dwstart.core.Employee.findByName")
                        .setParameter("name", builder.toString())
        );
    }

    public Optional<Employee> findById(long id) {
        return Optional.ofNullable(get(id));
    }
}
