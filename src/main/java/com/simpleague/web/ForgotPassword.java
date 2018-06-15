package com.simpleague.web;

import com.simpleague.helper.ForgotPasswordHelper;
import com.simpleague.user.Credentials;
import com.simpleague.user.User;
import com.simpleague.user.UserBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class ForgotPassword {
    private static final Logger logger = Logger.getLogger(ForgotPassword.class.getName());

    private String captchaText;
    private Credentials credentials = new Credentials();
    private String error;
    private boolean success;
    
    @Inject
    private UserBean userBean;
    
    @Inject
    private ForgotPasswordHelper forgotPasswordHelper;

    public String getCaptchaText() {
        return captchaText;
    }    

    public void setCaptchaText(String captchaText) {
        this.captchaText = captchaText;
    }    
    
    public Credentials getCredentials() {
        return credentials;
    }
    
    public String getError() {
        return error;
    }        
    
    public void setError(String error) {
        this.error = error;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String submit() {
        try {
            logger.log(Level.INFO, "in submit()");                    
            User user = userBean.findByEmail(credentials.getEmail());
            
            if (user == null) {
                error = FacesUtil.getResourceString("msg", "forgotPassword.email.invalid");
                return null;
            }
            success = forgotPasswordHelper.process(user);
        } catch (Exception e) {
            logger.log(Level.INFO, "exception: {0}", e.getMessage());        
            error = FacesUtil.getResourceString("msg", "forgotPassword.exception");
            return null;
        }
        return "/forgotPasswordComplete.xhtml";
    }
}
