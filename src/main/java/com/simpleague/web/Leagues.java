package com.simpleague.web;

import com.simpleague.league.League;
import com.simpleague.league.LeagueBean;
import java.io.Serializable;
import java.util.List;
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

    @Inject
    private LeagueBean leagueBean;
     
    private List<League> leagues;
     
    private League selectedLeague;
     
    @PostConstruct
    public void init() {
        logger.log(Level.INFO, "in init()");        
        leagues = leagueBean.createLeagues(50);
    }
 
    public List<League> getLeagues() {
        return leagues;
    }    
 
    public League getSelectedLeague() {
        return selectedLeague;
    }
 
    public void setSelectedleague(League selectedLeague) {
        this.selectedLeague = selectedLeague;
    }
}
