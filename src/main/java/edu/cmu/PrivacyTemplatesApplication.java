package edu.cmu;

import edu.cmu.health.TemplateHealthCheck;
import edu.cmu.resources.HelloWorldResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class PrivacyTemplatesApplication extends Application<PrivacyTemplatesConfiguration> {

    public static void main(final String[] args) throws Exception {
        new PrivacyTemplatesApplication().run(args);
    }

    @Override
    public String getName() {
        return "PrivacyTemplates";
    }

    @Override
    public void initialize(final Bootstrap<PrivacyTemplatesConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(PrivacyTemplatesConfiguration configuration,
                    Environment environment) {
        environment.jersey().register(
                new HelloWorldResource(
                        configuration.getTemplate(),
                        configuration.getDefaultName()
                ));

        environment.healthChecks().register("template", new TemplateHealthCheck(configuration.getTemplate()));

    }

}
