package com.khoros.twitterapp;

import com.khoros.twitterapp.resources.MainResource;
import com.khoros.twitterapp.services.TwitterService;

import dagger.Component;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import javax.inject.Singleton;

public class TwitterApp extends Application<TwitterAppConfiguration> {

    public static void main(String[] args) throws Exception {
        new TwitterApp().run(args);
    }

    @Singleton
    @Component(modules = {ServiceProviderModule.class})
    public interface ResourceComponent {
        MainResource mainResource();
    }

    @Override
    public void run(TwitterAppConfiguration configuration, Environment environment) {

        TwitterService.getInstance().setTWFactory(configuration.twitter4jConfigurationBuild().build());
        ResourceComponent resourceComponent = DaggerTwitterApp_ResourceComponent.builder()
                .serviceProviderModule(new ServiceProviderModule())
                .build;

        environment.jersey().register(resourceComponent.mainResource());
    }
}
