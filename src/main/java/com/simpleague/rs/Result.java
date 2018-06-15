package com.simpleague.rs;

public enum Result {
    SUCCESS("success"),
    FAILURE("failure"),
    INVALID("invalid");
    
    private String value;
    
    Result(String value) {
        this.value = value;
    }
    
    public String value() {
        return value;
    }
}
