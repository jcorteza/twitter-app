package com.khoros.twitterapp;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class AccessOriginFilter implements ContainerResponseFilter {
    private final String headerACAO = "Access-Control-Allow-Origin";
    private final String origin = "http://localhost:9000";

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)  {

        responseContext.getHeaders().add(headerACAO, origin);

    }
}
