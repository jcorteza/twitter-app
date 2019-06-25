package com.khoros.twitterapp.services;

import com.khoros.twitterapp.models.Status;
import com.khoros.twitterapp.models.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;

public class TwitterService {

    private final Logger logger = LoggerFactory.getLogger(TwitterService.class);

    public static final int MAX_TWEET_LENGTH = 280;
    public static final String GENERAL_ERR_MSG = "Whoops! Something went wrong. Try again later.";
    public static final String NO_TWEET_TEXT_MSG = "No tweet text entered.";
    public static final String TWEET_TOO_LONG_MSG = "Tweet text surpassed " + TwitterService.MAX_TWEET_LENGTH + " characters.";
    public Twitter twitterFactory;
    public enum CacheListType { HOME, USER };
    private CacheUp cacheUp;

    @Inject
    public TwitterService(Twitter twitterFactory) {
        this.twitterFactory = twitterFactory;
        this.cacheUp = new CacheUp();
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

                cacheUp.getTimelineCache(CacheListType.HOME).clear();
                cacheUp.getTimelineCache(CacheListType.USER).clear();

            } catch (TwitterException twitterException) {

                logger.info("Twitter status update aborted. Twitter Exception thrown.");

                handleTwitterException(twitterException);

            }

            return responseOptional;
        }
    }

    public Optional<List<Status>> getHomeTimeline() throws TwitterServiceException {

        return getFilteredHomeTimeline(null);

    }

    public Optional<List<Status>> getFilteredHomeTimeline(String keyword) throws TwitterServiceException {

        logger.info("Attempting to retrieve home timeline through Twitter API.");

        List<twitter4j.Status> cacheList = cacheUp.getTimelineCache(CacheListType.HOME);
        Optional<List<twitter4j.Status>> optionalList = null;

        if(cacheList.isEmpty()) {

            try {

                    optionalList = Optional.ofNullable(twitterFactory.getHomeTimeline());
                    optionalList.ifPresent(list -> cacheUp.addStatusToCache(CacheListType.HOME, list));

            } catch (TwitterException twitterException) {

                logger.info("Home timeline retrieval aborted. Twitter Exception thrown.");

                handleTwitterException(twitterException);

            }

        } else {

            List<twitter4j.Status> responseList = new ArrayList<>();
            responseList.addAll(cacheList);
            optionalList = Optional.ofNullable(responseList);

        }

        return optionalList
                .map(list -> list.stream()
                        .filter(originalStatus -> {

                            if (StringUtils.isEmpty(keyword)) {

                                return true;

                            } else {

                                return originalStatus.getText().contains(keyword);
                            }
                        })
                        .map(thisStatus -> createNewStatusObject(thisStatus))
                        .collect(Collectors.toList())
                );

    }

    public Optional<List<Status>> getUserTimeline() throws TwitterServiceException {

        logger.info("Attempting to retrieve user timeline through Twitter API.");

        List<twitter4j.Status> cacheList = cacheUp.getTimelineCache(CacheListType.USER);
        Optional<List<twitter4j.Status>> optionalList = null;

        if(cacheList.isEmpty()) {

            try {

                optionalList = Optional.ofNullable(twitterFactory.getUserTimeline());
                optionalList.ifPresent(list -> cacheUp.addStatusToCache(CacheListType.USER,list));

            } catch (TwitterException twitterException) {

                logger.info("User timeline retrieval aborted. Twitter Exception thrown.");

                handleTwitterException(twitterException);

            }

        } else {

            List<twitter4j.Status> responseList = new ArrayList<>();
            responseList.addAll(cacheList);
            optionalList = Optional.ofNullable(responseList);

        }

        return optionalList
                .map(list -> list.stream()
                        .map(originalStatus -> createNewStatusObject(originalStatus))
                        .collect(Collectors.toList())
                );

    }

    public void setCacheUp(CacheUp cacheUp) {

        this.cacheUp = cacheUp;

    }

    public CacheUp getCacheUp() {
        return cacheUp;
    }

    public Twitter getTwitterFactory() {

        return twitterFactory;

    }

    public Status createNewStatusObject(twitter4j.Status originalStatus) {

        User newUser = new User();
        String handle = originalStatus.getUser().getScreenName();
        newUser.setTwHandle(handle);
        newUser.setName(originalStatus.getUser().getName());
        newUser.setProfileImageUrl(originalStatus.getUser().get400x400ProfileImageURL());

        Status newStatus = new com.khoros.twitterapp.models.Status();
        String url = new StringBuilder()
                .append("https://twitter.com/")
                .append(handle)
                .append("/status/")
                .append(originalStatus.getId())
                .toString();
        newStatus.setMessage(originalStatus.getText());
        newStatus.setUser(newUser);
        newStatus.setCreatedAt(originalStatus.getCreatedAt());
        newStatus.setPostUrl(url);

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
