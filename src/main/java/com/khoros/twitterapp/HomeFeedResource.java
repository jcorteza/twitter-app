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

@Path("/api/1.0/twitter/timeline")
@Produces(MediaType.TEXT_PLAIN)
public class HomeFeedResource {

    @GET
    public Response get() {
        Twitter factory = new TwitterFactory().getSingleton();
        try {
            String lineBreak = "========================================================================";
            List<Status> tweetsFeed = factory.getHomeTimeline();
            System.out.println("Showing home timeline.");
            System.out.println(lineBreak);
            for (Status tweet : tweetsFeed) {
                System.out.println(tweet.getUser().getName() + "\n" + tweet.getText() + "\n" + lineBreak);
            }
            return Response.status(200).build();
        } catch (TwitterException feedException) {
            feedException.printStackTrace();
            System.out.println(feedException.getErrorMessage());
            return Response.status(feedException.getStatusCode()).build();
        }
    }
}
