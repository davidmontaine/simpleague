package com.simpleague.helper;

import com.simpleague.mail.MailBean;
import com.simpleague.user.User;
import com.simpleague.user.UserBean;
import com.simpleague.util.ApplicationUtil;
import com.simpleague.util.StringUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class ForgotPasswordHelper {
    private static final Logger logger = Logger.getLogger(ForgotPasswordHelper.class.getName());        
    private static final int PASSWORD_COUNT = 3;
    
    @Inject
    private UserBean userBean;
    
    @Inject
    private MailBean mailBean;
    
    public boolean process(User user) throws Exception {
        logger.log(Level.INFO, "in process()");        
        
        if (user.getPasswordCount() < PASSWORD_COUNT) {
            String password = StringUtil.genPassword();
            user.getCredentials().setPasswordText(password);
            user.setPasswordCount(user.getPasswordCount() + 1);                
            user = userBean.update(user);
            mailBean.send(user.getCredentials().getEmail(),
                    ApplicationUtil.getResourceString("messages", "forgotPassword.email.subject"),
                    ApplicationUtil.getResourceString("messages", "forgotPassword.email.text") + " " + password);
            return true;
        }
        mailBean.send("admin@simpleague.com",
                ApplicationUtil.getResourceString("messages", "forgotPassword.email.error.subject"),
                ApplicationUtil.getResourceString("messages", "forgotPassword.email.error.text1") + " " +
                        user.getCredentials().getEmail() + " " +
                        ApplicationUtil.getResourceString("messages", "forgotPassword.email.error.text2"));                                
        return false;
    }
}
