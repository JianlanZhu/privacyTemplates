package edu.cmu.auth;

import edu.cmu.db.dao.TokenDAO;
import edu.cmu.db.entities.Token;
import edu.cmu.db.entities.User;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class TokenAuthenticatorTest {

    private TokenAuthenticator tokenAuthenticator;
    private TokenDAO tokenDAO;

    @Before
    public void setup() {
        tokenDAO = mock(TokenDAO.class);
        tokenAuthenticator = new TokenAuthenticator(tokenDAO);
    }

    @Test
    public void authenticateValidToken() {
        final String validTokenString = "validToken";
        final Instant validUntil = Instant.now().plus(Duration.ofMinutes(10));
        final User userWithValidToken = new User();
        userWithValidToken.setUserName("validUser");
        final Token validToken = new Token(userWithValidToken, validTokenString, validUntil);

        when(tokenDAO.findTokenByTokenString(validTokenString)).thenReturn(Optional.of(validToken));

        Optional<User> userOptional = tokenAuthenticator.authenticate(validTokenString);

        assertThat(userOptional).isPresent();

        User user = userOptional.get();

        assertThat(user.getUserName()).isEqualToIgnoringCase("validUser");

    }

    @Test
    public void rejectExpiredToken() {
        final String expiredTokenString = "expiredToken";
        final Instant validUntil = Instant.now().minus(Duration.ofMinutes(10));
        final User userWithExpiredToken = mock(User.class);
        final Token expiredToken = new Token(userWithExpiredToken, expiredTokenString, validUntil);

        when(tokenDAO.findTokenByTokenString(expiredTokenString)).thenReturn(Optional.of(expiredToken));

        Optional<User> userOptional = tokenAuthenticator.authenticate(expiredTokenString);

        assertThat(userOptional).isEmpty();
    }

    @Test
    public void rejectUnknownToken() {
        final String unknownTokenString = "unknownToken";

        when(tokenDAO.findTokenByTokenString(unknownTokenString)).thenReturn(Optional.empty());

        Optional<User> userOptional = tokenAuthenticator.authenticate(unknownTokenString);

        assertThat(userOptional).isEmpty();
    }
}