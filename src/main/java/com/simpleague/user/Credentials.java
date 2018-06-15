package com.simpleague.user;

import com.simpleague.util.StringUtil;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Embeddable
@XmlRootElement
@XmlType(name = "")
public class Credentials implements Serializable {
    private static final long serialVersionUID = 1L;        
    
    @Column(name = "email")    
    private String email;
    
    @Column(name = "password")
    private String password;
    
    @Transient
    private String passwordText;
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordText() {
        return passwordText;
    }

    public void setPasswordText(String passwordText) {
        this.password = StringUtil.encodeBase64(passwordText);        
        this.passwordText = passwordText;
    }    
}
