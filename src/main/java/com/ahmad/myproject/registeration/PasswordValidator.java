package com.ahmad.myproject.registeration;

public class PasswordValidator {

    public static void validator(int length){
        if(length< 8){
            throw new IllegalStateException("Invalid password, Password should be at least 8 characters");
        }


    }
}
