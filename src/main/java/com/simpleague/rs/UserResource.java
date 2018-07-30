package com.simpleague.rs;

import com.simpleague.helper.AccountHelper;
import com.simpleague.mail.MailBean;
import com.simpleague.user.User;
import com.simpleague.user.UserBean;
import com.simpleague.util.ApplicationUtil;
import com.simpleague.web.FacesUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Path("user")
public class UserResource {
    private static final Logger logger = Logger.getLogger(UserResource.class.getName());            
    
    @Inject
    private UserBean userBean;

    @Inject
    private MailBean mailBean;    
    
    @Inject
    private AccountHelper accountHelper;
    
    @Context
    private HttpServletRequest req;        
    
    @POST
    public Response create(User user) {
        try {
            logger.log(Level.INFO, "in create()");
            userBean.create(user);                        
            mailBean.send(user.getCredentials().getEmail(),
                    ApplicationUtil.getResourceString("messages", "emailVerify.email.subject"),
                    ApplicationUtil.getResourceString("messages", "emailVerify.email.text") + " " + ApplicationUtil.getUriHost(req) + FacesUtil.EMAIL_VERIFY_COMPLETE + user.getUuid());
            return new Response(Result.SUCCESS);
        } catch (Exception e) {
            logger.log(Level.INFO, "exception: {0}", e.getMessage());                        
        }
        return new Response(Result.FAILURE);
    }    
    
    @Secured
    @GET
    @Path("{email}")    
    public User get(@PathParam("email") String email) {
        try {
            logger.log(Level.INFO, "in get()");
            return userBean.findByEmail(email);
        } catch (Exception e) {
            logger.log(Level.INFO, "exception: {0}", e.getMessage());                        
        }
        return null;
    }

    @Secured
    @PUT
    public Response update(UserCurrent userCurrent) {
        try {
            logger.log(Level.INFO, "in update()");
            User user = userBean.findByEmail(userCurrent.getEmailCurrent());
            user.setName(userCurrent.getName());
            user.getCredentials().setEmail(userCurrent.getCredentials().getEmail());            
            accountHelper.process(user, userCurrent.getEmailCurrent(), ApplicationUtil.getUriHost(req));
            return new Response(Result.SUCCESS);
        } catch (Exception e) {
            logger.log(Level.INFO, "exception: {0}", e.getMessage());                        
        }
        return new Response(Result.FAILURE);
    }        
}

@XmlRootElement
@XmlType(name = "")
class UserCurrent extends User {
    private String emailCurrent;
    
    public String getEmailCurrent() {
        return emailCurrent;
    }

    public void setEmailCurrent(String emailCurrent) {
        this.emailCurrent = emailCurrent;
    }
}
