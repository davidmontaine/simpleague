package com.simpleague.rs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class LoginResponse extends Response {
    private String token;
    private String verified;

    public LoginResponse() {}
    
    public LoginResponse(String token, String verified) {
        super(Result.SUCCESS);
        this.token = token;
        this.verified = verified;
    }
}
