package com.simpleague.rs;

import com.simpleague.helper.PasswordHelper;
import com.simpleague.token.TokenBean;
import com.simpleague.user.Credentials;
import com.simpleague.user.User;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Path("user")
public class ResetPasswordResource {
    private static final Logger logger = Logger.getLogger(ResetPasswordResource.class.getName());            

    @Inject
    private PasswordHelper passwordHelper;    

    @Inject
    private TokenBean tokenBean;        
    
    @Context
    private HttpServletRequest req;        
    
    @Secured
    @POST
    @Path("resetPassword")
    public Response resetPassword(CredentialsCurrent credentialsCurrent) {
        try {
            logger.log(Level.INFO, "in resetPassword()");
            String authorizationHeader = req.getHeader(HttpHeaders.AUTHORIZATION);

            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                throw new Exception("Authorization header must be provided");
            }
            String token = authorizationHeader.substring("Bearer".length()).trim();
            User user = tokenBean.get(token);
            
            if (!credentialsCurrent.getPasswordCurrent().equals(user.getCredentials().getPasswordText())) {        
                throw new Exception("Current Password is invalid");
            }
            passwordHelper.process(credentialsCurrent);
            user.getCredentials().setPasswordText(credentialsCurrent.getPasswordText());
            return new Response(Result.SUCCESS);
        } catch (Exception e) {
            logger.log(Level.INFO, "exception: {0}", e.getMessage());                        
        }
        return new Response(Result.FAILURE);
    }    
}

@XmlRootElement
@XmlType(name = "")
class CredentialsCurrent extends Credentials {
    private String passwordCurrent;

    public String getPasswordCurrent() {
        return passwordCurrent;
    }

    public void setPasswordCurrent(String passwordCurrent) {
        this.passwordCurrent = passwordCurrent;
    }        
}
