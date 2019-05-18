package com.khoros.twitterapp;

import com.khoros.twitterapp.resources.HomeFeedResource;
import com.khoros.twitterapp.resources.StatusUpdateResource;
import com.khoros.twitterapp.services.TwitterService;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class TwitterApp extends Application<TwitterAppConfiguration> {

    public static final int MAX_TWEET_LENGTH = 280;
    public static final String GENERAL_ERR_MSG = "Whoops! Something went wrong. Try again later.";

    public static void main(String[] args) throws Exception {
        new TwitterApp().run(args);
        this.app.Config =
    }

    @Override
    public void initialize(Bootstrap<TwitterAppConfiguration> bootstrap) {
        // nothing going on
    }

    @Override
    public void run(TwitterAppConfiguration configuration, Environment environment) {
        environment.jersey().register(new StatusUpdateResource());
        environment.jersey().register(new HomeFeedResource());
    }
}
