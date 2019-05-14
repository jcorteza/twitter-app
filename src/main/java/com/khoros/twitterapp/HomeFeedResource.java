package com.khoros.twitterapp;

import twitter4j.TwitterFactory;
import twitter4j.Twitter;
import twitter4j.Status;
import twitter4j.TwitterException;
import java.util.List;

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
        Twitter factory = new TwitterFactory().getSingleton();
        try {
            List<Status> tweetsFeed = factory.getHomeTimeline();
            return Response.status(200).entity(tweetsFeed).build();
        } catch (TwitterException feedException) {
            return Response.status(feedException.getStatusCode()).entity("Whoops! Something went wrong. Try again later.").build();
        }
    }
}
