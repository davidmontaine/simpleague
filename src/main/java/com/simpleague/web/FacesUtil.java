package com.simpleague.web;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class FacesUtil {
    public static final String REQUEST_CONSTRAINED_PATH = "/auth";    
    public static final String EMAIL_VERIFY_COMPLETE = "/emailVerifyComplete.xhtml?uuid=";    
    
    public static Application getApplication() {
        return getFacesContext().getApplication();
    }  
    
    public static ExternalContext getExternalContext() {
        return getFacesContext().getExternalContext();
    }        
    
    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }        
    
    public static String getRemoteUser() {
        return getExternalContext().getRemoteUser();
    }            
    
    public static HttpServletRequest getRequest() {
        return (HttpServletRequest)getExternalContext().getRequest();
    }
    
    public static String getRequestServletPath() {
        return getExternalContext().getRequestServletPath();
    }                
    
    public static String getResourceString(String var, String key) {
        return getApplication().getResourceBundle(getFacesContext(), var).getString(key);
    }

    // Returns something similar to http://localhost:8080/simpleague or http://www.simpleague.com.
    // Port is excluded if 80.
    // Result is different if web context is used.    
    public static String getUri() {
        String uri = FacesUtil.getRequest().getScheme() + "://" + FacesUtil.getRequest().getServerName();
        
        if (FacesUtil.getRequest().getServerPort() != 80) {
            uri += ":" + FacesUtil.getRequest().getServerPort() +
                    FacesUtil.getRequest().getRequestURI().substring(0, FacesUtil.getRequest().getRequestURI().indexOf("/", 1));
        }
        return uri;
    }

    public static UIInput getValidatorComponent(UIComponent component, String attribute) {
        return (UIInput)component.getAttributes().get(attribute);
    }    

    public static String getValidatorMessage(UIComponent component, String attribute) {
        String msg = getValidatorParameter(component, attribute);
        
        if (msg == null) {
            msg = attribute + " not specified in page";
        }
        return msg;
    }    
    
    public static String getValidatorParameter(UIComponent component, String attribute) {
        return (String)component.getAttributes().get(attribute);
    }
    
    public static String setValidatorParameter(UIComponent component, String attribute, String value) {
        return (String)component.getAttributes().put(attribute, value);
    }    

    public static void logout() throws Exception {
        getRequest().getSession().invalidate();
        getRequest().logout();        
    }
}
