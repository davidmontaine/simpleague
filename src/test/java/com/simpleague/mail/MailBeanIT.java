package com.simpleague.mail;

import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class MailBeanIT {
    @Inject
    private MailBean mailBean;
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
            .addClasses(MailBean.class)
            .addAsResource("mail.properties");                
    }

    @Test
    public void testSend() throws Exception {
        System.out.println("send");
        mailBean.send("dave@simpleague.com", "MailBeanIT Subject", "MailBeanIT Body");
    }        
}
