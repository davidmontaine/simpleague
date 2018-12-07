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
    
    private User user;
    private User newUser;
    
    private Integer expId = 1;
    private String expName = "Dave";
    private String expEmail = "dave@test.com";
    private String expPasswordText = "davetest";
    private String expGroupName = "USER";
    private String expUuid = "123456789";    
    private Integer expEmailCount = 0;
    private Integer expPasswordCount = 0;
    private String expVerified = "Y";
    private String expDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());                
    
    @Before
    public void setUp() {
        userBean = new UserBean();
        MockitoAnnotations.initMocks(this);
        
        Credentials cred = new Credentials();
        cred.setEmail(expEmail);
        cred.setPasswordText(expPasswordText);
        
        user = new User();
        user.setId(expId);
        user.setName(expName);
        user.setCredentials(cred);
        user.setGroupName(expGroupName);
        user.setUuid(expUuid);
        user.setEmailCount(expEmailCount);
        user.setPasswordCount(expPasswordCount);
        user.setVerified(expVerified);
        user.setCreatedDate(new Date());
        user.setModifiedDate(new Date());        
        
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
    public void testCreate() {
        System.out.println("create");
        userBean.create(user);
        assertFields(newUser);
    }

    @Test
    public void testFind() {
        System.out.println("find");
        Integer id = 1;
        User result = userBean.find(id);
        assertFields(result);
    }    
    
    @Test
    public void testFindByEmail() {
        System.out.println("findByEmail");
        String email = "dave@test.com";
        User result = userBean.findByEmail(email);
        assertFields(result);
    }

    @Test
    public void testFindByUuid() {
        System.out.println("findByUuid");
        String uuid = "123456789";
        User result = userBean.findByUuid(uuid);
        assertFields(result);
    }

    @Test
    public void testUpdate() {
        System.out.println("update");
        User result = userBean.update(user);
        assertFields(result);        
        assertEquals(expDate, new SimpleDateFormat("dd/MM/yyyy").format(result.getModifiedDate()));        
    }

    @Test
    public void testDelete() {
        System.out.println("delete");
        userBean.delete(user);
    }    
    
    private void assertFields(User user) {
        assertNotNull(user);
        assertEquals(expId, user.getId());
        assertEquals(expName, user.getName());
        assertNotNull(user.getCredentials());
        assertEquals(expEmail, user.getCredentials().getEmail());
        assertEquals(expPasswordText, user.getCredentials().getPasswordText());
        assertEquals(expGroupName, user.getGroupName());
        assertEquals(expUuid, user.getUuid());
        assertEquals(expEmailCount, user.getEmailCount());
        assertEquals(expPasswordCount, user.getPasswordCount());
        assertEquals(expVerified, user.getVerified());
        assertEquals(expDate, new SimpleDateFormat("dd/MM/yyyy").format(user.getCreatedDate()));        
    }
}
