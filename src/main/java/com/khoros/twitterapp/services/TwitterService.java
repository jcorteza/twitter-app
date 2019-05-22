package com.khoros.twitterapp.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Twitter;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.ResponseList;
import twitter4j.conf.Configuration;

import java.lang.Exception;

public final class TwitterService {

    final Logger logger = LoggerFactory.getLogger(TwitterService.class);

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

    public Status updateStatus(String statusText) throws TwitterServiceException {

        logger.info("Attempting to update status through Twitter API.");

        if (statusText.length() == 0) {

            logger.info("Twitter status update unsuccessful.");

            throw new TwitterServiceException(TwitterService.NO_TWEET_TEXT_MSG);

        } else if (statusText.length() > TwitterService.MAX_TWEET_LENGTH) {

            logger.info("Twitter status update unsuccessful.");

            throw new TwitterServiceException(TwitterService.TWEET_TOO_LONG_MSG);

        } else {

            try {

                return twitterFactory.updateStatus(statusText);

            } catch (TwitterException twitterException) {

                logger.info("Twitter status update aborted. Twitter Exception thrown.");

                if (twitterException.isErrorMessageAvailable()) {

                    logger.error("Twitter Exception — Error Message: {} — Exception Code: {}",
                            twitterException.getErrorMessage(),
                            twitterException.getExceptionCode(),
                            twitterException);

                } else {

                    logger.error("Unknown Twitter Exception — Exception Code: {}",
                            twitterException.getExceptionCode(),
                            twitterException);

                }

                throw new TwitterServiceException("Twitter Exception thrown.", twitterException);

            }
        }
    }

    public ResponseList<Status> getHomeTimeline() throws TwitterServiceException {

        logger.info("Attempting to retrieve home timeline through Twitter API.");

        try {

            return twitterFactory.getHomeTimeline();

        } catch (TwitterException twitterException) {

            logger.info("Timeline retrieval aborted. Twitter Exception thrown." );

            if (twitterException.isErrorMessageAvailable()) {

                logger.error("Twitter Exception — Error Message: {} — Exception Code: {}",
                        twitterException.getErrorMessage(),
                        twitterException.getExceptionCode(),
                        twitterException);

            } else {

                logger.error("Unknown Twitter Exception — Exception Code: {}",
                        twitterException.getExceptionCode(),
                        twitterException);

            }

            throw new TwitterServiceException("Twitter Exception thrown.", twitterException);
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

        logger.debug("Twitter Configuration setup: {}", config);

        twitterConfiguration = config;
        twitterFactoryRef = new TwitterFactory(config).getInstance();
        twitterFactory = twitterFactoryRef;

    }

}
