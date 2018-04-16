package edu.cmu.db.dao;

import edu.cmu.db.entities.Token;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.Optional;

/**
 * Responsible for database interaction regarding requests.
 */
public class TokenDAO extends AbstractDAO<Token> {

    public TokenDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Token persistToken(Token token) {
        persist(token);
        return token;
    }

    public Optional<Token> findUserByToken(String token) {
        return namedQuery("edu.cmu.db.entities.Token.findUserByToken").setParameter("token", token).uniqueResultOptional();
    }
}
