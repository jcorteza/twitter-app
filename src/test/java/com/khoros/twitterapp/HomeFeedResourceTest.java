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

    @InjectMocks
    private HomeFeedResource feedResource;

    @Mock
    private Twitter mockFactory;

    private ResponseList<Status> entity;
    private Response exampleResponse;

    @Before
    public void setup() {
        feedResource = new HomeFeedResource();
        mockFactory = mock(Twitter.class);

        try {
            entity = mockFactory.getHomeTimeline();
            exampleResponse = Response.status(200).entity(entity).build();
            when(mockFactory.getHomeTimeline()).thenReturn(entity);
        } catch(TwitterException e) {
            Assert.fail("Test failed due to TwitterException: " + e.getMessage());
        }
    }

    @Test
    public void resourceGetSuccess() {
        Assert.assertEquals(exampleResponse, feedResource.get());
    }
}
