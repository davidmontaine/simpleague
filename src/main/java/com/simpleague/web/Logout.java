package com.simpleague.web;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Logout {
    private static final Logger logger = Logger.getLogger(Logout.class.getName());            
    
    public String logout() {
        try {
            logger.log(Level.INFO, "in logout()");                                    
            FacesUtil.logout();
        } catch (Exception e) {
            logger.log(Level.INFO, "exception: {0}", e.getMessage());                        
        }
        return "/index.xhtml?faces-redirect=true";
    }
}
