package edu.cmu.auth;

import edu.cmu.db.dao.TokenDAO;
import edu.cmu.db.entities.Token;
import edu.cmu.db.entities.User;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.hibernate.UnitOfWork;

import java.util.Optional;

public class UserAuthenticator implements Authenticator<String, User> {

    private TokenDAO tokenDAO;

    public UserAuthenticator(TokenDAO tokenDAO) {
        this.tokenDAO = tokenDAO;
    }

    @UnitOfWork
    @Override
    public Optional<User> authenticate(String token) {
        return tokenDAO.findUserByToken(token).map(Token::getUser);
    }
}
