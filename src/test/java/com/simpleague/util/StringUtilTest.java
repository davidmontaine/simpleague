package com.simpleague.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class StringUtilTest {
    @Test
    public void testEncodeBase64() {
        System.out.println("encodeBase64");
        String text = "unittest";
        String expResult = "3wgicwnJLy8/AwtCMAnml0RTcSg9iTUqJtBk05+3PDY=";
        String result = StringUtil.encodeBase64(text);
        assertEquals(expResult, result);
    }

    @Test
    public void testGenPasswordLength() {
        System.out.println("genPassword");
        boolean expResult = true;
        boolean result = StringUtil.genPassword().length() >= 8;
        assertEquals(expResult, result);
    }

    @Test
    public void testGenPasswordRandom() {
        System.out.println("genPassword");
        boolean expResult = false;
        boolean result = StringUtil.genPassword().equals(StringUtil.genPassword());
        assertEquals(expResult, result);
    }    

    @Test
    public void testValidateEmail() {
        System.out.println("validateEmail");
        String email = "address@test.com";
        boolean expResult = true;
        boolean result = StringUtil.validateEmail(email);
        assertEquals(expResult, result);
    }    
    
    @Test
    public void testValidateEmailEmpty() {
        System.out.println("validateEmail");
        String email = "";
        boolean expResult = false;
        boolean result = StringUtil.validateEmail(email);
        assertEquals(expResult, result);
    }

    @Test
    public void testValidateEmailSpace() {
        System.out.println("validateEmail");
        String email = "address address@test.com";
        boolean expResult = false;
        boolean result = StringUtil.validateEmail(email);
        assertEquals(expResult, result);
    }        
    
    @Test
    public void testValidateEmailText() {
        System.out.println("validateEmail");
        String email = "address";
        boolean expResult = false;
        boolean result = StringUtil.validateEmail(email);
        assertEquals(expResult, result);
    }    
    
    @Test
    public void testGetStackTrace() {
        System.out.println("getStackTrace");
        Exception e = new Exception();
        String expResult = "java.lang.Exception";
        String result = StringUtil.getStackTrace(e).substring(0, expResult.length());
        assertEquals(expResult, result);
    }
}
