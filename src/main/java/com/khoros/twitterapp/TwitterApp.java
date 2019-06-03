package com.khoros.twitterapp;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class TwitterApp extends Application<TwitterAppConfiguration> {

    public static void main(String[] args) throws Exception {
        new TwitterApp().run(args);
    }

    @Override
    public void run(TwitterAppConfiguration configuration, Environment environment) {

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
