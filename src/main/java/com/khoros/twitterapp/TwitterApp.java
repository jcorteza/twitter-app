package com.khoros.twitterapp;

import com.khoros.twitterapp.resources.MainResource;
import com.khoros.twitterapp.services.CacheUp;
import com.khoros.twitterapp.services.TwitterService;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TwitterApp extends Application<TwitterAppConfiguration> {

    public static void main(String[] args) throws Exception {
        new TwitterApp().run(args);
    }

    @Override
    public void run(TwitterAppConfiguration configuration, Environment environment) {

        TwitterService.getInstance().setTWFactory(configuration.twitter4jConfigurationBuild().build());

        environment.jersey().register(new MainResource());

        ScheduledFuture<?> executorService = Executors
                .newScheduledThreadPool(1)
                .scheduleWithFixedDelay(CacheUp.cleanCache, 0, 5, TimeUnit.MINUTES);
    }
}
