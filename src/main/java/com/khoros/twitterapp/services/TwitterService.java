package com.khoros.twitterapp.services;

import com.khoros.twitterapp.TwitterApp;
import twitter4j.TwitterFactory;
import twitter4j.Twitter;
import twitter4j.conf.Configuration;
import twitter4j.TwitterResponse;
import twitter4j.TwitterException;

import java.util.List;

public final class TwitterService {

    private static final TwitterService singleton = new TwitterService();
    private static final Twitter twitterFactoryRef = new TwitterFactory(TwitterApp.twConf).getInstance();
    private static Twitter twitterFactory = twitterFactoryRef;

    private TwitterService() {
        // hidden constructor
    }

    public static TwitterService getInstance() {
        return singleton;
    }

    public TwitterResponse updateStatus(String statusText) {

        try {

            return twitterFactory.updateStatus(statusText);

        } catch(TwitterException twitterException) {

            return twitterException;

        }
    }

    public TwitterResponse getHomeTimeline() {

        try {

            return twitterFactory.getHomeTimeline();

        } catch(TwitterException twitterException) {

            return twitterException;

        }
    }

    public void setTWFactory(Configuration newConf) {

        twitterFactory = new TwitterFactory(newConf).getInstance();

    }

    // mock Twitter Factory construction injection for unit testing
    public void setMockTWFactory(Twitter mockFactory) {

        twitterFactory = mockFactory;

    }

    public Twitter getFatory() {

        return twitterFactory;

    }

    public void resetTWFactory() {

        twitterFactory = twitterFactoryRef;

    }
}
