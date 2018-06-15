package com.simpleague.user;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserBean {
    @PersistenceContext(unitName = "simpleaguePU")
    private EntityManager em;

    public void create(User user) {
        em.persist(user);
        em.flush();
        em.refresh(user);
    }
    
    public User find(Integer id) {
        return em.find(User.class, id);
    }
    
    public User findByEmail(String email) {
        User user = null;        
        List<User> users = em.createNamedQuery("User.FindByEmail", User.class).setParameter("email", email).getResultList();
        
        if(!users.isEmpty()){
            user = users.get(0);
        }        
        return user;
    }    
    
    public User findByUuid(String uuid) {
        User user = null;        
        List<User> users = em.createNamedQuery("User.FindByUuid", User.class).setParameter("uuid", uuid).getResultList();
        
        if(!users.isEmpty()){
            user = users.get(0);
        }        
        return user;        
    }        
    
    public User update(User user) {
        user = em.merge(user);
        em.flush();
        em.refresh(user);
        return user;
    }
}
