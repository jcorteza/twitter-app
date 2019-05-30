package com.khoros.twitterapp;

import com.khoros.twitterapp.resources.MainResource;

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

    @Singleton
    @Component(modules = {TwitterFactoryModule.class, ServiceProviderModule.class})
    public interface ResourceComponent {
        MainResource mainResource();
        void injectTwitterFactory(Twitter twFactory);
    }

    @Override
    public void run(TwitterAppConfiguration configuration, Environment environment) {

        Configuration twConfig = configuration.twitter4jConfigurationBuild().build();
        Twitter twFactory = new TwitterFactory(twConfig).getInstance();
        ResourceComponent resourceComponent = DaggerTwitterApp_ResourceComponent.builder().build();

        resourceComponent.injectTwitterFactory(twFactory);
        environment.jersey().register(resourceComponent.mainResource());
    }
}
