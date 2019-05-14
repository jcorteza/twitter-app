package com.khoros.twitterapp;

import org.junit.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import twitter4j.Twitter;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.ResponseList;

import javax.ws.rs.core.Response;

public class HomeFeedResourceTest {

    private HomeFeedResource feedResource;
    private Twitter mockFactory;
    private ResponseList<Status> feedEntity;

    @Before
    public void setup() {
        feedResource = new HomeFeedResource();
        mockFactory = mock(Twitter.class);
        feedResource.setFactory(mockFactory);

        try {
            feedEntity = new ResponseImplTest<>();
            when(mockFactory.getHomeTimeline()).thenReturn(feedEntity);
        } catch(TwitterException e) {
            Assert.fail("Test failed due to TwitterException: " + e.getMessage());
        }
    }

    @Test
    public void resourceGetSuccess() {
        Assert.assertEquals(200, feedResource.get().getStatus());
        Assert.assertEquals(feedEntity, feedResource.get().getEntity());
    }
}
