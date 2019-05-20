package com.khoros.twitterapp.resources;

import com.khoros.twitterapp.TwitterApp;
import com.khoros.twitterapp.services.TwitterService;

import twitter4j.TwitterException;
import twitter4j.TwitterResponse;

import java.net.HttpURLConnection;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/timeline")
@Produces(MediaType.APPLICATION_JSON)
public class HomeFeedResource {

    @GET
    public Response get() {

        TwitterResponse twitterResponse = TwitterService.getInstance().getHomeTimeline();

        if(twitterResponse instanceof TwitterException) {

            TwitterException e = (TwitterException) twitterResponse;
            return Response.status(e.getStatusCode()).entity(TwitterApp.GENERAL_ERR_MSG).build();

        } else {

            return Response.status(HttpURLConnection.HTTP_OK).entity(twitterResponse).build();

        }
    }
}
