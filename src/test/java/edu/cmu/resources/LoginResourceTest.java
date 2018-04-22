package edu.cmu.resources;

import edu.cmu.db.dao.RequestDAO;
import edu.cmu.db.dao.TokenDAO;
import edu.cmu.db.dao.UserDAO;
import edu.cmu.db.entities.Token;
import edu.cmu.db.entities.User;
import edu.cmu.db.enums.UserType;
import edu.cmu.resources.interaction.LoginInput;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


public class LoginResourceTest {

    private LoginResource loginResource;
    private TokenDAO tokenDAO;
    private UserDAO userDAO;
    private RequestDAO requestDAO;

    @Before
    public void setup() {
        tokenDAO = mock(TokenDAO.class);
        userDAO = mock(UserDAO.class);
        requestDAO = mock(RequestDAO.class);
        loginResource = new LoginResource(tokenDAO, userDAO, requestDAO);
    }

    @Test
    public void login() {
    }

    @Test
    public void getAuthenticationToken() {
        LoginInput validLoginInput = new LoginInput();
        validLoginInput.setUsername("SaSm");
        validLoginInput.setPassword("SS");

        LoginInput invalidLoginInput = new LoginInput();
        invalidLoginInput.setUsername("SaSm");
        invalidLoginInput.setPassword("wrongPassword");

        LoginInput nonexistentLoginInput = new LoginInput();
        nonexistentLoginInput.setUsername("NotExistentUser");
        nonexistentLoginInput.setPassword("somePassword");

        User user = new User();
        user.setUserName("SaSm");
        user.setSalt("SamsSalt");
        user.setPassword("32C6636677E3634D64DD5B73EB4D3AB78FD8AB4D19C2497176A2D6D752AF1C98");
        user.setUserType(UserType.LAW_ENFORCEMENT_OFFICER.name());

        when(userDAO.findByUsername("SaSm")).thenReturn(Optional.of(user));
        when(userDAO.findByUsername(nonexistentLoginInput.getUsername())).thenReturn(Optional.empty());

        final String tokenString = "someToken";
        when(tokenDAO.persistToken(any())).thenReturn(new Token(user, tokenString, Instant.now().plusSeconds(3600)));

        Throwable thrown = catchThrowable(() -> loginResource.getAuthenticationToken(invalidLoginInput));
        assertThat(thrown).isInstanceOf(NotAuthorizedException.class);

        thrown = catchThrowable(() -> loginResource.getAuthenticationToken(nonexistentLoginInput));
        assertThat(thrown).isInstanceOf(NotAuthorizedException.class);

        Response response = loginResource.getAuthenticationToken(validLoginInput);

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getCookies().get("pepToken").toCookie().getValue()).isEqualToIgnoringCase(tokenString);
    }

    @Test
    public void determineNextView() {

    }

    @Test
    public void validateUserAuthenticity() {
    }

    @Test
    public void logout() {

        HttpServletRequest requestContext = mock(HttpServletRequest.class);

        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("pepToken", "tokenString");
        when(requestContext.getCookies()).thenReturn(cookies);

        Response response = loginResource.logout(requestContext);

        verify(tokenDAO).deleteToken("tokenString");
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getEntity().toString()).contains("login");
    }
}