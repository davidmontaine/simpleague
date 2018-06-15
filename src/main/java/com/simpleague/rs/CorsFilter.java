package com.simpleague.rs;

import com.simpleague.util.StringUtil;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

@Provider
public class CorsFilter implements ContainerResponseFilter {
    private static final Logger logger = Logger.getLogger(CorsFilter.class.getName());                    
    private static final String PROPERTIES = "/cors.properties";    
    
    @Context
    private HttpServletRequest req;
    
    private Properties prop = new Properties();
    
    @PostConstruct
    public void init() {
        logger.log(Level.INFO, "in CorsFilter init()");                    
        
        try (final InputStream stream = this.getClass().getResourceAsStream(PROPERTIES)) {
            prop.load(stream);
        } catch (Exception e) {
            logger.log(Level.INFO, "exception: {0}", e.getMessage());            
        }
    }
    
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext response) {
        logger.log(Level.INFO, "in filter()");
        String origin = StringUtil.defaultValue(req.getHeader("Origin")).toLowerCase();
        
        if ("true".equals(prop.getProperty(origin))) {
            response.getHeaders().putSingle("Access-Control-Allow-Origin", req.getHeader("Origin"));
        }
        response.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");        
        response.getHeaders().putSingle("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Authorization");
    }
}
