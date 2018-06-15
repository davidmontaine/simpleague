package com.simpleague.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static String defaultValue(String value) {
        if (value == null) {
            return "";
        }
        return value;
    }
    
    public static String defaultValue(String value, String defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return value;
    }    
    
    public static String encodeBase64(String text) throws RuntimeException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(md.digest());
        } catch(NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String genPassword() {
        Random gen = new Random();
        int rand = 0;        
        String password = "";
        
        for (int i = 0; i < 7; i++) {
            rand = gen.nextInt(26);
            char ch = (char)(97 + rand);
            password += (ch + "");
        }
        rand = gen.nextInt(9);
        password += rand;
        return password;
    }    

    public static String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
    
    public static boolean validateEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Matcher matcher = Pattern.compile(EMAIL_PATTERN).matcher(email);
        return matcher.matches();
    }        
}
