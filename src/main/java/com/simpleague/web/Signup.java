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
public class Signup extends PasswordVerify {
    private static final Logger logger = Logger.getLogger(Signup.class.getName());    
    
    private User user = new User();
    private String captchaText;
    private String error;    
    
    @Inject
    private UserBean userBean;
    
    @Inject
    private Login login;
    
    @Inject
    private MailBean mailBean;

    public String getCaptchaText() {
        return captchaText;
    }    

    public void setCaptchaText(String captchaText) {
        this.captchaText = captchaText;
    }    

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }    
    
    public User getUser() {
        return user;
    }
    
    public String submit() throws Exception {
        try {
            logger.log(Level.INFO, "in submit()");                    
            String passwordText = user.getCredentials().getPasswordText();
            userBean.create(user);            
            user.getCredentials().setPasswordText(passwordText);
            FacesUtil.getRequest().login(user.getCredentials().getEmail(), user.getCredentials().getPasswordText());            
            login.setCredentials(user.getCredentials());
            mailBean.send(user.getCredentials().getEmail(),
                    FacesUtil.getResourceString("msg", "emailVerify.email.subject"),
                    FacesUtil.getResourceString("msg", "emailVerify.email.text") + " " + FacesUtil.getUri() + FacesUtil.EMAIL_VERIFY_COMPLETE + user.getUuid());
        } catch (Exception e) {
            logger.log(Level.INFO, "exception: {0}", e.getMessage());        
            error = FacesUtil.getResourceString("msg", "signup.exception");
            return null;
        }
        return "/auth/emailVerify.xhtml";
    }
}
