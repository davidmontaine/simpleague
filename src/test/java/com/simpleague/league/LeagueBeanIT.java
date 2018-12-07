package com.simpleague.league;

import com.simpleague.user.Credentials;
import com.simpleague.user.User;
import com.simpleague.user.UserBean;
import com.simpleague.util.StringUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
public class LeagueBeanIT {
    @Inject
    private UserBean userBean;    
    
    @Inject
    private LeagueBean leagueBean;
    
    private String expName2 = "Dave";
    private String expEmail = "dave@itest.com";
    private String expPasswordText = "daveitest";
    private String expGroupName = "USER";
    private Integer expEmailCount = 0;
    private Integer expPasswordCount = 0;
    private String expVerified = "N";

    private String expName = "National League";
    private String expDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());                
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
            .addClasses(StringUtil.class, Credentials.class, User.class, UserBean.class, League.class, LeagueBean.class)
            .addAsResource("META-INF/persistence.xml");                
    }

    @Before
    public void testCreate() {
        System.out.println("create");

        Credentials cred = new Credentials();
        cred.setEmail(expEmail);
        cred.setPasswordText(expPasswordText);
        
        User user = new User();
        user.setName(expName2);
        user.setCredentials(cred);
        
        userBean.create(user);
        
        League league = new League();
        league.setName(expName);
        league.setUser(user);
        
        leagueBean.create(league);
    }
    
    @After
    public void testDelete() {
        System.out.println("delete");
        String expNameNew = "National League New";
        League league = leagueBean.findByName(expNameNew);
        leagueBean.delete(league);
        
        User user = userBean.findByEmail(expEmail);
        userBean.delete(user);        
    }        

    @Test
    public void test() {
        System.out.println("findByName");
        League league = leagueBean.findByName(expName);
        assertFields(league);
        assertEquals(expName, league.getName());        
        assertEquals(null, league.getModifiedDate());                                
        
        System.out.println("find");
        Integer leagueId = league.getId();
        league = leagueBean.find(leagueId);
        assertFields(league);
        assertEquals(expName, league.getName());        
        assertEquals(null, league.getModifiedDate());                                        
        
        System.out.println("findByUserEmail");
        String userEmail = league.getUser().getCredentials().getEmail();
        List<League> leagues = leagueBean.findByUserEmail(userEmail);
        league = leagues.get(0);
        assertFields(league);
        assertEquals(expName, league.getName());        
        assertEquals(null, league.getModifiedDate());                                                

        System.out.println("findByUserId");
        Integer userId = league.getUser().getId();
        leagues = leagueBean.findByUserId(userId);
        league = leagues.get(0);
        assertFields(league);
        assertEquals(expName, league.getName());        
        assertEquals(null, league.getModifiedDate());                                                        
        
        System.out.println("update");        
        String expNameNew = "National League New";
        league.setName(expNameNew);
        league = leagueBean.update(league);
        assertFields(league);
        assertEquals(expNameNew, league.getName());        
        assertEquals(expDate, new SimpleDateFormat("dd/MM/yyyy").format(league.getModifiedDate()));                                        
    }        
    
    public void assertFields(League league) {
        assertNotNull(league);
        assertNotNull(league.getId());
        assertNotNull(league.getUser());                
        assertNotNull(league.getUser().getId());        
        assertNotNull(league.getUser().getCredentials());
        assertEquals(expEmail, league.getUser().getCredentials().getEmail());
        assertEquals(expGroupName, league.getUser().getGroupName());
        assertNotNull(league.getUser().getUuid());
        assertEquals(expEmailCount, league.getUser().getEmailCount());
        assertEquals(expPasswordCount, league.getUser().getPasswordCount());
        assertEquals(expVerified, league.getUser().getVerified());
        assertEquals(expDate, new SimpleDateFormat("dd/MM/yyyy").format(league.getUser().getCreatedDate()));                                        
        assertEquals(expDate, new SimpleDateFormat("dd/MM/yyyy").format(league.getCreatedDate()));                                
    }
}
