package com.khoros.twitterapp.services;

import com.khoros.twitterapp.models.Status;
import com.khoros.twitterapp.models.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.inject.Inject;

public class TwitterService {

    private final Logger logger = LoggerFactory.getLogger(TwitterService.class);

    public static final int MAX_TWEET_LENGTH = 280;
    public static final String GENERAL_ERR_MSG = "Whoops! Something went wrong. Try again later.";
    public static final String IN_REPLY_TO_NUll_MSG = "Value for inReplyTo is required.";
    public static final String NO_TWEET_TEXT_MSG = "No tweet text entered.";
    public static final String TWEET_TOO_LONG_MSG = "Tweet text surpassed " + TwitterService.MAX_TWEET_LENGTH + " characters.";
    public Twitter twitterFactory;
    private CacheUp cacheUp;

    @Inject
    public TwitterService(Twitter twitterFactory) {
        this.twitterFactory = twitterFactory;
        this.cacheUp = new CacheUp();
    }

    public Optional<Status> updateStatus(String statusText) throws TwitterServiceException {

        logger.info("Attempting to update status through Twitter API.");

        verifyTextLength(statusText);

        try {

            cacheUp.getTimelineCache().clear();

            Optional<Status> responseOptional = Optional.ofNullable(twitterFactory.updateStatus(statusText))
                    .map(s -> createNewStatusObject(s));

            return responseOptional;

        } catch (TwitterException twitterException) {

            logger.info("TwitterService status update aborted. Twitter Exception thrown.");

            throw handleTwitterException(twitterException);

        }

    }

    public Optional<List<Status>> getHomeTimeline() throws TwitterServiceException {

        return getFilteredHomeTimeline(null);

    }

    public Optional<List<Status>> getFilteredHomeTimeline(String keyword) throws TwitterServiceException {

        logger.info("Attempting to retrieve home timeline through Twitter API.");

        Optional<List<twitter4j.Status>> optionalList = Optional.ofNullable(cacheUp.getTimelineCache().get("home"));

        if(!optionalList.isPresent()) {

            try {

                optionalList = Optional.ofNullable(twitterFactory.getHomeTimeline());
                optionalList.ifPresent(list -> cacheUp.addStatusToCache("home", list));

            } catch (TwitterException twitterException) {

                logger.info("TwitterService home timeline retrieval aborted. Twitter Exception thrown.");

                throw handleTwitterException(twitterException);

            }

        }

        return optionalList
                .map(list -> list.stream()
                        .filter(originalStatus -> {

                            if (StringUtils.isEmpty(keyword)) {

                                return true;

                            } else {

                                return originalStatus.getText().toLowerCase().contains(keyword.toLowerCase());
                            }
                        })
                        .map(thisStatus -> createNewStatusObject(thisStatus))
                        .collect(Collectors.toList())
                );

    }

    public Optional<List<Status>> getUserTimeline() throws TwitterServiceException {

        logger.info("Attempting to retrieve user timeline through Twitter API.");

        Optional<List<twitter4j.Status>> optionalList = Optional.ofNullable(cacheUp.getTimelineCache().get("user"));

        if(!optionalList.isPresent()) {

            try {

                optionalList = Optional.ofNullable(twitterFactory.getUserTimeline());
                optionalList.ifPresent(list -> cacheUp.addStatusToCache("user",list));

            } catch (TwitterException twitterException) {

                logger.info("TwitterService user timeline retrieval aborted. Twitter Exception thrown.");

                throw handleTwitterException(twitterException);

            }

        }

        return optionalList
                .map(list -> list.stream()
                        .map(originalStatus -> createNewStatusObject(originalStatus))
                        .collect(Collectors.toList())
                );

    }

    public Optional<Status> replyToTweet(String statusText, Long inReplyToID) throws TwitterServiceException {

        logger.info("Attempting to reply to status through Twitter API.");

        verifyTextLength(statusText);
        if(inReplyToID == null) {

            throw new TwitterServiceException(IN_REPLY_TO_NUll_MSG);

        }

        twitter4j.StatusUpdate statusUpdate = new twitter4j.StatusUpdate(statusText);
        statusUpdate.setInReplyToStatusId(inReplyToID);

        try {

            Optional<Status> newStatus = Optional.ofNullable(twitterFactory.updateStatus(statusUpdate))
                    .map(status -> createNewStatusObject(status));

            cacheUp.getTimelineCache().clear();

            return newStatus;


        } catch (TwitterException twitterException) {

            logger.info("TwitterService replyToTweet aborted. Twitter Exception thrown.");

            throw handleTwitterException(twitterException);

        }

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
        newStatus.setStatusID(Long.toString(originalStatus.getId()));
        newStatus.setPostUrl(url);

        return newStatus;

    }

    private void verifyTextLength(String statusText) throws TwitterServiceException {

        logger.info("Verifying length of new status text.");

        if (StringUtils.isEmpty(statusText)) {

            logger.info("Twitter status update unsuccessful.");

            throw new TwitterServiceException(TwitterService.NO_TWEET_TEXT_MSG);

        } else if (statusText.length() > TwitterService.MAX_TWEET_LENGTH) {

            logger.info("Twitter status update unsuccessful.");

            throw new TwitterServiceException(TwitterService.TWEET_TOO_LONG_MSG);

        }

        logger.info("Text passed length verification.");

    }

    private TwitterServiceException handleTwitterException(TwitterException e) {

        return new TwitterServiceException(GENERAL_ERR_MSG, e);

    }
}
