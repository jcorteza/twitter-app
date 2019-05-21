package com.khoros.twitterapp.services;

import twitter4j.*;
import twitter4j.conf.Configuration;

import java.lang.Exception;

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

    public void setTWFactory(Twitter factory, Boolean getActualFactory) {

        if (getActualFactory) {

            twitterFactory = factory;

        } else {

            twitterFactoryRef = factory;
            twitterFactory = twitterFactoryRef;

        }

    }

    public Twitter getFactory(Boolean getActualFactory) {

        return (getActualFactory)? twitterFactory : twitterFactoryRef;

    }

}
