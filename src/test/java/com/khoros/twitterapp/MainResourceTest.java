package com.khoros.twitterapp;

import com.khoros.twitterapp.resources.MainResource;
import com.khoros.twitterapp.services.TwitterService;
import com.khoros.twitterapp.services.TwitterServiceException;
import com.khoros.twitterapp.models.Status;
import com.khoros.twitterapp.models.User;

import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;
import twitter4j.Twitter;
import twitter4j.ResponseList;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class MainResourceTest {

    private TwitterService twSingleton;
    private Twitter mockFactory;
    private MainResource mainResource;
    private String exampleText;
    private String exceptionText;
    private ResponseList<twitter4j.Status> exampleTwitterFeed;


    @Before
    public void setup() {

        twSingleton = TwitterService.getInstance();
        mockFactory = mock(Twitter.class);
        twSingleton.setTWFactory(mockFactory);
        mainResource = new MainResource();
        exampleText = "Tweet Test";
        exceptionText = "Testing TwitterException.";
        exampleTwitterFeed = new ResponseImplTest<>();
        exampleTwitterFeed.add(new Twitter4jStatusImpl());

    }

    @Test
    public void postStatusUpdateTestSuccess() {

        twitter4j.Status twitterStatus = null;
        Status statusResponse = null;

        try {

            twitterStatus = TwitterObjectFactory.createStatus("{\"rateLimitStatus\":null,\"accessLevel\":2,\"createdAt\":1557851237000,\"id\":1128335988466622465,\"text\":\"" + exampleText + "\",\"displayTextRangeStart\":0,\"displayTextRangeEnd\":12,\"source\":\"<a href=\\\"https://github.com/jcorteza\\\" rel=\\\"nofollow\\\">Tweeting—Khoros Lab C104</a>\",\"inReplyToStatusId\":-1,\"inReplyToUserId\":-1,\"favoriteCount\":0,\"inReplyToScreenName\":null,\"geoLocation\":null,\"place\":null,\"retweetCount\":0,\"lang\":\"en\",\"retweetedStatus\":null,\"userMentionEntities\":[],\"hashtagEntities\":[],\"mediaEntities\":[],\"symbolEntities\":[],\"currentUserRetweetId\":-1,\"scopes\":null,\"user\":{\"rateLimitStatus\":null,\"accessLevel\":0,\"id\":2422687974,\"name\":\"Josephine Cortez\",\"email\":null,\"screenName\":\"joC346\",\"location\":\"\",\"description\":\"\",\"descriptionURLEntities\":[],\"url\":null,\"followersCount\":41,\"status\":null,\"profileBackgroundColor\":\"ACDED6\",\"profileTextColor\":\"333333\",\"profileLinkColor\":\"94D487\",\"profileSidebarFillColor\":\"DDEEF6\",\"profileSidebarBorderColor\":\"FFFFFF\",\"profileUseBackgroundImage\":true,\"showAllInlineMedia\":false,\"friendsCount\":410,\"createdAt\":1396387577000,\"favouritesCount\":23,\"utcOffset\":-1,\"timeZone\":null,\"profileBackgroundImageUrlHttps\":\"https://abs.twimg.com/images/themes/theme18/bg.gif\",\"profileBackgroundTiled\":false,\"lang\":\"en\",\"statusesCount\":60,\"translator\":false,\"listedCount\":0,\"withheldInCountries\":null,\"protected\":false,\"contributorsEnabled\":false,\"profileImageURL\":\"http://pbs.twimg.com/profile_images/513270910254473218/Or70322R_normal.png\",\"biggerProfileImageURL\":\"http://pbs.twimg.com/profile_images/513270910254473218/Or70322R_bigger.png\",\"miniProfileImageURL\":\"http://pbs.twimg.com/profile_images/513270910254473218/Or70322R_mini.png\",\"originalProfileImageURL\":\"http://pbs.twimg.com/profile_images/513270910254473218/Or70322R.png\",\"400x400ProfileImageURL\":\"http://pbs.twimg.com/profile_images/513270910254473218/Or70322R_400x400.png\",\"profileImageURLHttps\":\"https://pbs.twimg.com/profile_images/513270910254473218/Or70322R_normal.png\",\"biggerProfileImageURLHttps\":\"https://pbs.twimg.com/profile_images/513270910254473218/Or70322R_bigger.png\",\"miniProfileImageURLHttps\":\"https://pbs.twimg.com/profile_images/513270910254473218/Or70322R_mini.png\",\"originalProfileImageURLHttps\":\"https://pbs.twimg.com/profile_images/513270910254473218/Or70322R.png\",\"400x400ProfileImageURLHttps\":\"https://pbs.twimg.com/profile_images/513270910254473218/Or70322R_400x400.png\",\"defaultProfileImage\":false,\"defaultProfile\":false,\"profileBackgroundImageURL\":\"http://abs.twimg.com/images/themes/theme18/bg.gif\",\"profileBannerURL\":null,\"profileBannerRetinaURL\":null,\"profileBannerIPadURL\":null,\"profileBannerIPadRetinaURL\":null,\"profileBannerMobileURL\":null,\"profileBannerMobileRetinaURL\":null,\"profileBanner300x100URL\":null,\"profileBanner600x200URL\":null,\"profileBanner1500x500URL\":null,\"geoEnabled\":true,\"verified\":false,\"followRequestSent\":false,\"urlentity\":{\"start\":0,\"end\":0,\"url\":\"\",\"expandedURL\":\"\",\"displayURL\":\"\",\"text\":\"\"}},\"withheldInCountries\":null,\"quotedStatus\":null,\"quotedStatusId\":-1,\"quotedStatusPermalink\":null,\"truncated\":false,\"favorited\":false,\"retweeted\":false,\"retweet\":false,\"retweetedByMe\":false,\"possiblySensitive\":false,\"contributors\":[],\"urlentities\":[]}");
            when(mockFactory.updateStatus(exampleText)).thenReturn(twitterStatus);
            statusResponse = (Status) mainResource.postStatusUpdate(exampleText).getEntity();

        } catch (TwitterException e) {

            Assert.fail("Test failed due to Twitter Exception.");

        }

        Assert.assertEquals(HttpURLConnection.HTTP_OK, mainResource.postStatusUpdate(exampleText).getStatus());
        Assert.assertEquals(twitterStatus.getText(), statusResponse.getMessage());
    }

    @Test
    public void postStatusUpdateTestException() throws TwitterServiceException {

        try {

            twitter4j.Status testStatus = new Twitter4jStatusImpl();
            when(mockFactory.updateStatus(exampleText)).thenReturn(testStatus);

        } catch (TwitterException e) {

            Assert.fail("Unit test failed due to Twitter Exception.");

        }

        when(twSingleton.updateStatus(exampleText)).thenThrow(
                new TwitterException(exceptionText)
        );

        Assert.assertEquals(HttpURLConnection.HTTP_INTERNAL_ERROR, mainResource.postStatusUpdate(exampleText).getStatus());
        Assert.assertEquals(exceptionText, mainResource.postStatusUpdate(exampleText).getEntity());
    }

    @Test
    public void statusLengthZeroTest() {
        exampleText = "";

        Assert.assertEquals(HttpURLConnection.HTTP_FORBIDDEN, mainResource.postStatusUpdate(exampleText).getStatus());
        Assert.assertEquals(TwitterService.NO_TWEET_TEXT_MSG, mainResource.postStatusUpdate(exampleText).getEntity());
    }

    @Test
    public void statusLengthLongTest() {
        exampleText = StringUtils.repeat("a", TwitterService.MAX_TWEET_LENGTH + 1);

        Assert.assertEquals(HttpURLConnection.HTTP_FORBIDDEN, mainResource.postStatusUpdate(exampleText).getStatus());
        Assert.assertEquals(TwitterService.TWEET_TOO_LONG_MSG, mainResource.postStatusUpdate(exampleText).getEntity());
    }

    @Test
    public void getHomeTimelineTestSuccess() {

        List<Status> twServiceResponse = new ArrayList<>();
        List<Status> responseList = null;

        User newUser = new User();
        newUser.setTwHandle(exampleTwitterFeed.get(0).getUser().getScreenName());
        newUser.setName(exampleTwitterFeed.get(0).getUser().getName());
        newUser.setProfileImageUrl(exampleTwitterFeed.get(0).getUser().getProfileImageURL());

        Status newStatus = new com.khoros.twitterapp.models.Status();
        newStatus.setMessage(exampleTwitterFeed.get(0).getText());
        newStatus.setUser(newUser);
        newStatus.setCreatedAt(exampleTwitterFeed.get(0).getCreatedAt());

        twServiceResponse.add(newStatus);

        try {

            when(mockFactory.getHomeTimeline()).thenReturn(exampleTwitterFeed);
            responseList = (List<Status>) mainResource.getHomeTimeline().getEntity();

        } catch (TwitterException e) {

            Assert.fail("Test failed due to Twitter Exception.");

        }

        Assert.assertEquals(HttpURLConnection.HTTP_OK, mainResource.getHomeTimeline().getStatus());
        Assert.assertEquals(twServiceResponse.get(0).getMessage(), responseList.get(0).getMessage());

    }

    @Test
    public void getHomeTimelineTestException() throws TwitterServiceException {

        try {

            when(mockFactory.getHomeTimeline()).thenReturn(exampleTwitterFeed);

        } catch (TwitterException e) {

            Assert.fail("Unit test failed due to Twitter Exception.");

        }

        when(twSingleton.getHomeTimeline()).thenThrow(new TwitterException(exceptionText));

        Assert.assertEquals(HttpURLConnection.HTTP_INTERNAL_ERROR, mainResource.getHomeTimeline().getStatus());
        Assert.assertEquals(exceptionText, mainResource.getHomeTimeline().getEntity());
    }

}
