package com.khoros.twitterapp.services;

import twitter4j.TwitterFactory;
import twitter4j.Twitter;
import twitter4j.conf.Configuration;
import twitter4j.TwitterResponse;
import twitter4j.TwitterException;

import java.util.List;

public final class TwitterService {

    private static final TwitterService singleton = new TwitterService();
    // private Configuration conf;
    // private Twitter twitterFactory = new TwitterFactory(conf).getInstance();
    private static Twitter twitterFactory = new TwitterFactory().getSingleton();

    private TwitterService() {
        // hidden constructor
    }

    public static TwitterService getInstance() {
        return singleton;
    }

    public static TwitterResponse updateStatus(String statusText) {

        try {

            return twitterFactory.updateStatus(statusText);

        } catch(TwitterException twitterException) {

            return twitterException;

        }
    }

    public static TwitterResponse getHomeTimeline() {

        try {

            return twitterFactory.getHomeTimeline();

        } catch(TwitterException twitterException) {

            return twitterException;

        }
    }

    public static String errorHandler(TwitterException e) {
        return "Whoops! Something went wrong. Try again later.";
    }
}
