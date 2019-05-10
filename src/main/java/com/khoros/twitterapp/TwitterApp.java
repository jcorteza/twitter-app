package com.khoros.twitterapp;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class TwitterApp extends Application<TwitterAppConfiguration> {
    public static void main(String[] args) throws Exception {
        new TwitterApp().run(args);
    }

    @Override
    public void run(TwitterAppConfiguration configuration, Environment environment) {
        environment.jersey().register(new HomeFeedResource());
        environment.jersey().register(new StatusUpdateResource());
    }
}
