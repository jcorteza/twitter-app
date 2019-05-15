package com.khoros.twitterapp;

import com.khoros.twitterapp.resources.HomeFeedResource;
import com.khoros.twitterapp.resources.StatusUpdateResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class TwitterApp extends Application<TwitterAppConfiguration> {
    public static void main(String[] args) throws Exception {
        new TwitterApp().run(args);
    }

    @Override
    public void run(TwitterAppConfiguration configuration, Environment environment) {
        StatusUpdateResource statusResource = new StatusUpdateResource()
        environment.jersey().register(new HomeFeedResource());
        environment.jersey().register(new StatusUpdateResource());
    }
}
