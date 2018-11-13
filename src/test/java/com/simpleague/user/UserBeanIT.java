package com.simpleague.user;

import com.simpleague.util.StringUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class UserBeanIT {
    @Inject
    private UserBean userBean;
    
    private String expName = "Dave";
    private String expEmail = "dave@itest.com";
    private String expPasswordText = "daveitest";
    private String expGroupName = "USER";
    private Integer expEmailCount = 0;
    private Integer expPasswordCount = 0;
    private String expVerified = "N";
    private String expCreatedDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());            
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
            .addClasses(StringUtil.class, Credentials.class, User.class, UserBean.class)
            .addAsResource("META-INF/persistence.xml");                
    }

    @Before
    public void testCreate() throws Exception {
        System.out.println("create");

        Credentials cred = new Credentials();
        cred.setEmail(expEmail);
        cred.setPasswordText(expPasswordText);
        
        User user = new User();
        user.setName(expName);
        user.setCredentials(cred);
        
        userBean.create(user);
    }
    
    @After
    public void testDelete() throws Exception {
        System.out.println("delete");
        User user = userBean.findByEmail(expEmail);
        userBean.delete(user);
    }        

    @Test
    public void test() throws Exception {
        System.out.println("findByEmail");
        User user = userBean.findByEmail(expEmail);
        
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(expName, user.getName());
        assertNotNull(user.getCredentials());
        assertEquals(expEmail, user.getCredentials().getEmail());
        assertEquals(expGroupName, user.getGroupName());
        assertNotNull(user.getUuid());
        assertEquals(expEmailCount, user.getEmailCount());
        assertEquals(expPasswordCount, user.getPasswordCount());
        assertEquals(expVerified, user.getVerified());
        assertEquals(expCreatedDate, new SimpleDateFormat("dd/MM/yyyy").format(user.getCreatedDate()));                        

        System.out.println("update");        
        String expNameNew = "Dave New";
        user.setName(expNameNew);
        user = userBean.update(user);
        
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(expNameNew, user.getName());
        assertNotNull(user.getCredentials());
        assertEquals(expEmail, user.getCredentials().getEmail());
        assertEquals(expGroupName, user.getGroupName());
        assertNotNull(user.getUuid());
        assertEquals(expEmailCount, user.getEmailCount());
        assertEquals(expPasswordCount, user.getPasswordCount());
        assertEquals(expVerified, user.getVerified());
        assertEquals(expCreatedDate, new SimpleDateFormat("dd/MM/yyyy").format(user.getCreatedDate()));                                
        
        System.out.println("findByEmail");        
        user = userBean.findByEmail(expEmail);        
        
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(expNameNew, user.getName());
        assertNotNull(user.getCredentials());
        assertEquals(expEmail, user.getCredentials().getEmail());
        assertEquals(expGroupName, user.getGroupName());
        assertNotNull(user.getUuid());
        assertEquals(expEmailCount, user.getEmailCount());
        assertEquals(expPasswordCount, user.getPasswordCount());
        assertEquals(expVerified, user.getVerified());
        assertEquals(expCreatedDate, new SimpleDateFormat("dd/MM/yyyy").format(user.getCreatedDate()));                
    }        
}
