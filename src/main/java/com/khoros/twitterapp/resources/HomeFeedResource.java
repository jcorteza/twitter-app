package com.khoros.twitterapp.resources;

import com.khoros.twitterapp.TwitterApp;
import com.khoros.twitterapp.models.Status;
import com.khoros.twitterapp.models.User;

import twitter4j.TwitterFactory;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.conf.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.net.HttpURLConnection;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/timeline")
@Produces(MediaType.APPLICATION_JSON)
public class HomeFeedResource {


    private Configuration conf;
    private Twitter factory;

    public HomeFeedResource(Configuration conf) {
        this.conf = conf;
        this.factory = new TwitterFactory(conf).getInstance();
    }

    // constructor for unit testing using mock Twitter object
    public HomeFeedResource(Twitter factory) {
        this.factory = factory;
    }

    @GET
    public Response get() {

        logger.debug("Configuration setup: {}", conf);

        try {

            // List<Status> tweetsFeed = factory.getHomeTimeline();
            List<Status> statusesList  = new ArrayList<>();

            for (twitter4j.Status originalStatus: factory.getHomeTimeline()) {

                User newUser = new User();
                newUser.setTwHandle(originalStatus.getUser().getScreenName());
                newUser.setName(originalStatus.getUser().getName());
                newUser.setProfileImageUrl(originalStatus.getUser().get400x400ProfileImageURL());

                Status newStatus = new Status();
                newStatus.setMessage(originalStatus.getText());
                newStatus.setUser(newUser);
                newStatus.setCreatedAt(originalStatus.getCreatedAt());

                statusesList.add(newStatus);

            }

            return Response.status(HttpURLConnection.HTTP_OK).entity(statusesList).build();

        } catch (TwitterException feedException) {


            if (feedException.isErrorMessageAvailable()) {

                logger.error("Twitter Exception thrown. Error Message: {} — Exception Code: {}",
                        feedException.getErrorMessage(),
                        feedException.getExceptionCode(),
                        feedException);

            } else {

                logger.error("Unknown Twitter Exception thrown. — Exception Code: {}",
                        feedException.getExceptionCode(),
                        feedException);

            }

            return Response.status(feedException.getStatusCode()).entity(TwitterApp.GENERAL_ERR_MSG).build();

        }
    }
}
