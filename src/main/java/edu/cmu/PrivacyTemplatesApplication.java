package edu.cmu;

import edu.cmu.db.dao.RequestDAO;
import edu.cmu.db.entities.Request;
import edu.cmu.db.entities.User;
import edu.cmu.health.TemplateHealthCheck;
import edu.cmu.resources.RequestResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.hibernate.SessionFactory;

public class PrivacyTemplatesApplication extends Application<PrivacyTemplatesConfiguration> {

    /**
     * Introduces Hibernate to the application. Needs to list all entities that are to be used.
     */
    private final HibernateBundle<PrivacyTemplatesConfiguration> hibernateBundle
            = new HibernateBundle<PrivacyTemplatesConfiguration>(
            Request.class,
            User.class
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
    private final AssetsBundle assetsBundle = new AssetsBundle("/assets", "/", "index.jsp");

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
        bootstrap.addBundle(assetsBundle);
        bootstrap.addBundle(new MultiPartBundle());
    }

    /**
     * Starts the whole application. Resources (i.e., endpoints) need to be regstered here.
     * @param configuration
     * @param environment
     */
    @Override
    public void run(PrivacyTemplatesConfiguration configuration,
                    Environment environment) {

        SessionFactory sessionFactory = hibernateBundle.getSessionFactory();

        environment.jersey().register(new RequestResource(new RequestDAO(sessionFactory)));

        environment.healthChecks().register("template", new TemplateHealthCheck(configuration.getTemplate()));

        environment.jersey().register(new JsonProcessingExceptionMapper(true));

    }

}
