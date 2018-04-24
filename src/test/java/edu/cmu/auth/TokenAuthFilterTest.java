package edu.cmu.auth;

import org.junit.Test;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;


public class TokenAuthFilterTest {

    @Test
    public void rejectCookielessRequest() {
        AppAuthorizer appAuthorizer = mock(AppAuthorizer.class);
        TokenAuthenticator tokenAuthenticator = mock(TokenAuthenticator.class);
        TokenAuthFilter tokenAuthFilter = new TokenAuthFilter(tokenAuthenticator, appAuthorizer);

        Map<String, Cookie> cookies = new HashMap<>();

        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getCookies()).thenReturn(cookies);

        Throwable thrown = catchThrowable(() -> tokenAuthFilter.filter(containerRequestContext));
        assertThat(thrown).isInstanceOf(NotAuthorizedException.class);
    }

    @Test
    public void rejectRequestWithoutAuthCookie() {
        AppAuthorizer appAuthorizer = mock(AppAuthorizer.class);
        TokenAuthenticator tokenAuthenticator = mock(TokenAuthenticator.class);
        TokenAuthFilter tokenAuthFilter = new TokenAuthFilter(tokenAuthenticator, appAuthorizer);

        Map<String, Cookie> cookies = new HashMap<>();
        cookies.put("someOtherCOokie", new Cookie("notPepToken", "someValue"));

        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getCookies()).thenReturn(cookies);

        Throwable thrown = catchThrowable(() -> tokenAuthFilter.filter(containerRequestContext));
        assertThat(thrown).isInstanceOf(NotAuthorizedException.class);
    }

    @Test
    public void passOnRequestWithFittingCookie() {
        AppAuthorizer appAuthorizer = mock(AppAuthorizer.class);
        TokenAuthenticator tokenAuthenticator = mock(TokenAuthenticator.class);
        // returning empty Optional to avoid mocking the whole ContainerRequestContext
        when(tokenAuthenticator.authenticate(any())).thenReturn(Optional.empty());
        TokenAuthFilter tokenAuthFilter = new TokenAuthFilter(tokenAuthenticator, appAuthorizer);

        Map<String, Cookie> cookies = new HashMap<>();
        cookies.put("pepToken", new Cookie("pepToken", "tokenString"));

        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getCookies()).thenReturn(cookies);

        // counts as passed if no exception is thrown
        tokenAuthFilter.filter(containerRequestContext);
    }
}