package com.khoros.twitterapp.resources;

import twitter4j.TwitterFactory;
import twitter4j.Twitter;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.conf.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/tweet")
@Consumes("application/x-www-form-urlencoded")
@Produces("application/json")
public class StatusUpdateResource {

    final Logger logger = LoggerFactory.getLogger(StatusUpdateResource.class);

    private int maxTweetLength;
    private Configuration conf;
    private Twitter factory = new TwitterFactory(conf).getInstance();

    public StatusUpdateResource(Configuration conf, int maxTweetLength) {
        this.maxTweetLength = maxTweetLength;
        this.conf = conf;
    }

    public StatusUpdateResource(Twitter mockFactory) {
        this.factory = mockFactory;
        this.maxTweetLength = 280;
    }

    @POST
    public Response postStatus(@FormParam("message") String tweetText) {

        String statusText = tweetText.trim();

        logger.info("Twitter Configuration: {}", conf);
        logger.info("Status Update Text: {}", statusText);

        try {
            if (statusText.length() == 0) {

                logger.info("statusText length: {}", statusText.length());
                return Response.status(403).entity("No tweet text entered.").build();

            } else if (statusText.length() > maxTweetLength) {

                logger.info("statusText length: {}", statusText.length());
                return Response.status(403).entity("Tweet text surpassed 280 characters.").build();

            } else {

                Status newStatus = factory.updateStatus(statusText);
                return Response.status(200).entity(newStatus).build();

            }
        } catch (TwitterException tweetException) {

            logger.info("Twitter Exception thrown." );

            if (tweetException.isCausedByNetworkIssue()) {

                logger.debug("Exception Caused By Network Issues: {}", tweetException.isCausedByNetworkIssue());

            } else if (tweetException.exceededRateLimitation()) {

                logger.debug("Exceed Rate Limitation: {}", tweetException.exceededRateLimitation());
                logger.debug("Current Rate Limit: {}", tweetException.getRateLimitStatus());

            } else {

                logger.debug("Twitter API Access Level: {}", tweetException.getAccessLevel());
                logger.debug("TwitterException Error Message: {}", tweetException.getErrorMessage());

            }

            return Response.status(tweetException.getStatusCode()).entity("Whoops! Something went wrong. Try again later.").build();
        }
    }
}
