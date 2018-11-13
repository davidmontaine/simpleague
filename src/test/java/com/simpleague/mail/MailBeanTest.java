package com.simpleague.mail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MailBeanTest {
    @Mock
    private MailBean mailBean;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSend() throws Exception {
        System.out.println("send");
        mailBean.send("dave@simpleague.com", "Subject", "Body");
        mailBean.send(new String[] {"dave@simpleague.com"}, "Subject", "Body");        
    }
}
