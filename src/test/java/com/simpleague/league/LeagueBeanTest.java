package com.simpleague.league;

import com.simpleague.user.User;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

public class LeagueBeanTest {
    @Mock
    private EntityManager em;
    
    @Mock
    private TypedQuery<League> query;    
    
    @InjectMocks
    private LeagueBean leagueBean;
    
    private League league;
    private League newLeague;
    
    private Integer expId = 1;
    private String expName = "National League";
    private String expDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());                
    
    @Before
    public void setUp() {
        leagueBean = new LeagueBean();
        MockitoAnnotations.initMocks(this);
        
        league = new League();
        league.setId(expId);
        league.setName(expName);
        league.setUser(new User());
        league.setCreatedDate(new Date());
        league.setModifiedDate(new Date());
        
        newLeague = null;
        
        doAnswer((InvocationOnMock invocation) -> {
            newLeague = (League)invocation.getArguments()[0];
            return null;
        }).when(em).persist(any(League.class));
        
        when(em.find(any(Class.class), any(Integer.class))).thenReturn(league);

        when(em.createNamedQuery(any(String.class), any(Class.class))).thenReturn(query);
        when(query.setParameter(any(String.class), any())).thenReturn(query);
        ArrayList<League> leagues = new ArrayList<>();
        leagues.add(league);
        when(query.getResultList()).thenReturn(leagues);
        
        when(em.merge(any(League.class))).thenReturn(league);        
    }

    @Test
    public void testCreate() {
        System.out.println("create");
        leagueBean.create(league);
        assertFields(newLeague);
    }

    @Test
    public void testFind() {
        System.out.println("find");
        Integer id = 1;
        League result = leagueBean.find(id);
        assertFields(result);
    }    
    
    @Test
    public void testFindByName() {
        System.out.println("findByName");
        String name = "National League";
        League result = leagueBean.findByName(name);
        assertFields(result);
    }

    @Test
    public void testFindByUserEmail() {
        System.out.println("findByUserEmail");
        String userEmail = "dave@test.com";
        List<League> results = leagueBean.findByUserEmail(userEmail);
        assertNotNull(results);
        League result = results.get(0);
        assertFields(result);
    }    

    @Test
    public void testFindByUserId() {
        System.out.println("findByUserId");
        Integer userId = 1;
        List<League> results = leagueBean.findByUserId(userId);
        assertNotNull(results);
        League result = results.get(0);
        assertFields(result);
    }        
    
    @Test
    public void testUpdate() {
        System.out.println("update");
        League result = leagueBean.update(league);
        assertFields(result);
        assertEquals(expDate, new SimpleDateFormat("dd/MM/yyyy").format(result.getModifiedDate()));        
    }

    @Test
    public void testDelete() {
        System.out.println("delete");
        leagueBean.delete(league);
    }    
    
    private void assertFields(League league) {
        assertNotNull(league);
        assertEquals(expId, league.getId());
        assertEquals(expName, league.getName());
        assertNotNull(league.getUser());
        assertEquals(expDate, new SimpleDateFormat("dd/MM/yyyy").format(league.getCreatedDate()));
    }    
}
