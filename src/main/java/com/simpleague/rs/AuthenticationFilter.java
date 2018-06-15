package com.simpleague.rs;

import com.simpleague.token.TokenBean;
import com.simpleague.user.User;
import com.simpleague.util.StringUtil;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    private static final Logger logger = Logger.getLogger(AuthenticationFilter.class.getName());                
    
    @Inject
    private TokenBean tokenBean;
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        logger.log(Level.INFO, "in filter()");        
        
        if (requestContext.getMethod().equalsIgnoreCase("options")) { 
            requestContext.abortWith(Response.ok().build()); 
        }         
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }
        String token = authorizationHeader.substring("Bearer".length()).trim();
        
        try {
            validateToken(token);
        } catch (Exception e) {
            logger.log(Level.INFO, "exception: {0}", StringUtil.getStackTrace(e));                    
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private void validateToken(String token) throws Exception {
        logger.log(Level.INFO, "in validateToken()");                
        User user = tokenBean.get(token);
        
        if (user == null) {        
            throw new NotAuthorizedException("Invalid token");
        }
    }
}
