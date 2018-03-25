package edu.cmu.auth;

import edu.cmu.db.entities.User;
import io.dropwizard.auth.Authorizer;

public class AppAuthorizer implements Authorizer<User>
{
    @Override
    public boolean authorize(User user, String role) {
        return user.getUserType() != null && user.getUserType().equals(role);
    }
}
