package com.simpleague.mail;

import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@Singleton
@Startup
@Lock(LockType.READ)
public class MailBean {
    private static final Logger logger = Logger.getLogger(MailBean.class.getName());        
    private static final String PROPERTIES = "/mail.properties";
    
    private String fromAddress;
    // Overrides toAddress.
    private String toAddressTesting;
    
    @Resource(name = "SmtpRelay")
    private Session session;
    
    @PostConstruct
    public void init() {
        try (final InputStream stream = this.getClass().getResourceAsStream(PROPERTIES)) {
            logger.log(Level.INFO, "in init()");            
            Properties prop = new Properties();
            prop.load(stream);
            fromAddress = prop.getProperty("fromAddress");
            toAddressTesting = prop.getProperty("toAddressTesting");            
            logger.log(Level.INFO, "fromAddress: {0}", fromAddress);                        
            logger.log(Level.INFO, "toAddressTesting: {0}", toAddressTesting);                        
        } catch (Exception e) {
            logger.log(Level.INFO, "exception: {0}", e.getMessage());            
        }
    }
    
    public void send(String toAddress, String subject, String messageText) throws Exception {
        send(fromAddress, toAddress, subject, messageText);
    }    
	
    public void send(String fromAddress, String[] toAddress, String subject, String messageText) throws Exception {
        String ta = "";

        for (int i = 0; i < toAddress.length; i++) {
            ta += (i == 0 ? "" : ", ") + toAddress[i];
        }
        send(fromAddress, ta, subject, messageText);
    }	    

    public void send(String fromAddress, String toAddress, String subject, String messageText) throws Exception {
        logger.log(Level.INFO, "in send()");            
        
        if (this.toAddressTesting != null) {
            toAddress = this.toAddressTesting;
        }
        Transport.send(createMessage(fromAddress, toAddress, subject, messageText));
    }
    
    private Message createMessage(String fromAddress, String toAddress, String subject, String messageText) throws Exception {
        logger.log(Level.INFO, "in createMessage()");                    
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(fromAddress));
        msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(toAddress));
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        MimeBodyPart mbp = new MimeBodyPart();
        mbp.setText(messageText);
        Multipart mp = new MimeMultipart();
        mp.addBodyPart(mbp);
        msg.setContent(mp);
        return msg;
    }    
}
