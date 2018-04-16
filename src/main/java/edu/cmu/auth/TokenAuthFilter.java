package edu.cmu.auth;

import edu.cmu.db.entities.User;
import io.dropwizard.auth.AuthFilter;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.PreMatching;

@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class TokenAuthFilter extends AuthFilter<String, User> {

    public TokenAuthFilter(TokenAuthenticator authenticator, AppAuthorizer authorizer) {
        super.authenticator = authenticator;
        super.authorizer = authorizer;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String token = requestContext.getCookies().get("pepToken").getValue();
        if (token == null) {
            throw new NotAuthorizedException("No token present.");
        }
        super.authenticate(requestContext, token, "Bearer");
    }
}