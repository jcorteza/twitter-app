package com.khoros.twitterapp;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import sun.tools.java.Environment;

public class TwitterApp2 extends Application<TwitterAppConfiguration> {
    public static void main(String[] args) throws Exception {
        new TwitterApp2().run(args);
    }

    @Override
    public void initialize(Bootstrap<TwitterAppConfiguration> bootstrap) {

    }

    @Override
    public void run(TwitterAppConfiguration configuration, Environment environment) {

    }
}
