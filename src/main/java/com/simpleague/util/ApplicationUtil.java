package com.simpleague.util;

import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;

public class ApplicationUtil {
    public static String getResourceString(String base, String key) {
        return ResourceBundle.getBundle(base).getString(key);
    }

    // Returns something similar to http://localhost:8080/simpleague or http://www.simpleague.com.
    // Result is different if web context is used.
    public static String getUri(HttpServletRequest req) {
        String uri = getUriHost(req) + req.getRequestURI().substring(0, req.getRequestURI().indexOf("/", 1));
        return uri;
    }    

    // Returns something similar to http://localhost:8080 or http://www.simpleague.com.
    // Port is excluded if 80.
    public static String getUriHost(HttpServletRequest req) {
        String uri = req.getScheme() + "://" + req.getServerName();
        
        if (req.getServerPort() != 80) {
            uri += ":" + req.getServerPort();
        }
        return uri;
    }        
}
