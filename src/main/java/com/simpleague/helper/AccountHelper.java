package com.simpleague.helper;

import com.simpleague.mail.MailBean;
import com.simpleague.user.User;
import com.simpleague.user.UserBean;
import com.simpleague.util.ApplicationUtil;
import com.simpleague.web.FacesUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class AccountHelper {
    private static final Logger logger = Logger.getLogger(AccountHelper.class.getName());    
    
    @Inject
    private UserBean userBean;
    
    @Inject
    private MailBean mailBean;
    
    public boolean process(User user, String emailCurrent, String uri) throws Exception {
        logger.log(Level.INFO, "in process()");
        boolean emailChanged = false;
        
        if (!user.getCredentials().getEmail().equalsIgnoreCase(emailCurrent)) {
            emailChanged = true;
            user.resetEmailRelated();
        }
        user = userBean.update(user);
            
        if (emailChanged) {
            email(user, uri);
        }
        return emailChanged;
    }
    
    public void email(User user, String uri) throws Exception {
        logger.log(Level.INFO, "in email()");        
        mailBean.send(user.getCredentials().getEmail(),
                ApplicationUtil.getResourceString("messages", "emailVerify.email.subject"),
                ApplicationUtil.getResourceString("messages", "emailVerify.email.text") + " " + uri +
                        FacesUtil.EMAIL_VERIFY_COMPLETE + user.getUuid());
    }    
}
