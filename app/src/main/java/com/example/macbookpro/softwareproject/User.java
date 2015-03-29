package com.example.macbookpro.softwareproject;

/**
 * Created by pc on 26.03.2015.
 */
public class User {
    public static final String USER_NAME = "username";
    public static final String USER_PASSWORD = "userpassword";
    String username;
    String userpassword;

    public User(){

    }
    public User(String username,String userpassword){
        this.username = username;
        this.userpassword = userpassword;
    }
}
