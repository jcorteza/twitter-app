package com.khoros.twitterapp.resources;

import com.khoros.twitterapp.ResponseImplTest;
import com.khoros.twitterapp.Twitter4jStatusImpl;
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

import javax.ws.rs.core.Response;

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

        assertEquals(Response.Status.CREATED.getStatusCode(), mainResource.postStatusUpdate(exampleText).getStatusInfo().getStatusCode());
        assertEquals(twitterStatus.getText(), statusResponse.getMessage());
    }

    @Test
    public void postStatusUpdateTestException() {

        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), mainResource.postStatusUpdate("").getStatusInfo().getStatusCode());
        assertEquals(TwitterService.NO_TWEET_TEXT_MSG, mainResource.postStatusUpdate("").getEntity());

    }

    @Test
    public void statusLengthZeroTest() {
        exampleText = "";

        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), mainResource.postStatusUpdate(exampleText).getStatusInfo().getStatusCode());
        assertEquals(TwitterService.NO_TWEET_TEXT_MSG, mainResource.postStatusUpdate(exampleText).getEntity());
    }

    @Test
    public void statusLengthLongTest() {
        exampleText = StringUtils.repeat("a", TwitterService.MAX_TWEET_LENGTH + 1);

        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), mainResource.postStatusUpdate(exampleText).getStatusInfo().getStatusCode());
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

        assertEquals(Response.Status.OK.getStatusCode(), mainResource.getHomeTimeline().getStatusInfo().getStatusCode());
        assertEquals(twServiceResponse.get(0).getMessage(), responseList.get(0).getMessage());

    }

    @Test
    public void getHomeTimelineTestException() throws TwitterServiceException {

        when(twSingleton.getHomeTimeline()).thenThrow(new TwitterException(exceptionText));

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), mainResource.getHomeTimeline().getStatusInfo().getStatusCode());
        assertEquals(TwitterService.GENERAL_ERR_MSG, mainResource.getHomeTimeline().getEntity());
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

        assertEquals(Response.Status.OK.getStatusCode(), mainResource.getFilteredTimeline(exampleText).getStatusInfo().getStatusCode());
        assertEquals(twServiceResponse.get(0).getMessage(), responseList.get(0).getMessage());

    }

    @Test
    public void getHomeTimelineTestFilteredException() throws TwitterServiceException {

        when(cacheUp.getTimelineCache()).thenReturn(new HashMap<>());
        when(twSingleton.getHomeTimeline()).thenThrow(new TwitterException(exceptionText));

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), mainResource.getFilteredTimeline(exampleText).getStatusInfo().getStatusCode());
        assertEquals(TwitterService.GENERAL_ERR_MSG, mainResource.getHomeTimeline().getEntity());

    }

    @Test
    public void getUserTimelineTestSuccess() {

        try {

            when(mockFactory.getUserTimeline()).thenReturn(exampleTwitterFeed);
            Response testResponse = mainResource.getUserTimeline();
            List<Status> testList = (List<Status>) testResponse.getEntity();

            assertEquals(Response.Status.OK.getStatusCode(), testResponse.getStatusInfo().getStatusCode());
            assertEquals(exampleTwitterFeed.get(0).getText(), testList.get(0).getMessage());

        } catch (TwitterException e) {

            fail("Test failed due to a TwitterException.");

        }
    }

    @Test
    public void getUserTimelineTestError() throws TwitterException {

            when(mockFactory.getUserTimeline()).thenThrow(new TwitterException(exceptionText));
            Response testResponse = mainResource.getUserTimeline();

            assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), testResponse.getStatusInfo().getStatusCode());
            assertEquals(TwitterService.GENERAL_ERR_MSG, testResponse.getEntity());

    }

    @Test
    public void replyToTweetTestSuccess() {
        String testID = "999999";
        StatusUpdate testUpdate = new StatusUpdate(exampleText);
        testUpdate.setInReplyToStatusId(Long.parseLong(testID));
        Status response = null;

        try {

            when(mockFactory.updateStatus(testUpdate)).thenReturn(twitterStatus);
            response = (Status) mainResource.replyToTweet(exampleText, testID).getEntity();

        } catch (TwitterException e) {

            fail("Test failed due to TwitterException.");
        }

        assertEquals(Response.Status.CREATED.getStatusCode(), mainResource.replyToTweet(exampleText, testID).getStatusInfo().getStatusCode());
        assertEquals(twitterStatus.getText(), response.getMessage());
    }

    @Test
    public void replyToTweetTestTwitterException() throws TwitterException {
        String testID = "999999";
        StatusUpdate testUpdate = new StatusUpdate(exampleText);
        testUpdate.setInReplyToStatusId(Long.parseLong(testID));

        when(mockFactory.updateStatus(testUpdate)).thenThrow(new TwitterException(""));

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), mainResource.replyToTweet(exampleText, testID).getStatusInfo().getStatusCode());
        assertEquals(TwitterService.GENERAL_ERR_MSG, mainResource.replyToTweet(exampleText, testID).getEntity());

    }

    @Test
    public void replyToTweetTestTypeError() throws TwitterException {

        String testID = "999999???";
        Response testResponse = mainResource.replyToTweet(exampleText, testID);

        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), testResponse.getStatus());
        assertEquals(TwitterService.ID_TYPE_ERR_MSG, testResponse.getEntity());

    }

}
