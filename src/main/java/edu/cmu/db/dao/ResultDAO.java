package edu.cmu.db.dao;

import edu.cmu.db.entities.Result;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;


/**
 *
 */
public class ResultDAO extends AbstractDAO<Result> {
    public ResultDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Result> findAll() {
        return list(namedQuery("edu.cmu.db.entities.Result.findAll"));
    }

    public Optional<Result> findById(int id) {
        return Optional.ofNullable(get(id));
    }

    public Result persistNewResult(Result result) {
        persist(result);
        return result;
    }

    public void deleteResultByID(int resultID) {
        currentSession().delete(resultID);
    }

}
