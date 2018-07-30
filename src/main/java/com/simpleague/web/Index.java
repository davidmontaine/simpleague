package com.simpleague.web;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Index {
    private static final Logger logger = Logger.getLogger(Index.class.getName());            
    
    public String link() {
        try {
            logger.log(Level.INFO, "in link()");                                    
            return FacesUtil.getUri() + "/angular";
        } catch (Exception e) {
            logger.log(Level.INFO, "exception: {0}", e.getMessage());                        
        }
        return null;
    }
}
