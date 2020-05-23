package com.example.sparklingclean.Login;

import com.example.sparklingclean.Firebase.DB.User;
import com.example.sparklingclean.Firebase.DB.UserHandler;

public class Authentication {

    private String username = "";
    private String password = "";

    private UserHandler userHandler;

    public Authentication(String username, String password) {
        this.username = username;
        this.password = password;

        userHandler = new UserHandler();
    }

    public boolean loginUser() {
        userHandler.findUser(username, password);
        if(userHandler.getUser() != null) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean registerUser(String firstName, String lastName, String address, String email, String telnum, String dob) {
        userHandler.findUser(email);
        if(userHandler.getUser() == null) {
            userHandler.addUser(firstName, lastName, dob, address, telnum, username, password, email);
        } else {
            return false;
        }
        return true;
    }

    public User getCurrentUser() {
        return userHandler.getUser();
    }
}
