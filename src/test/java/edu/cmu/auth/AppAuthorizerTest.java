package edu.cmu.auth;

import edu.cmu.db.entities.User;
import edu.cmu.db.enums.UserType;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class AppAuthorizerTest {

    @Test
    public void authorize() {
        final AppAuthorizer appAuthorizer = new AppAuthorizer();

        final User leoUser = new User();
        leoUser.setUserType(UserType.LAW_ENFORCEMENT_OFFICER.name());

        final User smeUser = new User();
        smeUser.setUserType(UserType.SOCIAL_MEDIA_EMPLOYEE.name());

        final String targetRole = UserType.LAW_ENFORCEMENT_OFFICER.name();

        assertThat(appAuthorizer.authorize(smeUser, targetRole)).isFalse();
        assertThat(appAuthorizer.authorize(leoUser, targetRole)).isTrue();
    }
}