package com.simpleague.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;

public class UserBeanTest {
    @Mock
    private EntityManager em;
    
    @Mock
    private TypedQuery<User> query;    
    
    @InjectMocks
    private UserBean userBean;
    
    private Credentials cred;
    private User user;
    private User newUser;
    
    @Before
    public void setUp() {
        userBean = new UserBean();
        MockitoAnnotations.initMocks(this);
        
        cred = new Credentials();
        cred.setEmail("dave@test.com");
        cred.setPasswordText("davetest");
        
        user = new User();
        user.setId(1);
        user.setName("Dave");
        user.setCredentials(cred);
        user.setGroupName("group");
        user.setUuid("123456789");
        user.setEmailCount(0);
        user.setPasswordCount(0);
        user.setVerified("Y");
        user.setCreatedDate(new Date());
        
        newUser = null;
        
        doAnswer((InvocationOnMock invocation) -> {
            newUser = (User)invocation.getArguments()[0];
            return null;
        }).when(em).persist(any(User.class));
        
        when(em.find(any(Class.class), any(Integer.class))).thenReturn(user);

        when(em.createNamedQuery(any(String.class), any(Class.class))).thenReturn(query);
        when(query.setParameter(any(String.class), any())).thenReturn(query);
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        when(query.getResultList()).thenReturn(users);
        
        when(em.merge(any(User.class))).thenReturn(user);        
    }

    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        
        Integer expId = 1;
        String expName = "Dave";
        String expEmail = "dave@test.com";
        String expPasswordText = "davetest";
        String expGroupName = "group";
        String expUuid = "123456789";
        Integer expEmailCount = 0;
        Integer expPasswordCount = 0;
        String expVerified = "Y";
        String expCreatedDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());        
        
        userBean.create(user);
        
        assertNotNull(newUser);
        assertEquals(expId, newUser.getId());
        assertEquals(expName, newUser.getName());
        assertNotNull(newUser.getCredentials());
        assertEquals(expEmail, newUser.getCredentials().getEmail());
        assertEquals(expPasswordText, newUser.getCredentials().getPasswordText());
        assertEquals(expGroupName, newUser.getGroupName());
        assertEquals(expUuid, newUser.getUuid());
        assertEquals(expEmailCount, newUser.getEmailCount());
        assertEquals(expPasswordCount, newUser.getPasswordCount());
        assertEquals(expVerified, newUser.getVerified());
        assertEquals(expCreatedDate, new SimpleDateFormat("dd/MM/yyyy").format(newUser.getCreatedDate()));
    }

    @Test
    public void testFind() {
        System.out.println("find");
        Integer id = 1;
        
        Integer expId = 1;
        String expName = "Dave";
        String expEmail = "dave@test.com";
        String expPasswordText = "davetest";
        String expGroupName = "group";
        String expUuid = "123456789";
        Integer expEmailCount = 0;
        Integer expPasswordCount = 0;
        String expVerified = "Y";
        String expCreatedDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());        
        
        User result = userBean.find(id);
        
        assertNotNull(result);
        assertEquals(expId, result.getId());
        assertEquals(expName, result.getName());
        assertNotNull(result.getCredentials());
        assertEquals(expEmail, result.getCredentials().getEmail());
        assertEquals(expPasswordText, result.getCredentials().getPasswordText());
        assertEquals(expGroupName, result.getGroupName());
        assertEquals(expUuid, result.getUuid());
        assertEquals(expEmailCount, result.getEmailCount());
        assertEquals(expPasswordCount, result.getPasswordCount());
        assertEquals(expVerified, result.getVerified());
        assertEquals(expCreatedDate, new SimpleDateFormat("dd/MM/yyyy").format(result.getCreatedDate()));
    }    
    
    @Test
    public void testFindByEmail() throws Exception {
        System.out.println("findByEmail");
        String email = "dave@test.com";
        
        Integer expId = 1;
        String expName = "Dave";
        String expEmail = "dave@test.com";
        String expPasswordText = "davetest";
        String expGroupName = "group";
        String expUuid = "123456789";
        Integer expEmailCount = 0;
        Integer expPasswordCount = 0;
        String expVerified = "Y";
        String expCreatedDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());        
        
        User result = userBean.findByEmail(email);
        
        assertNotNull(result);
        assertEquals(expId, result.getId());
        assertEquals(expName, result.getName());
        assertNotNull(result.getCredentials());
        assertEquals(expEmail, result.getCredentials().getEmail());
        assertEquals(expPasswordText, result.getCredentials().getPasswordText());
        assertEquals(expGroupName, result.getGroupName());
        assertEquals(expUuid, result.getUuid());
        assertEquals(expEmailCount, result.getEmailCount());
        assertEquals(expPasswordCount, result.getPasswordCount());
        assertEquals(expVerified, result.getVerified());
        assertEquals(expCreatedDate, new SimpleDateFormat("dd/MM/yyyy").format(result.getCreatedDate()));        
    }

    @Test
    public void testFindByUuid() throws Exception {
        System.out.println("findByUuid");
        String uuid = "123456789";
        
        Integer expId = 1;
        String expName = "Dave";
        String expEmail = "dave@test.com";
        String expPasswordText = "davetest";
        String expGroupName = "group";
        String expUuid = "123456789";
        Integer expEmailCount = 0;
        Integer expPasswordCount = 0;
        String expVerified = "Y";
        String expCreatedDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());        
        
        User result = userBean.findByEmail(uuid);
        
        assertNotNull(result);
        assertEquals(expId, result.getId());
        assertEquals(expName, result.getName());
        assertNotNull(result.getCredentials());
        assertEquals(expEmail, result.getCredentials().getEmail());
        assertEquals(expPasswordText, result.getCredentials().getPasswordText());
        assertEquals(expGroupName, result.getGroupName());
        assertEquals(expUuid, result.getUuid());
        assertEquals(expEmailCount, result.getEmailCount());
        assertEquals(expPasswordCount, result.getPasswordCount());
        assertEquals(expVerified, result.getVerified());
        assertEquals(expCreatedDate, new SimpleDateFormat("dd/MM/yyyy").format(result.getCreatedDate()));        
    }

    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        
        Integer expId = 1;
        String expName = "Dave";
        String expEmail = "dave@test.com";
        String expPasswordText = "davetest";
        String expGroupName = "group";
        String expUuid = "123456789";
        Integer expEmailCount = 0;
        Integer expPasswordCount = 0;
        String expVerified = "Y";
        String expCreatedDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());        
        
        User result = userBean.update(user);
        
        assertNotNull(result);
        assertEquals(expId, result.getId());
        assertEquals(expName, result.getName());
        assertNotNull(result.getCredentials());
        assertEquals(expEmail, result.getCredentials().getEmail());
        assertEquals(expPasswordText, result.getCredentials().getPasswordText());
        assertEquals(expGroupName, result.getGroupName());
        assertEquals(expUuid, result.getUuid());
        assertEquals(expEmailCount, result.getEmailCount());
        assertEquals(expPasswordCount, result.getPasswordCount());
        assertEquals(expVerified, result.getVerified());
        assertEquals(expCreatedDate, new SimpleDateFormat("dd/MM/yyyy").format(result.getCreatedDate()));
    }
}
