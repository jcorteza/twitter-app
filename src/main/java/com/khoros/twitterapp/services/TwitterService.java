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
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;

public class TwitterService {

    private final Logger logger = LoggerFactory.getLogger(TwitterService.class);

    public static final int MAX_TWEET_LENGTH = 280;
    public static final String GENERAL_ERR_MSG = "Whoops! Something went wrong. Try again later.";
    public static final String NO_TWEET_TEXT_MSG = "No tweet text entered.";
    public static final String TWEET_TOO_LONG_MSG = "Tweet text surpassed " + TwitterService.MAX_TWEET_LENGTH + " characters.";
    private CacheUp cacheUp;
    public Twitter twitterFactory;

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
        Optional<List<twitter4j.Status>> optionalList = null;

        if(cacheSet.isEmpty()) {

            try {

                optionalList = Optional.ofNullable(twitterFactory.getHomeTimeline());
                optionalList.ifPresent((list) -> cacheUp.addStatusesToCache(list));

            } catch (TwitterException twitterException) {

                logger.info("Timeline retrieval aborted. Twitter Exception thrown.");

                handleTwitterException(twitterException);

            }

        } else {

            List<twitter4j.Status> responseList = new ArrayList<>();
            responseList.addAll(cacheSet);
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
        newUser.setProfileImageUrl(originalStatus.getUser().getProfileImageURL());

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
