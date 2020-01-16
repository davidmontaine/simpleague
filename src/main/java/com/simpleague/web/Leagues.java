package com.simpleague.web;

import com.simpleague.league.League;
import com.simpleague.league.LeagueBean;
import com.simpleague.user.User;
import com.simpleague.user.UserBean;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class Leagues implements Serializable {
    private static final Logger logger = Logger.getLogger(Leagues.class.getName());            
    
    private List<League> leagues;
    private Map<League, Boolean> checkedLeagues = new HashMap<>();    
    private League selectedLeague;
    private String error;

    @Inject
    private LeagueBean leagueBean;
    
    @Inject
    private Login login;
    
    @Inject
    private UserBean userBean;    
     
    @PostConstruct
    public void init() {
        logger.log(Level.INFO, "in init()");        
        leagues = leagueBean.findAll();
        leagues.forEach(league -> {checkedLeagues.put(league, Boolean.FALSE);});
    }
    
    public Map<League, Boolean> getCheckedLeagues() {
        return checkedLeagues;
    }
 
    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
    }            
    
    public List<League> getLeagues() {
        return leagues;
    }    
    
    public League getSelectedLeague() {
        return selectedLeague;
    }
 
    public void setSelectedLeague(League selectedLeague) {
        this.selectedLeague = selectedLeague;
    }
    
    public String add() {
        selectedLeague = new League();
        return null;
    }            
    
    public String delete() {
        try {
            logger.log(Level.INFO, "in delete");          
            int cnt = -1;
            Set<League> deleteLeagues = new HashSet<>();

            for (Boolean checked : checkedLeagues.values()) {
                cnt++;

                if (checked) {
                    deleteLeagues.add((League)checkedLeagues.keySet().toArray()[cnt]);
                }
            }
            leagueBean.delete(deleteLeagues);
            return "/auth/leagues.xhtml";                                
        } catch (Exception e) {
            logger.log(Level.INFO, "exception: {0}", e.getMessage());                
            error = FacesUtil.getResourceString("msg", "leagues.exception");                        
        }
        return null;
    }                
    
    public String select() {
        return null;
    }        
    
    public String submit() {
        try {
            logger.log(Level.INFO, "in submit");  
            
            if (selectedLeague.getId() == null) {
                User user = userBean.findByEmail(login.getCredentials().getEmail());
                selectedLeague.setUser(user);
                leagueBean.create(selectedLeague);
            } else {
                leagueBean.update(selectedLeague);
            }
            selectedLeague = null;
            return "/auth/leagues.xhtml";                    
        } catch (Exception e) {
            logger.log(Level.INFO, "exception: {0}", e.getMessage());                
            error = FacesUtil.getResourceString("msg", "leagues.exception");            
        }
        return null;
    }    
}
