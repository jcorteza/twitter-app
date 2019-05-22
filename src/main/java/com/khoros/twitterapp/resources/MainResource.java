package com.khoros.twitterapp.resources;

import com.khoros.twitterapp.models.User;
import com.khoros.twitterapp.models.Status;
import com.khoros.twitterapp.services.TwitterService;
import com.khoros.twitterapp.services.TwitterServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

@Path("/api/1.0/twitter")
@Produces(MediaType.APPLICATION_JSON)
public class MainResource {

    final Logger logger = LoggerFactory.getLogger(MainResource.class);
    public TwitterService twitterService = TwitterService.getInstance();

    @Path("/tweet")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @POST
    public Response post(@FormParam("message") String tweetText) {

        logger.info("Accessing Twitter Service updateStatus feature.");

        String statusText = tweetText.trim();

        try {

            twitter4j.Status twitterStatus = twitterService.updateStatus(statusText);
            return Response.status(HttpURLConnection.HTTP_OK).entity(twitterStatus).build();

        } catch (TwitterServiceException twServiceException) {

            logger.info("Twitter Service process aborted. Twitter Service Excpetion thrown.");

            if (twServiceException.getCause() == null) {

                logger.error("Twitter Service Exception — Error Message: {}",
                        twServiceException.getMessage(),
                        twServiceException);

                return Response.status(HttpURLConnection.HTTP_FORBIDDEN).entity(twServiceException.getMessage()).build();

            } else {

                logger.error("Twitter Service Exception — Error Cause: {}",
                        twServiceException.getCause().getMessage(),
                        twServiceException);

                return Response.status(HttpURLConnection.HTTP_INTERNAL_ERROR).entity(
                        twServiceException.getCause().getMessage()
                ).build();

            }
        }

    }

    @Path("/timeline")
    @GET
    public Response get() {

        logger.info("Accessing Twitter Service getHomeTimeline feature.");

        try {

            return Response.status(HttpURLConnection.HTTP_OK).entity(twitterService.getHomeTimeline()).build();

        } catch (TwitterServiceException twServiceException) {

            logger.info("Twitter Service process aborted. Twitter Service Exception thrown." );
            logger.error("Twitter Service Exception — Error Cause: {}",
                    twServiceException.getCause().getMessage(),
                    twServiceException);

            return Response.status(HttpURLConnection.HTTP_INTERNAL_ERROR).entity(
                    twServiceException.getCause().getMessage()
            ).build();

        }

    }
}
