package com.khoros.twitterapp;

import com.khoros.twitterapp.resources.MainResource;

import com.khoros.twitterapp.services.TwitterService;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import twitter4j.Twitter;
import twitter4j.conf.Configuration;
import twitter4j.TwitterFactory;
import dagger.Component;

import javax.inject.Singleton;

public class TwitterApp extends Application<TwitterAppConfiguration> {

    public static void main(String[] args) throws Exception {
        new TwitterApp().run(args);
    }

    @Component(modules = ServiceProviderModule.class)
    @Singleton
    public interface ResourceComponent {
        MainResource mainResource();
    }

    @Override
    public void run(TwitterAppConfiguration configuration, Environment environment) {

        Configuration twConfig = configuration.twitter4jConfigurationBuild().build();
        Twitter twFactory = new TwitterFactory(twConfig).getInstance();
        ResourceComponent resourceComponent = DaggerTwitterApp_ResourceComponent.builder()
                .serviceProviderModule(new ServiceProviderModule(twFactory))
                .build();

        environment.jersey().register(resourceComponent.mainResource());
    }
}
