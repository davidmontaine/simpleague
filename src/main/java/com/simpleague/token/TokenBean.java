package com.simpleague.token;

import com.simpleague.user.User;
import com.simpleague.util.StringUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedExecutorService;

@Singleton
@Startup
@Lock(LockType.READ)
public class TokenBean {
    private static final Logger logger = Logger.getLogger(TokenBean.class.getName());
    private static final int INTERVAL = 3600000;    
    private static final int SESSION = 300000;
    
    // Could be a database.
    private Map<String, StampedUser> tokens = new HashMap();    
        
    @Resource
    private ManagedExecutorService mes;

    // Start thread which runs periodically to clean up expired tokens.
    @PostConstruct
    public void init() {
        logger.log(Level.INFO, "in init()");
        
        mes.submit(() -> {
            try {
                Thread.sleep(INTERVAL);
                logger.log(Level.INFO, "starting cleanup");
                Set<String> keys = tokens.keySet();
                
                keys.forEach(token -> {
                    StampedUser stampedUser = tokens.get(token);
                    
                    if ((stampedUser != null) && (new Date().getTime() - stampedUser.getTimestamp().getTime() >= SESSION)) {
                        logger.log(Level.INFO, "remove: {0}", stampedUser.getUser());
                        tokens.remove(token);
                    }
                });
            } catch (InterruptedException e) {
                logger.log(Level.INFO, "exception: {0}", StringUtil.getStackTrace(e));
            }
        });
    }

    // Associate token with user and updated timestamp.
    public void put(String token, User user) {
        tokens.put(token, new StampedUser(user));
    }    

    // If token not expired, get associated user and updated timestamp.
    // Otherwise, return null.
    public User get(String token) {
        StampedUser stampedUser = tokens.get(token);
        
        if ((stampedUser != null) && (new Date().getTime() - stampedUser.getTimestamp().getTime() >= SESSION)) {
            return null;
        }
        put(token, stampedUser.getUser());
        return stampedUser.getUser();
    }        

    private class StampedUser {
        private User user = null;
        private Date timestamp = new Date();

        public StampedUser(User user) {
            this.user = user;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public User getUser() {
            return user;
        }
    }
}
