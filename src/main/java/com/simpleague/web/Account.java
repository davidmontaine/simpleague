package com.simpleague.web;

import com.simpleague.helper.AccountHelper;
import com.simpleague.user.User;
import com.simpleague.user.UserBean;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;    
    private static final Logger logger = Logger.getLogger(Account.class.getName());        
    
    private User user;
    private String emailCurrent;
    private String error;
    
    @Inject
    private UserBean userBean;
    
    @Inject
    private AccountHelper accountHelper;

    @Inject
    private Login login;
    
    @PostConstruct
    public void init() {
        logger.log(Level.INFO, "in init()");
        user = userBean.findByEmail(login.getCredentials().getEmail());        
        emailCurrent = user.getCredentials().getEmail();
    }
   
    public String getEmailCurrent() {
        return emailCurrent;
    }
    
    public void setEmailCurrent(String emailCurrent) {
        this.emailCurrent = emailCurrent;
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
    
    public String submit() {
        try {
            logger.log(Level.INFO, "in submit()");            
            
            if (accountHelper.process(user, emailCurrent, FacesUtil.getUri())) {
                login.setCredentials(user.getCredentials());                
                return "/auth/emailVerify.xhtml";
            }
        } catch (Exception e) {
            logger.log(Level.INFO, "exception: {0}", e.getMessage());
            error = FacesUtil.getResourceString("msg", "account.exception");
            return null;            
        }
        return "/auth/league.xhtml";
    }
}
