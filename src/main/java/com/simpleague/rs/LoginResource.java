package com.simpleague.rs;

import com.simpleague.token.TokenBean;
import com.simpleague.user.Credentials;
import com.simpleague.user.User;
import com.simpleague.user.UserBean;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

@Path("user")
public class LoginResource {
    private static final Logger logger = Logger.getLogger(LoginResource.class.getName());            
    
    @Context
    private HttpServletRequest req;    
    
    @Inject
    private UserBean userBean;
    
    @Inject
    private TokenBean tokenBean;
    
    @POST
    @Path("login")
    public LoginResponse login(Credentials credentials) {
        try {
            logger.log(Level.INFO, "in login()");
            
            if (req.getRemoteUser() != null) {
                req.logout();
            }
            req.login(credentials.getEmail(), credentials.getPasswordText());
            String token = new BigInteger(130, new SecureRandom()).toString(32);
            User user = userBean.findByEmail(credentials.getEmail());
            user.getCredentials().setPasswordText(credentials.getPasswordText());
            tokenBean.put(token, user);
            return new LoginResponse(token, user.getVerified());            
        } catch (Exception e) {
            logger.log(Level.INFO, "exception: {0}", e.getMessage());                        
        }
        return new LoginResponse();        
    }    
}
