package com.simpleague.web;

import com.simpleague.helper.PasswordHelper;
import com.simpleague.user.Credentials;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class Password extends PasswordVerify {
    private static final Logger logger = Logger.getLogger(Password.class.getName());                
    
    private Credentials credentials = new Credentials();
    private String passwordCurrent;
    private String error;
    
    @Inject
    private PasswordHelper passwordHelper;

    @Inject
    private Login login;

    public Credentials getCredentials() {
        return credentials;
    }
    
    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
    }        
    
    public String getPasswordCurrent() {
        return passwordCurrent;
    }
    
    public void setPasswordCurrent(String passwordCurrent) {
        this.passwordCurrent = passwordCurrent;
    }    
    
    public String submit() throws Exception {
        try {
            logger.log(Level.INFO, "in submit()");  
            credentials.setEmail(login.getCredentials().getEmail());
            passwordHelper.process(credentials);
            login.setCredentials(credentials);
        } catch (Exception e) {
            logger.log(Level.INFO, "exception: {0}", e.getMessage());        
            error = FacesUtil.getResourceString("msg", "password.exception");
            return null;            
        }
        return "/auth/leagues.xhtml";        
    }
}
