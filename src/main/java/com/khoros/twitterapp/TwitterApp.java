package com.khoros.twitterapp;

import com.khoros.twitterapp.resources.MainResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import twitter4j.Twitter;
import dagger.Component;
import javax.inject.Singleton;

public class TwitterApp extends Application<TwitterAppConfiguration> {

    public static void main(String[] args) throws Exception {
        new TwitterApp().run(args);
    }

    @Component(modules = TwitterFactoryModule.class)
    public interface TwitterFactoryComponent {
        Twitter twFactory();
    }

    @Component(
            dependencies = TwitterFactoryComponent.class,
            modules = ServiceProviderModule.class
    )
    @Singleton
    public interface ResourceComponent {
        MainResource mainResource();
    }

    @Override
    public void run(TwitterAppConfiguration configuration, Environment environment) {

        TwitterFactoryComponent twComponent = DaggerTwitterApp_TwitterFactoryComponent.builder()
                .twitterFactoryModule(
                        new TwitterFactoryModule(
                                // Twitter Configuration Object
                                configuration.twitter4jConfigurationBuild().build()
                        )
                )
                .build();
        ResourceComponent resourceComponent = DaggerTwitterApp_ResourceComponent.builder()
                .twitterFactoryComponent(twComponent)
                .build();

        environment.jersey().register(resourceComponent.mainResource());
    }
}
