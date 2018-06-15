package com.simpleague.helper;

import com.simpleague.mail.MailBean;
import com.simpleague.user.Credentials;
import com.simpleague.user.User;
import com.simpleague.user.UserBean;
import com.simpleague.util.ApplicationUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class PasswordHelper {
    private static final Logger logger = Logger.getLogger(PasswordHelper.class.getName());
    
    @Inject
    private UserBean userBean;
    
    @Inject
    private MailBean mailBean;
    
    public void process(Credentials credentials) throws Exception {
        logger.log(Level.INFO, "in process()");
        User user = userBean.findByEmail(credentials.getEmail());
        user.getCredentials().setPasswordText(credentials.getPasswordText());
        user.setPasswordCount(0);
        user = userBean.update(user);
        mailBean.send(user.getCredentials().getEmail(),
                ApplicationUtil.getResourceString("messages", "password.email.subject"),
                ApplicationUtil.getResourceString("messages", "password.email.text"));
    }
}
