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

    private static final TwitterService INSTANCE = new TwitterService();
    private static Configuration twitterConfiguration;
    private static Twitter twitterFactoryRef = new TwitterFactory(twitterConfiguration).getInstance();
    private static Twitter twitterFactory = twitterFactoryRef;

    private TwitterService() {
        // hidden constructor
    }

    public static TwitterService getInstance() {
        return INSTANCE;
    }

    public Status updateStatus(String statusText) throws Exception {

            try {

                return twitterFactory.updateStatus(statusText);

            } catch (TwitterException twitterException) {

                throw new Exception();

            }

    }

    public ResponseList<Status> getHomeTimeline() throws Exception {

            try {

                return twitterFactory.getHomeTimeline();

            } catch (TwitterException twitterException) {

                throw new Exception();

            }
    }

    public void setTWFactory(Configuration newConf) {

        twitterFactory = new TwitterFactory(newConf).getInstance();

    }

    public void setTWFactory(Twitter factory) {

            twitterFactory = factory;

    }

    public Twitter getFactory(Boolean getActualFactory) {

        return (getActualFactory)? twitterFactory : twitterFactoryRef;

    }

    public void setTwitterConfiguration(Configuration config) {

        twitterConfiguration = config;

    }

}
