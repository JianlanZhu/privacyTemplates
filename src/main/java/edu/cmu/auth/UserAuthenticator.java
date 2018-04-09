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
    public Optional<User> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
        Optional<User> userOptional = userDAO.findByUsername(basicCredentials.getUsername());

        if(!userOptional.isPresent()){
            return Optional.empty();
        }

        User user = userOptional.get();
        String salt = user.getSalt();

        String hashedPassword = Hashing.sha256()
                .hashString(salt + basicCredentials.getPassword(), StandardCharsets.UTF_8)
                .toString().toUpperCase();

        if(userOptional.get().getPassword().equals(hashedPassword)){
            return userOptional;
        } else{
            return Optional.empty();
        }
    }
}
