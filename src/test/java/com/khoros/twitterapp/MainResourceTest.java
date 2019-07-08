package com.khoros.twitterapp;

import com.khoros.twitterapp.resources.MainResource;
import com.khoros.twitterapp.services.CacheUp;
import com.khoros.twitterapp.services.TwitterService;
import com.khoros.twitterapp.services.TwitterServiceException;
import com.khoros.twitterapp.models.Status;
import com.khoros.twitterapp.models.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.ResponseList;
import twitter4j.TwitterException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class MainResourceTest {

    private CacheUp cacheUp;
    private TwitterService twSingleton;
    private Twitter mockFactory;
    private MainResource mainResource;
    private String exampleText;
    private String exceptionText;
    private ResponseList<twitter4j.Status> exampleTwitterFeed;
    private List<Status> twServiceResponse = new ArrayList<>();
    private List<Status> responseList;
    private User newUser = new User();
    private Status newStatus = new com.khoros.twitterapp.models.Status();
    private twitter4j.Status twitterStatus;


    @Before
    public void setup() {

        mockFactory = mock(Twitter.class);
        twSingleton = new TwitterService(mockFactory);
        mainResource = new MainResource(twSingleton);
        cacheUp = mock(CacheUp.class);
        twSingleton.setCacheUp(cacheUp);
        exampleText = "Tweet Test";
        exceptionText = "Testing TwitterException.";
        exampleTwitterFeed = new ResponseImplTest<>();
        twitterStatus = new Twitter4jStatusImpl();
        exampleTwitterFeed.add(twitterStatus);

        newUser.setTwHandle(exampleTwitterFeed.get(0).getUser().getScreenName());
        newUser.setName(exampleTwitterFeed.get(0).getUser().getName());
        newUser.setProfileImageUrl(exampleTwitterFeed.get(0).getUser().getProfileImageURL());

        newStatus.setMessage(exampleTwitterFeed.get(0).getText());
        newStatus.setUser(newUser);
        newStatus.setCreatedAt(exampleTwitterFeed.get(0).getCreatedAt());

        twServiceResponse.add(newStatus);

    }

    @Test
    public void postStatusUpdateTestSuccess() {

        Status statusResponse = null;

        try {

            when(mockFactory.updateStatus(exampleText)).thenReturn(twitterStatus);
            statusResponse = (Status) mainResource.postStatusUpdate(exampleText).getEntity();

        } catch (TwitterException e) {

            fail("Test failed due to Twitter Exception.");

        }

        assertEquals(201, mainResource.postStatusUpdate(exampleText).getStatus());
        assertEquals(twitterStatus.getText(), statusResponse.getMessage());
    }

    @Test
    public void postStatusUpdateTestException() {

        assertEquals(403, mainResource.postStatusUpdate("").getStatus());
        assertEquals(TwitterService.NO_TWEET_TEXT_MSG, mainResource.postStatusUpdate("").getEntity());

    }

    @Test
    public void statusLengthZeroTest() {
        exampleText = "";

        assertEquals(403, mainResource.postStatusUpdate(exampleText).getStatus());
        assertEquals(TwitterService.NO_TWEET_TEXT_MSG, mainResource.postStatusUpdate(exampleText).getEntity());
    }

    @Test
    public void statusLengthLongTest() {
        exampleText = StringUtils.repeat("a", TwitterService.MAX_TWEET_LENGTH + 1);

        assertEquals(403, mainResource.postStatusUpdate(exampleText).getStatus());
        assertEquals(TwitterService.TWEET_TOO_LONG_MSG, mainResource.postStatusUpdate(exampleText).getEntity());
    }

    @Test
    public void getHomeTimelineTestSuccess() {

        try {

            when(mockFactory.getHomeTimeline()).thenReturn(exampleTwitterFeed);
            responseList = (List<Status>) mainResource.getHomeTimeline().getEntity();

        } catch (TwitterException e) {

            fail("Test failed due to Twitter Exception.");

        }

        assertEquals(200, mainResource.getHomeTimeline().getStatus());
        assertEquals(twServiceResponse.get(0).getMessage(), responseList.get(0).getMessage());

    }

    @Test
    public void getHomeTimelineTestException() throws TwitterServiceException {

        when(twSingleton.getHomeTimeline()).thenThrow(new TwitterException(exceptionText));

        assertEquals(500, mainResource.getHomeTimeline().getStatus());
        assertEquals(exceptionText, mainResource.getHomeTimeline().getEntity());
    }

    @Test
    public void getHomeTimelineFilteredTestSuccess() {

        exampleText = "Tweet";

        try {

            when(mockFactory.getHomeTimeline()).thenReturn(exampleTwitterFeed);
            responseList = (List<Status>) mainResource.getFilteredTimeline(exampleText).getEntity();

        } catch (TwitterException e) {

            fail("Test failed due to Twitter Exception.");

        }

        assertEquals(200, mainResource.getFilteredTimeline(exampleText).getStatus());
        assertEquals(twServiceResponse.get(0).getMessage(), responseList.get(0).getMessage());

    }

    @Test
    public void getHomeTimelineTestFilteredException() throws TwitterServiceException {

        when(cacheUp.getTimelineCache()).thenReturn(new HashMap<>());
        when(twSingleton.getHomeTimeline()).thenThrow(new TwitterException(exceptionText));

        assertEquals(500, mainResource.getFilteredTimeline(exampleText).getStatus());
        assertEquals(exceptionText, mainResource.getHomeTimeline().getEntity());

    }

    @Test
    public void replyToTweetTestSuccess() {
        long testID = 999999;
        StatusUpdate testUpdate = new StatusUpdate(exampleText);
        testUpdate.setInReplyToStatusId(testID);
        Status response = null;

        try {

            when(mockFactory.updateStatus(testUpdate)).thenReturn(twitterStatus);
            response = (Status) mainResource.replyToTweet(exampleText, testID).getEntity();

        } catch (TwitterException e) {

            fail("Test failed due to TwitterException.");
        }

        assertEquals(201, mainResource.replyToTweet(exampleText, testID).getStatus());
        assertEquals(twitterStatus.getText(), response.getMessage());
    }

    @Test
    public void replyToTweetError() throws TwitterException {
        long testID = 999999;
        StatusUpdate testUpdate = new StatusUpdate(exampleText);
        testUpdate.setInReplyToStatusId(testID);

        when(mockFactory.updateStatus(testUpdate)).thenThrow(new TwitterException(exceptionText));

        assertEquals(500, mainResource.replyToTweet(exampleText, testID).getStatus());
        assertEquals(exceptionText, mainResource.replyToTweet(exampleText, testID).getEntity());

    }

}
