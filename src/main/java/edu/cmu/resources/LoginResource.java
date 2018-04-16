package edu.cmu.resources;

import com.google.common.hash.Hashing;
import edu.cmu.core.util.SecureTokenGenerator;
import edu.cmu.db.dao.TokenDAO;
import edu.cmu.db.dao.UserDAO;
import edu.cmu.db.entities.Token;
import edu.cmu.db.entities.User;
import edu.cmu.resources.interaction.LoginInput;
import io.dropwizard.hibernate.UnitOfWork;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/login")
public class LoginResource {

    private TokenDAO tokenDAO;
    private UserDAO userDAO;

    public LoginResource(TokenDAO tokenDAO, UserDAO userDAO) {
        this.tokenDAO = tokenDAO;
        this.userDAO = userDAO;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response getAuthenticationToken(LoginInput loginInput) {
        Optional<User> userOptional = userDAO.findByUsername(loginInput.getUsername());

        if (!userOptional.isPresent()) {
            throw new NotAuthorizedException("Invalid username / password");
        }

        User user = userOptional.get();
        String salt = user.getSalt();

        String hashedPassword = Hashing.sha256()
                .hashString(salt + loginInput.getPassword(), StandardCharsets.UTF_8)
                .toString().toUpperCase();

        if (!userOptional.get().getPassword().equals(hashedPassword)) {
            throw new NotAuthorizedException("Invalid username / password");
        }

        String tokenString = SecureTokenGenerator.nextToken();
        Instant instant = Instant.now();
        instant.plus(Duration.ofMinutes(60));

        Token token = new Token(user, tokenString, instant);

        token = tokenDAO.persistToken(token);

        Cookie cookie = new Cookie("pepToken", tokenString);
        NewCookie newCookie = new NewCookie(cookie);

        return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(token).cookie(newCookie).build();
    }

    @POST
    @Path("/logout")
    @UnitOfWork
    public Response logout(@Context HttpServletRequest requestContext) {
        javax.servlet.http.Cookie[] cookies = requestContext.getCookies();
        if (cookies != null) {
            List<String> tokens = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("pepToken")).map(javax.servlet.http.Cookie::getValue).collect(Collectors.toList());
            tokens.forEach(token -> {
                System.out.println("" + token);
                tokenDAO.deleteToken(token);
            });
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
