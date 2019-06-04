package com.khoros.twitterapp;

import com.khoros.twitterapp.resources.MainResource;
import com.khoros.twitterapp.services.TwitterService;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import twitter4j.TwitterFactory;

public class TwitterApp extends Application<TwitterAppConfiguration> {

    public static void main(String[] args) throws Exception {
        new TwitterApp().run(args);
    }

    @Override
    public void run(TwitterAppConfiguration configuration, Environment environment) {

        TwitterService.getInstance().setTWFactory(
                new TwitterFactory(configuration.twitter4jConfigurationBuild().build()).getInstance()
        );

        environment.jersey().register(new MainResource());

    }
}
