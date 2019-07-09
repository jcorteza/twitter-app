package com.khoros.twitterapp.services;

import com.khoros.twitterapp.ResponseImplTest;
import com.khoros.twitterapp.Twitter4jStatusImpl;
import com.khoros.twitterapp.models.Status;


import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import twitter4j.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class TwitterServiceTest {

    private CacheUp cacheUp;
    private TwitterService twSingleton;
    private Twitter mockFactory;
    private String testStatusText;
    private String testUrl;
    private ResponseList<twitter4j.Status> twResponse;
    private twitter4j.Status exampleStatus;
    private TwitterException testExceptionWithoutMessage;
    private TwitterException testEceptionWithmessage;

    @Before
    public void setup() {

        mockFactory = mock(Twitter.class);
        twSingleton = new TwitterService(mockFactory);
        cacheUp = mock(CacheUp.class);
        twSingleton.setCacheUp(cacheUp);
        testStatusText = "Tweet Test";
        twResponse = new ResponseImplTest<>();
        exampleStatus = new Twitter4jStatusImpl();
        twResponse.add(exampleStatus);
        testExceptionWithoutMessage = new TwitterException("");
        testEceptionWithmessage = new TwitterException("Test Exception");
        testUrl = new StringBuilder()
                .append("https://twitter.com/")
                .append(exampleStatus.getUser().getScreenName())
                .append("/status/")
                .append(exampleStatus.getId())
                .toString();

    }

    @After
    public void resetTesting() {
        reset(mockFactory, cacheUp);
    }

    @Test
    public void updateStatusTestSuccess() {

        Optional<Status> serviceResponse = Optional.empty();

        try {

            when(mockFactory.updateStatus(testStatusText)).thenReturn(exampleStatus);
            serviceResponse = twSingleton.updateStatus(testStatusText);

        } catch (TwitterException e) {

            fail("Test failed due to Twitter Exception.");

        } catch (TwitterServiceException e) {

            fail("Test failed due to Twitter Service Exception.");

        }

        assertEquals(exampleStatus.getText(), serviceResponse.get().getMessage());
        assertEquals(exampleStatus.getCreatedAt(), serviceResponse.get().getCreatedAt());
        assertEquals(exampleStatus.getId(), serviceResponse.get().getStatusID());
        assertEquals(testUrl, serviceResponse.get().getPostUrl());

    }

    @Test
    public void updateStatusTestError() throws TwitterException {

        try{

            when(mockFactory.updateStatus(testStatusText)).thenThrow(testExceptionWithoutMessage);
            twSingleton.updateStatus(testStatusText);

            fail("Expected TwitterServiceException to be thrown.");

        } catch (TwitterServiceException e) {

            assertEquals(e.getCause(), testExceptionWithoutMessage);

        }

    }

    @Test
    public void getHomeTimelineTestSuccess() {

        Optional<List<Status>> responseList = null;

        try {

            when(mockFactory.getHomeTimeline()).thenReturn(twResponse);
            responseList = twSingleton.getHomeTimeline();

        } catch (Exception e) {

            fail("Test failed due to Twitter Exception");

        }

        assertEquals(exampleStatus.getText(), responseList.get().get(0).getMessage());
        assertEquals(exampleStatus.getCreatedAt(), responseList.get().get(0).getCreatedAt());
        assertEquals(exampleStatus.getId(), responseList.get().get(0).getStatusID());
        assertEquals(testUrl, responseList.get().get(0).getPostUrl());

    }

    @Test
    public void getUserTimelineTestSuccess() {

        Optional<List<Status>> responseList = Optional.empty();

        try {

            when(mockFactory.getUserTimeline()).thenReturn(twResponse);
            responseList = twSingleton.getUserTimeline();

        } catch (Exception e) {

            fail("Test failed due to Twitter Exception");

        }

        assertEquals(exampleStatus.getText(), responseList.get().get(0).getMessage());
        assertEquals(exampleStatus.getCreatedAt(), responseList.get().get(0).getCreatedAt());
        assertEquals(exampleStatus.getId(), responseList.get().get(0).getStatusID());
        assertEquals(testUrl, responseList.get().get(0).getPostUrl());

    }

    @Test
    public void getUserTimelineTestError() throws TwitterException {

        try {

            when(mockFactory.getUserTimeline()).thenThrow(testExceptionWithoutMessage);
            twSingleton.getUserTimeline();

            fail("Expected TwitterServiceException to be thrown.");

        } catch (TwitterServiceException e) {

            assertEquals(e.getCause(), testExceptionWithoutMessage);
        }
    }

    @Test
    public void replyToTweetTestSuccess() {

        Optional<Status> response = Optional.empty();
        String testID = "999999";
        twitter4j.StatusUpdate testUpdate = new twitter4j.StatusUpdate(testStatusText);
        testUpdate.setInReplyToStatusId(Long.parseLong(testID));

        try {

            when(mockFactory.updateStatus(testUpdate)).thenReturn(exampleStatus);
            response = twSingleton.replyToTweet(testStatusText, testID);

        } catch (TwitterException e) {

            fail("Test failed due to Twitter Exception.");

        } catch (TwitterServiceException e) {

            fail("Test failed due to Twitter Service Exception.");
        }

        assertEquals(exampleStatus.getText(), response.get().getMessage());
        assertEquals(exampleStatus.getCreatedAt(), response.get().getCreatedAt());
        assertEquals(exampleStatus.getId(), response.get().getStatusID());
        assertEquals(testUrl, response.get().getPostUrl());

    }

    @Test
    public void replyToTweetTestTypeError() {

        try {

            String testID = "999999???";
            twSingleton.replyToTweet(testStatusText, testID);

            fail("Expected a TwitterServiceException to be thrown.");

        } catch (TwitterServiceException e) {

            assertEquals(TwitterService.ID_TYPE_ERR_MSG, e.getMessage());
            assertEquals(NumberFormatException.class, e.getCause().getClass());

        }
    }

    @Test
    public void createNewStatusObjectTest() {

        Status exampleNewStatus = twSingleton.createNewStatusObject(exampleStatus);

        assertEquals(exampleStatus.getText(), exampleNewStatus.getMessage());
        assertEquals(exampleStatus.getCreatedAt(), exampleNewStatus.getCreatedAt());
        assertEquals(exampleStatus.getUser().getName(), exampleNewStatus.getUser().getName());
        assertEquals(exampleStatus.getUser().getScreenName(), exampleNewStatus.getUser().getTwHandle());
        assertEquals(exampleStatus.getUser().get400x400ProfileImageURL(), exampleNewStatus.getUser().getProfileImageUrl());
        assertEquals(exampleStatus.getId(), exampleNewStatus.getStatusID());
        assertEquals(testUrl, exampleNewStatus.getPostUrl());

    }

    @Test
    public void verifyTextLengthTestEmpty() {

        try {

            twSingleton.updateStatus(null);
            fail("Expected a TwitterServiceException to be thrown.");

        } catch (TwitterServiceException twServiceException){

            assertEquals(twServiceException.getMessage(), TwitterService.NO_TWEET_TEXT_MSG);

        }

    }

    @Test
    public void verifyTextLengthTestTooLong() {

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i <= TwitterService.MAX_TWEET_LENGTH; i++) {
            sb.append("a");
        }

        try {

            twSingleton.updateStatus(sb.toString());
            fail("Expected a TwitterServiceException to be thrown.");

        } catch (TwitterServiceException twServiceException){

            assertEquals(twServiceException.getMessage(), TwitterService.TWEET_TOO_LONG_MSG);

        }

    }

    @Test
    public void handleTwitterExceptionTestWithMessage() throws TwitterException {

        String testID = "999999";
        StatusUpdate testUpdate = new StatusUpdate(testStatusText);
        testUpdate.setInReplyToStatusId(Long.parseLong(testID));

        try {

            when(mockFactory
                    .updateStatus(testUpdate))
                    .thenThrow(testEceptionWithmessage);
            twSingleton.replyToTweet(testStatusText, testID);
            fail("Expected a TwitterServiceException to be thrown.");

        } catch (TwitterServiceException twServiceException) {

            assertEquals(twServiceException.getCause().getMessage(), testEceptionWithmessage.getMessage());
        }
    }

    @Test
    public void handleTwitterExceptionTestNoMessage() throws TwitterException {

        String testID = "999999";
        StatusUpdate testUpdate = new StatusUpdate(testStatusText);
        testUpdate.setInReplyToStatusId(Long.parseLong(testID));

        try {

            when(mockFactory
                    .updateStatus(testUpdate))
                    .thenThrow(testExceptionWithoutMessage);
            twSingleton.replyToTweet(testStatusText, testID);
            fail("Expected a TwitterServiceException to be thrown.");

        } catch (TwitterServiceException twServiceException) {

            assertEquals(twServiceException.getCause(), testExceptionWithoutMessage);

        }
    }

    @Test
    public void getCachedTimelineTest() {

        List<twitter4j.Status> testList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            testList.add(new Twitter4jStatusImpl());
        }

        HashMap<String, List<twitter4j.Status>> testHashMap = new HashMap<>();
        Optional<List<Status>> testResponse = null;

        testHashMap.put("home", testList);

        try {

            when(cacheUp.getTimelineCache()).thenReturn(testHashMap);
            testResponse = twSingleton.getHomeTimeline();

        } catch(TwitterServiceException e) {

            fail("TwitterService unit test failed due to TwitterServiceException.");

        }

        assertTrue(testResponse.get().size() == 3);
    }

    @Test
    public void getFactoryTest() {

        assertEquals(mockFactory, twSingleton.getTwitterFactory());

    }

    @Test
    public void setCacheUpTest() {

        CacheUp testCacheUp = new CacheUp();
        twSingleton.setCacheUp(testCacheUp);

        assertEquals(testCacheUp.getTimelineCache().get("home"), twSingleton.getCacheUp().getTimelineCache().get("home"));
    }
}
