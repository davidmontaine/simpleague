package com.simpleague.rs;

import com.simpleague.helper.AccountHelper;
import com.simpleague.user.Credentials;
import com.simpleague.user.User;
import com.simpleague.user.UserBean;
import com.simpleague.util.ApplicationUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

@Path("user")
public class EmailResource {
    private static final Logger logger = Logger.getLogger(EmailResource.class.getName());            
    
    @Inject
    private UserBean userBean;
    
    @Inject
    private AccountHelper accountHelper;
    
    @Context
    private HttpServletRequest req;        
    
    @Secured
    @POST
    @Path("email")    
    public Response email(Credentials cred) {
        try {
            logger.log(Level.INFO, "in email()");
            User user = userBean.findByEmail(cred.getEmail());
            accountHelper.email(user, ApplicationUtil.getUriHost(req));
            return new Response(Result.SUCCESS);
        } catch (Exception e) {
            logger.log(Level.INFO, "exception: {0}", e.getMessage());                        
        }
        return new Response(Result.FAILURE);
    }            
}
