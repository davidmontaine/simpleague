package com.simpleague.web;

import com.simpleague.user.Credentials;
import com.simpleague.user.User;
import com.simpleague.user.UserBean;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class Login implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(Login.class.getName());

    private Credentials credentials = new Credentials();
    private String url;    
    private String error;
    
    @Inject
    private UserBean userBean;

    public Credentials getCredentials() {
        return credentials;
    }
    
    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }    
    
    public String getError() {
        return error;
    }    
    
    public void setError(String error) {}

    // Http get request just prior to challenge.
    public String getUrl() {
        if (FacesUtil.getRequest().getMethod().equalsIgnoreCase("get")) {
            url = (String)FacesUtil.getRequest().getAttribute("javax.servlet.forward.request_uri");
            error = null;
            credentials = new Credentials();
        }
        return url;
    }
    
    public void setUrl(String url) {}
    
    public String submit() {
        try {
            logger.log(Level.INFO, "in submit()");
            FacesUtil.getRequest().login(credentials.getEmail(), credentials.getPasswordText());
            User user = userBean.findByEmail(credentials.getEmail());
            
            if (!"Y".equals(user.getVerified())) {
                return EmailVerifyListener.VIEW_OUTCOME;
            }
            return url.substring(url.indexOf(FacesUtil.REQUEST_CONSTRAINED_PATH));
        } catch (Exception e) {
            logger.log(Level.INFO, "exception: {0}", e.getMessage());                
            error = FacesUtil.getResourceString("msg", "login.error");
        }
        credentials = new Credentials();
        return null;
    }
}
