package com.khoros.twitterapp;

import com.khoros.twitterapp.resources.MainResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import twitter4j.Twitter;
import twitter4j.conf.Configuration;
import twitter4j.TwitterFactory;
import dagger.Component;

public class TwitterApp extends Application<TwitterAppConfiguration> {

    public static void main(String[] args) throws Exception {
        new TwitterApp().run(args);
    }

    @Component(modules = TwitterFactoryModule.class)
    public interface TwitterServiceComponent {
        Twitter twFactory();
    }

    @Component(
            dependencies = TwitterServiceComponent.class,
            modules = ServiceProviderModule.class
    )
    public interface ResourceComponent {
        MainResource mainResource();
    }

    @Override
    public void run(TwitterAppConfiguration configuration, Environment environment) {

        Configuration twConfig = configuration.twitter4jConfigurationBuild().build();
        Twitter twFactory = new TwitterFactory(twConfig).getInstance();
        TwitterServiceComponent twServiceComponent = DaggerTwitterApp_TwitterServiceComponent.builder()
                .twitterFactoryModule(new TwitterFactoryModule(twFactory))
                .build();
        ResourceComponent resourceComponent = DaggerTwitterApp_ResourceComponent.builder()
                .twitterServiceComponent(twServiceComponent)
                .build();

        environment.jersey().register(resourceComponent.mainResource());
    }
}
