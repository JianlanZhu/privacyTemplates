package edu.cmu.auth;

import com.google.common.hash.Hashing;
import edu.cmu.db.dao.UserDAO;
import edu.cmu.db.entities.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.basic.BasicCredentials;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class UserAuthenticatorTest {

    private static UserDAO userDAO;
    private static User testUser1;
    private static final String testUser1Password = "SS";

    @BeforeClass
    public static void setup(){
        testUser1 = new User();
        testUser1.setUserName("SaSm");
        testUser1.setSalt("SamsSalt");
        testUser1.setUserID(13);

        final String hashedAndSaltedPassword =
                Hashing.sha256().hashString(testUser1.getSalt() + testUser1Password, StandardCharsets.UTF_8)
                        .toString().toUpperCase();

        testUser1.setPassword(hashedAndSaltedPassword);

        userDAO = mock(UserDAO.class);
        when(userDAO.findByUsername(anyString())).thenReturn(Optional.empty());
        when(userDAO.findByUsername("SaSm")).thenReturn(Optional.of(testUser1));
    }

    @Test
    public void authenticateExistingUser() {
        UserAuthenticator userAuthenticator = new UserAuthenticator(userDAO);
        try {
            Optional<User> userOptional = userAuthenticator.authenticate(new BasicCredentials(testUser1.getUserName(), testUser1Password));
            assertThat(userOptional).isPresent();
            assertThat(userOptional.get().getUserName()).isEqualTo(testUser1.getUserName());
        } catch (AuthenticationException e) {
            fail();
        }
    }

    @Test
    public void rejectUserWithWrongPassword() {
        UserAuthenticator userAuthenticator = new UserAuthenticator(userDAO);
        try {
            Optional<User> userOptional = userAuthenticator.authenticate(new BasicCredentials(testUser1.getUserName(), "WrongPassword"));
            assertThat(userOptional).isEmpty();
        } catch (AuthenticationException e) {
            fail();
        }
    }

    @Test
    public void rejectNonExistingUser() {
        UserAuthenticator userAuthenticator = new UserAuthenticator(userDAO);
        try {
            Optional<User> userOptional = userAuthenticator.authenticate(new BasicCredentials("SomeUserName", "WhateverPassword"));
            assertThat(userOptional).isEmpty();
        } catch (AuthenticationException e) {
            fail();
        }
    }
}