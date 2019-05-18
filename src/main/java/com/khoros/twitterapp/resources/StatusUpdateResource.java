package com.khoros.twitterapp.resources;

import com.khoros.twitterapp.TwitterApp;
import com.khoros.twitterapp.services.TwitterService;

import twitter4j.TwitterException;
import twitter4j.TwitterResponse;

import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response;
import java.net.HttpURLConnection;

@Path("/tweet")
@Consumes("application/x-www-form-urlencoded")
@Produces("application/json")
public class StatusUpdateResource {

    public static final String NO_TWEET_TEXT_MSG = "No tweet text entered.";
    public static final String TWEET_TOO_LONG_MSG = "Tweet text surpassed " + TwitterApp.MAX_TWEET_LENGTH + " characters.";
    public TwitterService twitterService = TwitterService.getInstance();

    @POST
    public Response postStatus(@FormParam("message") String tweetText) {

        String statusText = tweetText.trim();

        if (statusText.length() == 0) {

            return Response.status(HttpURLConnection.HTTP_FORBIDDEN).entity(StatusUpdateResource.NO_TWEET_TEXT_MSG).build();

        } else if (statusText.length() > TwitterApp.MAX_TWEET_LENGTH) {

            return Response.status(HttpURLConnection.HTTP_FORBIDDEN).entity(StatusUpdateResource.TWEET_TOO_LONG_MSG).build();

        } else {

            TwitterResponse twitterResponse = twitterService.updateStatus(statusText);

            if(twitterResponse instanceof TwitterException) {

                TwitterException e = (TwitterException) twitterResponse;
                return Response.status(e.getStatusCode()).entity(TwitterApp.GENERAL_ERR_MSG).build();

            } else {

                return Response.status(HttpURLConnection.HTTP_OK).entity(twitterResponse).build();

            }
        }

    }

    public void setTwitterService(TwitterService twitterService) {

        this.twitterService = twitterService;

    }
}