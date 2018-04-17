package edu.cmu.auth;

import edu.cmu.db.entities.User;
import io.dropwizard.auth.AuthFilter;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Cookie;

@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class TokenAuthFilter extends AuthFilter<String, User> {

    public TokenAuthFilter(TokenAuthenticator authenticator, AppAuthorizer authorizer) {
        super.authenticator = authenticator;
        super.authorizer = authorizer;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        Cookie cookie = requestContext.getCookies().get("pepToken");
        if (cookie == null) {
            throw new NotAuthorizedException("No token present.");
        }

        String token = cookie.getValue();
        super.authenticate(requestContext, token, "Bearer");
    }
}