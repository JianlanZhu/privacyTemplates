package edu.cmu.auth;

import edu.cmu.db.dao.TokenDAO;
import edu.cmu.db.entities.Token;
import edu.cmu.db.entities.User;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.hibernate.UnitOfWork;

import java.util.Optional;

/**
 * This class checks whether a given token is valid, i.e., whether it is currently stored in the database and still valid.
 */
public class TokenAuthenticator implements Authenticator<String, User> {

    private TokenDAO tokenDAO;

    public TokenAuthenticator(TokenDAO tokenDAO) {
        this.tokenDAO = tokenDAO;
    }

    @UnitOfWork
    @Override
    public Optional<User> authenticate(String token) {
        // FIXME: check token expiration date
        Optional<Token> tokenOptional = tokenDAO.findUserByToken(token);
        return tokenOptional.map(Token::getUser);
    }
}
