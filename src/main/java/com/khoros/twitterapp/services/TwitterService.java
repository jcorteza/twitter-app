package com.khoros.twitterapp.services;

import com.khoros.twitterapp.TwitterAppConfiguration;
import com.khoros.twitterapp.TwitterAuthorization;
import twitter4j.Twitter;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.ResponseList;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.lang.Exception;

public final class TwitterService {

    public static final int MAX_TWEET_LENGTH = 280;
    public static final String GENERAL_ERR_MSG = "Whoops! Something went wrong. Try again later.";
    public static final String NO_TWEET_TEXT_MSG = "No tweet text entered.";
    public static final String TWEET_TOO_LONG_MSG = "Tweet text surpassed " + TwitterService.MAX_TWEET_LENGTH + " characters.";
    private static final TwitterService INSTANCE = new TwitterService();
    private static Configuration twitterConfiguration;
    private static Twitter twitterFactoryRef;
    private static Twitter twitterFactory;

    private TwitterService() {
        // hidden constructor
    }

    public static TwitterService getInstance() {
        return INSTANCE;
    }

    public Status updateStatus(String statusText) throws Exception {

        if (statusText.length() == 0) {

            throw new Exception(TwitterService.NO_TWEET_TEXT_MSG);

        } else if (statusText.length() > TwitterService.MAX_TWEET_LENGTH) {

            throw new Exception(TwitterService.TWEET_TOO_LONG_MSG);

        } else {

            try {

                return twitterFactory.updateStatus(statusText);

            } catch (TwitterException twitterException) {

                throw new Exception("Twitter Exception thrown.", twitterException);

            }
        }
    }

    public ResponseList<Status> getHomeTimeline() throws Exception {

            try {

                return twitterFactory.getHomeTimeline();

            } catch (TwitterException twitterException) {

                throw new Exception("Twitter Exception thrown.", twitterException);
            }
    }

    public void setTWFactory(Configuration newConf) {

        twitterFactory = new TwitterFactory(newConf).getInstance();

    }

    public void setTWFactory(Twitter factory) {

            twitterFactory = factory;

    }

    public Twitter getTwitterFactoryRef() {

        return twitterFactoryRef;

    }

    public Twitter getTwitterFactory() {

        return twitterFactory;

    }

    public void setTwitterConfiguration(Configuration config) {

        twitterConfiguration = config;
        twitterFactoryRef = new TwitterFactory(config).getInstance();
        twitterFactory = twitterFactoryRef;

    }

}
