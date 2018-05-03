package edu.cmu.auth;

import edu.cmu.db.dao.TokenDAO;
import edu.cmu.db.entities.Token;
import edu.cmu.db.entities.User;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.hibernate.UnitOfWork;

import java.time.Instant;
import java.util.Optional;

/**
 * This class checks whether a given token is valid, i.e., whether it is currently stored in the database and still valid.
 */
public class TokenAuthenticator implements Authenticator<String, User> {

    private TokenDAO tokenDAO;

    public TokenAuthenticator(TokenDAO tokenDAO) {
        this.tokenDAO = tokenDAO;
    }

    /**
     * Checks whether a given tokenString is present and still valid. Automatically extends a valid tokenString by an hour.
     *
     * @param tokenString
     * @return an optional of user associated with the token if the token is valid. An empty optional otherwise.
     */
    @UnitOfWork
    @Override
    public Optional<User> authenticate(String tokenString) {
        Optional<Token> tokenOptional = tokenDAO.findTokenByTokenString(tokenString).filter(t -> t.getValidUntil().compareTo(Instant.now()) > 0);
        //tokenOptional.ifPresent(this::extendToken);
        return tokenOptional.map(Token::getUser);
    }

    /**
     * Extends the token by an hour.
     *
     * @param token
     */
    private void extendToken(Token token) {
        token.setValidUntil(Instant.now().plusSeconds(3600));
    }
}
