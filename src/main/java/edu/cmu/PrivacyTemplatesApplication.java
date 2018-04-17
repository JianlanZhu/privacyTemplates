package edu.cmu;

import edu.cmu.auth.AppAuthorizer;
import edu.cmu.auth.UserAuthenticator;
import edu.cmu.db.dao.RequestDAO;
import edu.cmu.db.dao.TokenDAO;
import edu.cmu.db.dao.UserDAO;
import edu.cmu.db.entities.Request;
import edu.cmu.db.entities.Token;
import edu.cmu.db.entities.User;
import edu.cmu.resources.LandingPageResource;
import edu.cmu.resources.LoginResource;
import edu.cmu.resources.RequestResource;
import edu.cmu.resources.SocialMediaResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.eclipse.jetty.server.session.SessionHandler;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.hibernate.SessionFactory;

public class PrivacyTemplatesApplication extends Application<PrivacyTemplatesConfiguration> {

    /**
     * Introduces Hibernate to the application. Needs to list all entities that are to be used.
     */
    private final HibernateBundle<PrivacyTemplatesConfiguration> hibernateBundle
            = new HibernateBundle<PrivacyTemplatesConfiguration>(
            Request.class,
            User.class,
            Token.class
    ) {
        @Override
        public DataSourceFactory getDataSourceFactory(
                PrivacyTemplatesConfiguration configuration
        ) {
            return configuration.getDataSourceFactory();
        }
    };

    /**
     * Enables serving static assets.
     */
    private final AssetsBundle viewAssets = new AssetsBundle("/assets", "/assets");

    /**
     * Main entry point.
     */
    public static void main(final String[] args) throws Exception {
        new PrivacyTemplatesApplication().run(args);
    }

    @Override
    public String getName() {
        return "PrivacyTemplates";
    }

    @Override
    public void initialize(final Bootstrap<PrivacyTemplatesConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(viewAssets);
        bootstrap.addBundle(new MultiPartBundle());
        bootstrap.addBundle(new ViewBundle<>());
    }

    /**
     * Starts the whole application. Resources (i.e., endpoints) need to be regstered here.
     *
     * @param configuration
     * @param environment
     */
    @Override
    public void run(PrivacyTemplatesConfiguration configuration,
                    Environment environment) {

        SessionFactory sessionFactory = hibernateBundle.getSessionFactory();
        RequestDAO requestDAO = new RequestDAO(sessionFactory);
        TokenDAO tokenDAO = new TokenDAO(sessionFactory);
        UserDAO userDAO = new UserDAO(sessionFactory);

        environment.jersey().register(new RequestResource(requestDAO));
        environment.jersey().register(new LoginResource(tokenDAO, userDAO));
        environment.jersey().register(new LandingPageResource());
        environment.jersey().register(new SocialMediaResource(requestDAO));

        UserAuthenticator userAuthenticator = new UnitOfWorkAwareProxyFactory(hibernateBundle)
                .create(UserAuthenticator.class, TokenDAO.class, new TokenDAO(sessionFactory));

        environment.jersey().register(
                new AuthDynamicFeature(
                        new OAuthCredentialAuthFilter.Builder<User>()
                                .setAuthenticator(userAuthenticator)
                                .setAuthorizer(new AppAuthorizer())
                                .setPrefix("Bearer")
                                .buildAuthFilter()
                )
        );

        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

        environment.jersey().register(new JsonProcessingExceptionMapper(true));

    }

}
