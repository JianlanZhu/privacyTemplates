package edu.cmu;

import edu.cmu.db.dao.RequestDAO;
import edu.cmu.db.entities.CaseType;
import edu.cmu.db.entities.Request;
import edu.cmu.resources.EmployeesResource;
import edu.cmu.db.dao.EmployeeDAO;
import edu.cmu.db.entities.Employee;
import edu.cmu.health.TemplateHealthCheck;
import edu.cmu.resources.HelloWorldResource;
import edu.cmu.resources.RequestResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.hibernate.SessionFactory;

public class PrivacyTemplatesApplication extends Application<PrivacyTemplatesConfiguration> {

    /**
     * Hibernate bundle.
     */
    private final HibernateBundle<PrivacyTemplatesConfiguration> hibernateBundle
            = new HibernateBundle<PrivacyTemplatesConfiguration>(
            Employee.class,
            Request.class,
            CaseType.class
    ) {
        @Override
        public DataSourceFactory getDataSourceFactory(
                PrivacyTemplatesConfiguration configuration
        ) {
            return configuration.getDataSourceFactory();
        }
    };

    private final AssetsBundle assetsBundle = new AssetsBundle("/assets", "/", "index.html");

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
    }

    @Override
    public void run(PrivacyTemplatesConfiguration configuration,
                    Environment environment) {

        SessionFactory sessionFactory = hibernateBundle.getSessionFactory();

        environment.jersey().register(new EmployeesResource(new EmployeeDAO(sessionFactory)));
        environment.jersey().register(new RequestResource(new RequestDAO(sessionFactory)));

        environment.jersey().register(
                new HelloWorldResource(
                        configuration.getTemplate(),
                        configuration.getDefaultName()
                ));

        environment.healthChecks().register("template", new TemplateHealthCheck(configuration.getTemplate()));

    }

}
