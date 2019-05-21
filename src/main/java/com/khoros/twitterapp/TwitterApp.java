package com.khoros.twitterapp;

import com.khoros.twitterapp.resources.MainResource;
import com.khoros.twitterapp.services.TwitterService;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import twitter4j.TwitterFactory;

public class TwitterApp extends Application<TwitterAppConfiguration> {

    public static final int MAX_TWEET_LENGTH = 280;
    public static final String GENERAL_ERR_MSG = "Whoops! Something went wrong. Try again later.";

    public static void main(String[] args) throws Exception {
        new TwitterApp().run(args);
    }

    @Override
    public void run(TwitterAppConfiguration configuration, Environment environment) {

        TwitterService.getInstance().setTwitterConfiguration(configuration.twitter4jConfigurationBuild().build());

        environment.jersey().register(new MainResource());
    }
}
