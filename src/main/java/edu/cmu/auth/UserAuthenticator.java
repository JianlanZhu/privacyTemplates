package edu.cmu.auth;

import edu.cmu.db.dao.UserDAO;
import edu.cmu.db.entities.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.hibernate.UnitOfWork;

import java.util.Optional;

public class UserAuthenticator implements Authenticator<BasicCredentials, User> {

    private UserDAO userDAO;

    public UserAuthenticator(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @UnitOfWork
    @Override
    public Optional<User> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
        Optional<User> userOptional = userDAO.findByUsername(basicCredentials.getUsername());
        // FIXME store hashed password instead of plain
        if (userOptional.isPresent() && userOptional.get().getPassword().equals(basicCredentials.getPassword())) {
            return userOptional;
        } else {
            return Optional.empty();
        }
    }
}
