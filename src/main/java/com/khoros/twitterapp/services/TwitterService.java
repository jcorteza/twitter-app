package com.khoros.twitterapp.services;

import twitter4j.TwitterFactory;
import twitter4j.Twitter;
import twitter4j.conf.Configuration;
import twitter4j.Status;
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

    public static Status updateStatus(String statusText) {

        try {

            return twitterFactory.updateStatus(statusText);

        } catch(TwitterException e) {

            return errorHandler(e);

        }
    }

    public static List<Status> getHomeTimeline() {

        try {

            return twitterFactory.getHomeTimeline();

        } catch(TwitterException e) {

            return errorHandler(e);

        }
    }

    public static String errorHandler(TwitterException e) {
        return "Whoops! Something went wrong. Try again later.";
    }
}
