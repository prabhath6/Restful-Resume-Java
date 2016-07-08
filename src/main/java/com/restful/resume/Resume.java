package com.restful.resume;

/**
 * Created by prabhath on 7/8/16.
 */

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class Resume {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getMessage() {
        System.out.println("test");
        return "Hello world!";
    }
}
