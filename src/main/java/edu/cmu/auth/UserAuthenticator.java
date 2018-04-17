package edu.cmu.auth;

import com.google.common.hash.Hashing;
import edu.cmu.db.dao.UserDAO;
import edu.cmu.db.entities.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.hibernate.UnitOfWork;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class UserAuthenticator implements Authenticator<BasicCredentials, User> {

    private UserDAO userDAO;

    public UserAuthenticator(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @UnitOfWork
    @Override
    public Optional<User> authenticate(String token) {
        return tokenDAO.findUserByToken(token).map(Token::getUser);
    }
}
