package com.simpleague.rs;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class LeagueResource {
    private static final Logger logger = Logger.getLogger(LeagueResource.class.getName());            
    
    @Secured
    @GET
    @Path("leagues")
    public Response getLeagues() {
        logger.log(Level.INFO, "in getLeagues()");
        return new Response(Result.SUCCESS);
    }    
}
