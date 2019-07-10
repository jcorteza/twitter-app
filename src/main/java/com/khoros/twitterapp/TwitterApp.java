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

        final String accessAllowedOrigin = "http://localhost:9000";
        final String accessAllowedMethods = "GET, POST";
        final String accessAllowedHeaders = "Origin, Content-Type, Method";
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("cors", CrossOriginFilter.class);
        cors.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, accessAllowedOrigin);
        cors.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_METHODS_HEADER, accessAllowedMethods);
        cors.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_HEADERS_HEADER, accessAllowedHeaders);
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, "*");

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
