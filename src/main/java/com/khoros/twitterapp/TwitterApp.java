package com.khoros.twitterapp;

import com.khoros.twitterapp.resources.HomeFeedResource;
import com.khoros.twitterapp.resources.MainResource;
import com.khoros.twitterapp.resources.StatusUpdateResource;

import com.khoros.twitterapp.services.TwitterService;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class TwitterApp extends Application<TwitterAppConfiguration> {

    public static final int MAX_TWEET_LENGTH = 280;
    public static final String GENERAL_ERR_MSG = "Whoops! Something went wrong. Try again later.";

    public static void main(String[] args) throws Exception {
        new TwitterApp().run(args);
    }

    @Override
    public void run(TwitterAppConfiguration configuration, Environment environment) {

        TwitterService.setTwitterFactoryRef(configuration.twitter4jConfigurationBuild());

        environment.jersey().register(new MainResource());
        // environment.jersey().register(new StatusUpdateResource());
        // environment.jersey().register(new HomeFeedResource());
    }
}
