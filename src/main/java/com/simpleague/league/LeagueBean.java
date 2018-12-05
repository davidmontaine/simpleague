package com.simpleague.league;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class LeagueBean {
    @PersistenceContext(unitName = "simpleaguePU")
    private EntityManager em;

    public void create(League league) {
        em.persist(league);
        em.flush();
        em.refresh(league);
    }
    
    public League find(Integer id) {
        return em.find(League.class, id);
    }
    
    public League findByName(String name) {
        League league = null;        
        List<League> leagues = em.createNamedQuery("League.FindByName", League.class).setParameter("name", name).getResultList();
        
        if(!leagues.isEmpty()){
            league = leagues.get(0);
        }        
        return league;
    }    

    public List<League> findByUserEmail(String userEmail) {
        List<League> leagues = em.createNamedQuery("League.FindByUserEmail", League.class).setParameter("userEmail", userEmail).getResultList();
        return leagues;
    }        

    public List<League> findByUserId(Integer userId) {
        List<League> leagues = em.createNamedQuery("League.FindByUserId", League.class).setParameter("userId", userId).getResultList();
        return leagues;
    }            
    
    public League update(League league) {
        if (!em.contains(league)) {
            league = em.merge(league);
        }        
        em.flush();
        em.refresh(league);
        return league;
    }
    
    public void delete(League league) {
        if (!em.contains(league)) {
            league = em.merge(league);
        }        
        em.remove(league);
    }   
    
    public List<League> createLeagues(int count) {
        List<League> leagues = new ArrayList<>();
        
        for (int i = 0; i < count; i++) {
            League league = new League();
            league.setId(i);
            league.setName("League " + i);
            league.setCreatedDate(new Date());
            league.setModifiedDate(new Date());
            leagues.add(league);
        }
        return leagues;
    }    
}
