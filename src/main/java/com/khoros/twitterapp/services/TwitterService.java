package com.khoros.twitterapp.services;

import com.khoros.twitterapp.models.Status;
import com.khoros.twitterapp.models.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public final class TwitterService {

    final Logger logger = LoggerFactory.getLogger(TwitterService.class);

    public static final int MAX_TWEET_LENGTH = 280;
    public static final String GENERAL_ERR_MSG = "Whoops! Something went wrong. Try again later.";
    public static final String NO_TWEET_TEXT_MSG = "No tweet text entered.";
    public static final String TWEET_TOO_LONG_MSG = "Tweet text surpassed " + TwitterService.MAX_TWEET_LENGTH + " characters.";
    private static final TwitterService INSTANCE = new TwitterService();
    private static Twitter twitterFactory;

    private TwitterService() {
        // hidden constructor
    }

    public static TwitterService getInstance() {
        return INSTANCE;
    }

    public Optional<Status> updateStatus(String statusText) throws TwitterServiceException {

        logger.info("Attempting to update status through Twitter API.");

        if (StringUtils.isEmpty(statusText)) {

            logger.info("Twitter status update unsuccessful.");

            throw new TwitterServiceException(TwitterService.NO_TWEET_TEXT_MSG);

        } else if (statusText.length() > TwitterService.MAX_TWEET_LENGTH) {

            logger.info("Twitter status update unsuccessful.");

            throw new TwitterServiceException(TwitterService.TWEET_TOO_LONG_MSG);

        } else {

            try {

                return Optional.ofNullable(twitterFactory.updateStatus(statusText))
                        .map(s -> createNewStatusObject(s));

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

    public Optional<List<Status>> getHomeTimeline() throws TwitterServiceException {

        return getHomeTimelineFilteredByKeyword(null);

    }

    public Optional<List<Status>> getHomeTimelineFilteredByKeyword(String keyword) throws TwitterServiceException {

        logger.info("Attempting to retrieve home timeline through Twitter API.");

        try {

           /*return Optional.ofNullable(twitterFactory.getHomeTimeline())
                    .get()
                    .stream()
                    .filter(originalStatus -> {

                        if (keyword == null) {

                            return true;

                        } else {

                            return originalStatus.getText().contains(keyword);
                        }
                    })
                    .map(thisStatus -> createNewStatusObject(thisStatus));*/

            return Optional.ofNullable(twitterFactory.getHomeTimeline())
                    .map(list ->
                        list.stream()
                            .filter(originalStatus -> {

                                if (keyword == null) {

                                    return true;

                                } else {

                                    return originalStatus.getText().contains(keyword);
                                }
                            })
                            .map(thisStatus -> createNewStatusObject(thisStatus))
                            .collect(Collectors.toList())
                    );


        } catch (TwitterException twitterException) {

            logger.info("Timeline retrieval aborted. Twitter Exception thrown.");

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

    public void setTWFactory(Configuration newConfiguration) {
        ;

        twitterFactory = new TwitterFactory(newConfiguration).getInstance();
        ;

    }

    public void setTWFactory(Twitter factory) {

        twitterFactory = factory;

    }

    public Twitter getTwitterFactory() {

        return twitterFactory;

    }

    private Status createNewStatusObject(twitter4j.Status originalStatus) {

        User newUser = new User();
        newUser.setTwHandle(originalStatus.getUser().getScreenName());
        newUser.setName(originalStatus.getUser().getName());
        newUser.setProfileImageUrl(originalStatus.getUser().getProfileImageURL());

        Status newStatus = new com.khoros.twitterapp.models.Status();
        newStatus.setMessage(originalStatus.getText());
        newStatus.setUser(newUser);
        newStatus.setCreatedAt(originalStatus.getCreatedAt());

        return newStatus;

    }
}
