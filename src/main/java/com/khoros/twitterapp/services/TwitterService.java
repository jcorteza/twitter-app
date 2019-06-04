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

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class TwitterService {

    private final Logger logger = LoggerFactory.getLogger(TwitterService.class);

    public static final int MAX_TWEET_LENGTH = 280;
    public static final String GENERAL_ERR_MSG = "Whoops! Something went wrong. Try again later.";
    public static final String NO_TWEET_TEXT_MSG = "No tweet text entered.";
    public static final String TWEET_TOO_LONG_MSG = "Tweet text surpassed " + TwitterService.MAX_TWEET_LENGTH + " characters.";
    private static final TwitterService INSTANCE = new TwitterService();
    private static Twitter twitterFactory;
    private CacheUp cacheUp = CacheUp.getInstance();

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

            Optional<Status> responseOptional = null;

            try {

                responseOptional = Optional.ofNullable(twitterFactory.updateStatus(statusText))
                        .map(s -> createNewStatusObject(s));

                cacheUp.getCacheSet().clear();
                Optional.ofNullable(twitterFactory.getHomeTimeline())
                        .ifPresent(list -> cacheUp.addStatusesToCache(list));

            } catch (TwitterException twitterException) {

                logger.info("Twitter status update aborted. Twitter Exception thrown.");

                handleTwitterException(twitterException);

            }

            return responseOptional;
        }
    }

    public Optional<List<Status>> getHomeTimeline() throws TwitterServiceException {

        return getHomeTimelineFilteredByKeyword(null);

    }

    public Optional<List<Status>> getHomeTimelineFilteredByKeyword(String keyword) throws TwitterServiceException {

        logger.info("Attempting to retrieve home timeline through Twitter API.");

        Set<twitter4j.Status> cacheSet = cacheUp.getCacheSet();
        Optional<List<Status>> optionalList = null;

        if(cacheSet.isEmpty()) {

            try {

                optionalList =  Optional.ofNullable(twitterFactory.getHomeTimeline())
                        .map(list -> {
                            cacheUp.addStatusesToCache(list);
                            return list.stream()
                                    .filter(originalStatus -> {

                                        if (StringUtils.isEmpty(keyword)) {

                                            return true;

                                        } else {

                                            return originalStatus.getText().contains(keyword);
                                        }
                                    })
                                    .map(thisStatus -> createNewStatusObject(thisStatus))
                                    .collect(Collectors.toList());
                        });

            } catch (TwitterException twitterException) {

                logger.info("Timeline retrieval aborted. Twitter Exception thrown.");

                handleTwitterException(twitterException);

            }

        } else {

            optionalList =  Optional.of(
                    cacheSet.stream()
                            .map(cacheStatus -> createNewStatusObject(cacheStatus))
                            .collect(Collectors.toList())
            );

        }

        return optionalList;

    }

    public void setTWFactory(Configuration newConfiguration) {

        twitterFactory = new TwitterFactory(newConfiguration).getInstance();

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

    private void handleTwitterException(TwitterException e) throws TwitterServiceException {

        if (e.isErrorMessageAvailable()) {

            logger.error("Twitter Exception — Error Message: {} — Exception Code: {}",
                    e.getErrorMessage(),
                    e.getExceptionCode(),
                    e);

        } else {

            logger.error("Unknown Twitter Exception — Exception Code: {}",
                    e.getExceptionCode(),
                    e);

        }

        throw new TwitterServiceException("Twitter Exception thrown.", e);

    }
}
