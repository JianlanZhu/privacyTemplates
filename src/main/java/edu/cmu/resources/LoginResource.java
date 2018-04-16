package edu.cmu.resources;

import com.google.common.hash.Hashing;
import edu.cmu.core.util.SecureTokenGenerator;
import edu.cmu.db.dao.TokenDAO;
import edu.cmu.db.dao.UserDAO;
import edu.cmu.db.entities.Token;
import edu.cmu.db.entities.User;
import edu.cmu.resources.interaction.LoginInput;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Path("/login")
public class LoginResource {

    private TokenDAO tokenDAO;
    private UserDAO userDAO;

    public LoginResource(TokenDAO tokenDAO, UserDAO userDAO) {
        this.tokenDAO = tokenDAO;
        this.userDAO = userDAO;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
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

}
