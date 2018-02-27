package edu.cmu;

import edu.cmu.api.EmployeesResource;
import edu.cmu.db.dao.EmployeeDAO;
import edu.cmu.db.entities.Employee;
import edu.cmu.health.TemplateHealthCheck;
import edu.cmu.resources.HelloWorldResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class PrivacyTemplatesApplication extends Application<PrivacyTemplatesConfiguration> {

    /**
     * Hibernate bundle.
     */
    private final HibernateBundle<PrivacyTemplatesConfiguration> hibernateBundle
            = new HibernateBundle<PrivacyTemplatesConfiguration>(
            Employee.class
    ) {
        @Override
        public DataSourceFactory getDataSourceFactory(
                PrivacyTemplatesConfiguration configuration
        ) {
            return configuration.getDataSourceFactory();
        }
    };

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
    }

    @Override
    public void run(PrivacyTemplatesConfiguration configuration,
                    Environment environment) {

        environment.jersey().register(new EmployeesResource(new EmployeeDAO(hibernateBundle.getSessionFactory())));

        environment.jersey().register(
                new HelloWorldResource(
                        configuration.getTemplate(),
                        configuration.getDefaultName()
                ));

        environment.healthChecks().register("template", new TemplateHealthCheck(configuration.getTemplate()));

    }

}
