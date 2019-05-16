package com.khoros.twitterapp;

import com.khoros.twitterapp.resources.HomeFeedResource;
import org.junit.*;
import static org.mockito.Mockito.*;
import twitter4j.Twitter;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.ResponseList;

import java.lang.Exception;

public class HomeFeedResourceTest {

    private HomeFeedResource feedResource;
    private Twitter mockFactory;
    private ResponseList<Status> feedEntity;
    private String stringEntity;

    @Before
    public void setup() {
        mockFactory = mock(Twitter.class);
        feedResource = new HomeFeedResource(mockFactory);
        stringEntity = "Whoops! Something went wrong. Try again later.";
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

        Assert.assertEquals(200, feedResource.get().getStatus());
        Assert.assertEquals(feedEntity, feedResource.get().getEntity());
    }

    @Test
    public void resourceGetException() throws TwitterException {

        when(mockFactory.getHomeTimeline()).thenThrow(new TwitterException("Testing TwitterException.", new Exception(), 500));

        Assert.assertEquals(500, feedResource.get().getStatus());
        Assert.assertEquals(stringEntity, feedResource.get().getEntity());
    }

    @Test
    public void mainConstructorTest() {

    }
}
