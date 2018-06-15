package com.simpleague.web;

import com.simpleague.user.User;
import com.simpleague.user.UserBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class EmailVerifyComplete {
    private static final Logger logger = Logger.getLogger(EmailVerifyComplete.class.getName());                
    
    private String uuid;
    private User user;    
    
    @Inject
    private UserBean userBean;

    @PostConstruct
    public void init() {
        logger.log(Level.INFO, "in init()");                
        uuid = FacesUtil.getRequest().getParameter("uuid");
        user = userBean.findByUuid(uuid);
        
        if (user != null) {
            user.setVerified("Y");
            user = userBean.update(user);
        }
    }
    
    public String getStatus() {
        if (user != null) {
            return user.getVerified();
        }
        return "N";
    }
}
