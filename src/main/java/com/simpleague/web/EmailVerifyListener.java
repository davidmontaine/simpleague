package com.simpleague.web;

import com.simpleague.user.User;
import com.simpleague.user.UserBean;
import javax.faces.application.NavigationHandler;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;

public class EmailVerifyListener implements PhaseListener {
    private static final String VIEW_ID = FacesUtil.REQUEST_CONSTRAINED_PATH + "/emailVerify.xhtml";
    public static final String VIEW_OUTCOME = "email-verify";

    @Inject
    private Login login;
    
    @Inject
    private UserBean userBean;
 
    @Override
    public void afterPhase(PhaseEvent event) {
        String requestPath = FacesUtil.getRequestServletPath();
        
        if (requestPath != null && !requestPath.contains(FacesUtil.REQUEST_CONSTRAINED_PATH + "/")) {
            return;
        }
        if (VIEW_ID.equals(requestPath)) {
            return;
        }        
        String email = login.getCredentials().getEmail();
        
        if (email == null) {
            return;
        }
        User user = userBean.findByEmail(email);

        // User is authenticated at this point however, won't be able to access any
        // constrained resources until email address is validated.
        if (user != null && !"Y".equals(user.getVerified())) {
            NavigationHandler nh = FacesUtil.getApplication().getNavigationHandler();
            nh.handleNavigation(FacesUtil.getFacesContext(), null, VIEW_OUTCOME);                
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {}
    
    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }    
}
