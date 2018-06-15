package com.simpleague.rs;

import com.simpleague.helper.ForgotPasswordHelper;
import com.simpleague.user.Credentials;
import com.simpleague.user.User;
import com.simpleague.user.UserBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("user")
public class ForgotPasswordResource {
    private static final Logger logger = Logger.getLogger(ForgotPasswordResource.class.getName());            
    
    @Inject
    private UserBean userBean;

    @Inject
    private ForgotPasswordHelper forgotPasswordHelper;    

    @POST
    @Path("forgotPassword")
    public Response forgotPassword(Credentials credentials) {
        try {
            logger.log(Level.INFO, "in forgotPassword()");
            User user = userBean.findByEmail(credentials.getEmail());
            
            if (user == null) {
                return new Response(Result.INVALID);
            }            
            if (forgotPasswordHelper.process(user)) {
                return new Response(Result.SUCCESS);
            }
        } catch (Exception e) {
            logger.log(Level.INFO, "exception: {0}", e.getMessage());                        
        }
        return new Response(Result.FAILURE);
    }
}
