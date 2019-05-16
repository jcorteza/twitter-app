package com.khoros.twitterapp;

import com.khoros.twitterapp.resources.HomeFeedResource;
import org.junit.*;
import static org.mockito.Mockito.*;
import twitter4j.Twitter;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.ResponseList;

import java.lang.Exception;
import java.net.HttpURLConnection;

public class HomeFeedResourceTest {

    private HomeFeedResource feedResource;
    private Twitter mockFactory;
    private ResponseList<Status> feedEntity;

    @Before
    public void setup() {
        mockFactory = mock(Twitter.class);
        feedResource = new HomeFeedResource(mockFactory);
    }

    @After
    public void resetMock() {
        reset(mockFactory);
    }

    @Test
    public void resourceGetSuccess() {

        try {
            feedEntity = new ResponseImplTest<>();
            when(mockFactory.getHomeTimeline()).thenReturn(feedEntity);
        } catch(TwitterException e) {
            Assert.fail("Test failed due to TwitterException: " + e.getMessage());
        }

        Assert.assertEquals(HttpURLConnection.HTTP_OK, feedResource.get().getStatus());
        Assert.assertEquals(feedEntity, feedResource.get().getEntity());
    }

    @Test
    public void resourceGetException() throws TwitterException {

        when(mockFactory.getHomeTimeline()).thenThrow(
                new TwitterException("Testing TwitterException.", new Exception(), HttpURLConnection.HTTP_INTERNAL_ERROR)
        );

        Assert.assertEquals(HttpURLConnection.HTTP_INTERNAL_ERROR, feedResource.get().getStatus());
        Assert.assertEquals(TwitterApp.GENERAL_ERR_MSG, feedResource.get().getEntity());
    }

    @Test
    public void mainConstructorTest() {

    }
}
