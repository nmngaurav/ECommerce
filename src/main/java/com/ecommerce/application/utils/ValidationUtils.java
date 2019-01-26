package com.ecommerce.application.utils;

import java.util.regex.Pattern;

public class ValidationUtils {   
	/**
     * Constructor.
     */
    private ValidationUtils() {    }   

    public static boolean isValidName(String name) {
        return Pattern.compile("^[a-zA-Z][a-zA-Z0-9 ]+$")
                .matcher(name).find();
    }
    
    public static boolean isValidEmail(String email) {
        return Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
                .matcher(email).find();
    }
    
    public static boolean isNullValue(String value) {
        return value.isEmpty();
    } 
    
/*    public static void main(String[] args) {
    	System.out.println(isValidUsername(".sahdfiu"));
    	System.out.println(isValidEmail("singhgm_ail@qwe.comas"));
    }*/

}
