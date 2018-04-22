package edu.cmu.resources;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.hash.Hashing;
import edu.cmu.core.util.SecureTokenGenerator;
import edu.cmu.db.dao.RequestDAO;
import edu.cmu.db.dao.TokenDAO;
import edu.cmu.db.dao.UserDAO;
import edu.cmu.db.entities.Token;
import edu.cmu.db.entities.User;
import edu.cmu.db.enums.UserType;
import edu.cmu.resources.interaction.LoginInput;
import edu.cmu.resources.views.LoginView;
import io.dropwizard.hibernate.UnitOfWork;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Endpoints for retrieving and destroying tokens.
 */
@Path("/")
public class LoginResource {

    private TokenDAO tokenDAO;
    private UserDAO userDAO;
    private RequestDAO requestDAO;

    public LoginResource(TokenDAO tokenDAO, UserDAO userDAO, RequestDAO requestDAO) {
        this.tokenDAO = tokenDAO;
        this.userDAO = userDAO;
        this.requestDAO = requestDAO;
    }

    @GET
    @Path("/login")
    public LoginView login(){
        return new LoginView();
    }

    /**
     * @param loginInput contains username and password
     * @return a new cookie if user is authenticated
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @Path("/login")
    public Response getAuthenticationToken(LoginInput loginInput) {
        Optional<User> userOptional = userDAO.findByUsername(loginInput.getUsername());

        if (!userOptional.isPresent()) {
            throw new NotAuthorizedException("Invalid username / password");
        }

        User user = userOptional.get();
        validateUserAuthenticity(user, loginInput.getPassword());

        String nextView = determineNextView(user);

        Token token = new Token(user, SecureTokenGenerator.nextToken(), Instant.now().plus(Duration.ofMinutes(60)));

        token = tokenDAO.persistToken(token);

        NewCookie newCookie = new NewCookie(new Cookie("pepToken", token.getToken()));

        return Response.status(Response.Status.OK).entity(nextView).cookie(newCookie).build();
    }

    @VisibleForTesting
    String determineNextView(User user) {
        if (user.getUserType().equals(UserType.LAW_ENFORCEMENT_OFFICER.name())) {
            return "http://localhost:8080/leoHome";//new LeoHomeView();
        } else if (user.getUserType().equals(UserType.SOCIAL_MEDIA_EMPLOYEE.name())) {
            return "http://localhost:8080/smeHome";//new SmeHomeView();
        } else {
            throw new BadRequestException("unknown role");
        }
    }

    @VisibleForTesting
    void validateUserAuthenticity(User user, String password) {
        String salt = user.getSalt();

        String hashedPassword = Hashing.sha256()
                .hashString(salt + password, StandardCharsets.UTF_8)
                .toString().toUpperCase();

        if (!user.getPassword().equals(hashedPassword)) {
            throw new NotAuthorizedException("Invalid username / password");
        }
    }

    /**
     * Destroys the user's token
     *
     * @param requestContext
     * @return
     */
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
        return Response.status(Response.Status.OK).entity("http://localhost:8080/login").build();
    }

}
