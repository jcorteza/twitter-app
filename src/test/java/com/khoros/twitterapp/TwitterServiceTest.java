package com.khoros.twitterapp;

import com.khoros.twitterapp.services.TwitterService;
import com.khoros.twitterapp.models.Status;

import com.khoros.twitterapp.services.TwitterServiceException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;
import twitter4j.*;
import java.util.List;
import java.util.Optional;

public class TwitterServiceTest {

    private TwitterService twSingleton;
    private Twitter mockFactory;
    private String testStatusText;
    private ResponseList<twitter4j.Status> twResponse;
    private twitter4j.Status exampleStatus;

    @Before
    public void setup() {

        mockFactory = mock(Twitter.class);
        twSingleton = new TwitterService(mockFactory);
        testStatusText = "Tweet Test";
        twResponse = new ResponseImplTest<twitter4j.Status>();
        exampleStatus = new Twitter4jStatusImpl();
        twResponse.add(exampleStatus);

    }

    @Test
    public void updateStatusTestSuccess() {

        Optional<Status> serviceResponse = Optional.empty();

        try {

            when(mockFactory.updateStatus(testStatusText)).thenReturn(exampleStatus);
            serviceResponse = twSingleton.updateStatus(testStatusText);

        } catch (TwitterException e) {

            Assert.fail("Test failed due to Twitter Exception");

        } catch (TwitterServiceException e) {

            Assert.fail("Test failed due to Twitter Service Exception.");

        }

        Assert.assertEquals(exampleStatus.getText(), serviceResponse.get().getMessage());
        Assert.assertEquals(exampleStatus.getCreatedAt(), serviceResponse.get().getCreatedAt());
        Assert.assertEquals(exampleStatus.getUser().getProfileImageURL(), serviceResponse.get().getUser().getProfileImageUrl());
        Assert.assertEquals(exampleStatus.getUser().getScreenName(), serviceResponse.get().getUser().getTwHandle());
        Assert.assertEquals(exampleStatus.getUser().getName(), serviceResponse.get().getUser().getName());

    }

    @Test
    public void getHomeTimelineTestSuccess() {

        Optional<List<Status>> responseList = Optional.empty();

        try {

            when(mockFactory.getHomeTimeline()).thenReturn(twResponse);
            responseList = twSingleton.getHomeTimeline();

        } catch (Exception e) {

            Assert.fail("Test failed due to Twitter Exception");

        }

        Assert.assertEquals(exampleStatus.getText(), responseList.get().get(0).getMessage());
        Assert.assertEquals(exampleStatus.getCreatedAt(), responseList.get().get(0).getCreatedAt());
        Assert.assertEquals(exampleStatus.getUser().getName(), responseList.get().get(0).getUser().getName());
        Assert.assertEquals(exampleStatus.getUser().getScreenName(), responseList.get().get(0).getUser().getTwHandle());
        Assert.assertEquals(exampleStatus.getUser().getProfileImageURL(), responseList.get().get(0).getUser().getProfileImageUrl());

    }

}
