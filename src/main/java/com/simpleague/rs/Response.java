package com.simpleague.rs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class Response {
    private String result = Result.FAILURE.value();

    public Response() {}
    
    public Response(Result result) {
        this.result = result.value();
    }
}
