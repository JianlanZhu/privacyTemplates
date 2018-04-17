package edu.cmu.auth;

public class UserAuthenticatorTest {
/*
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
        TokenAuthenticator userAuthenticator = new TokenAuthenticator(userDAO);
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
        TokenAuthenticator userAuthenticator = new TokenAuthenticator(userDAO);
        try {
            Optional<User> userOptional = userAuthenticator.authenticate(new BasicCredentials(testUser1.getUserName(), "WrongPassword"));
            assertThat(userOptional).isEmpty();
        } catch (AuthenticationException e) {
            fail();
        }
    }

    @Test
    public void rejectNonExistingUser() {
        TokenAuthenticator userAuthenticator = new TokenAuthenticator(userDAO);
        try {
            Optional<User> userOptional = userAuthenticator.authenticate(new BasicCredentials("SomeUserName", "WhateverPassword"));
            assertThat(userOptional).isEmpty();
        } catch (AuthenticationException e) {
            fail();
        }
    }
    */
}