package com.example.android.sportit.Models;

/**
 * Created by Sanya on 30/05/2017.
 */

public class User {
    private String userName;
    private String userEmail;

    public User(){}

    public User(String userName, String userEmail){
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public String getUserName(){
        return userName;
    }

    public String getUserEmail(){
        return userEmail;
    }

}


