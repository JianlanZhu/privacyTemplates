package edu.cmu.resources;

import com.google.common.hash.Hashing;
import edu.cmu.core.util.SecureTokenGenerator;
import edu.cmu.db.dao.RequestDAO;
import edu.cmu.db.dao.TokenDAO;
import edu.cmu.db.dao.UserDAO;
import edu.cmu.db.entities.Token;
import edu.cmu.db.entities.User;
import edu.cmu.db.enums.UserType;
import edu.cmu.resources.interaction.LoginInput;
import edu.cmu.resources.views.LeoHomeView;
import edu.cmu.resources.views.LoginView;
import edu.cmu.resources.views.SmeHomeView;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.views.View;

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
        String salt = user.getSalt();

        String hashedPassword = Hashing.sha256()
                .hashString(salt + loginInput.getPassword(), StandardCharsets.UTF_8)
                .toString().toUpperCase();

        if (!userOptional.get().getPassword().equals(hashedPassword)) {
            throw new NotAuthorizedException("Invalid username / password");
        }

        String nextView = null;
        if (user.getUserType().equals(UserType.LAW_ENFORCEMENT_OFFICER.name())) {
            nextView = "http://localhost:8080/leoHome";//new LeoHomeView();
        } else if (user.getUserType().equals(UserType.SOCIAL_MEDIA_EMPLOYEE.name())) {
            nextView = "http://localhost:8080/smeHome";//new SmeHomeView();
        } else {
            throw new BadRequestException("unknown role");
        }

        String tokenString = SecureTokenGenerator.nextToken();
        Instant instant = Instant.now();
        instant.plus(Duration.ofMinutes(60));

        Token token = new Token(user, tokenString, instant);

        token = tokenDAO.persistToken(token);

        Cookie cookie = new Cookie("pepToken", tokenString);
        NewCookie newCookie = new NewCookie(cookie);

        return Response.status(Response.Status.OK).entity(nextView).cookie(newCookie).build();
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
