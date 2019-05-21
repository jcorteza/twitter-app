package com.khoros.twitterapp.services;

import twitter4j.TwitterFactory;
import twitter4j.Twitter;
import twitter4j.conf.Configuration;
import twitter4j.TwitterResponse;
import twitter4j.TwitterException;

import java.util.List;

public final class TwitterService {

    private static final TwitterService INSTANCE = new TwitterService();
    private static Twitter twitterFactoryRef;
    private static Twitter twitterFactory;

    private TwitterService() {
        // hidden constructor
    }

    public static TwitterService getInstance() {
        return INSTANCE;
    }

    public static void setTwitterFactoryRef(Configuration originalConfig) {

        twitterFactoryRef = new TwitterFactory(originalConfig).getInstance();
        twitterFactory = twitterFactoryRef;
    }

    public TwitterResponse updateStatus(String statusText) throws TwitterException {

            return twitterFactory.updateStatus(statusText);

    }

    public TwitterResponse getHomeTimeline() throws TwitterException {

            return twitterFactory.getHomeTimeline();
    }

    public void setTWFactory(Configuration newConf) {

        twitterFactory = new TwitterFactory(newConf).getInstance();

    }

    // mock Twitter Factory construction injection for unit testing
    public void setMockTWFactory(Twitter mockFactory) {

        twitterFactory = mockFactory;

    }

    public Twitter getFactory() {

        return twitterFactory;

    }

    // method for resetting twitterFactory back to original config after a change
    public void resetTWFactory() {

        twitterFactory = twitterFactoryRef;

    }
}
