package com.khoros.twitterapp;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class TwitterApp extends Application<TwitterAppConfiguration> {

    public static void main(String[] args) throws Exception {
        new TwitterApp().run(args);
    }

    @Override
    public void run(TwitterAppConfiguration configuration, Environment environment) {

        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("cors", CrossOriginFilter.class);
        cors.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        TwitterFactoryComponent twComponent = DaggerTwitterFactoryComponent.builder()
                .twitterFactoryModule(
                        new TwitterFactoryModule(configuration.twitter4jConfigurationBuild())
                )
                .build();
        ResourceComponent resourceComponent = DaggerResourceComponent.builder()
                .twitterFactoryComponent(twComponent)
                .build();

        environment.jersey().register(resourceComponent.mainResource());

    }
}
