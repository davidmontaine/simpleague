package com.simpleague.web;

import com.simpleague.mail.MailBean;
import com.simpleague.user.User;
import com.simpleague.user.UserBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class EmailVerify {
    private static final Logger logger = Logger.getLogger(EmailVerify.class.getName());            
    private static final int EMAIL_COUNT = 3;

    private String error;
    
    @Inject
    private Login login;
    
    @Inject
    private UserBean userBean;
    
    @Inject
    private MailBean mailBean;

    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
    }
    
    public String submit() {
        try {
            logger.log(Level.INFO, "in submit()");                    
            User user = userBean.findByEmail(login.getCredentials().getEmail());
            
            if (user.getEmailCount() < EMAIL_COUNT) {
                user.setEmailCount(user.getEmailCount() + 1);
                user = userBean.update(user);
                mailBean.send(user.getCredentials().getEmail(),
                        FacesUtil.getResourceString("msg", "emailVerify.email.subject"),
                        FacesUtil.getResourceString("msg", "emailVerify.email.text") + " " + FacesUtil.getUri() +
                        FacesUtil.EMAIL_VERIFY_COMPLETE + user.getUuid());
            } else {
                mailBean.send("admin@simpleague.com",
                        FacesUtil.getResourceString("msg", "emailVerify.email.error.subject"),
                        FacesUtil.getResourceString("msg", "emailVerify.email.error.text1") + " " +
                        user.getCredentials().getEmail() + " " +
                        FacesUtil.getResourceString("msg", "emailVerify.email.error.text2"));
                FacesUtil.logout();
                return "/emailVerifyError.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
            logger.log(Level.INFO, "exception: {0}", e.getMessage());        
            error = FacesUtil.getResourceString("msg", "emailVerify.exception");
        }
        return null;
    }
}
