package com.example.sparklingclean.Login;

import com.example.sparklingclean.DB.Client;
import com.example.sparklingclean.DB.User;

public class Authentication {

    private String username = "";
    private String password = "";

    private User currentUser = null;

    public Authentication(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean loginUser() {
        User user = User.findUser(username, password);
        if(user != null) {
            currentUser = user;
            return true;
        }
        else {
            return false;
        }
    }

    public boolean registerUser(String firstName, String lastName, String address, String email, String telnum, String dob) {
        User user = User.registerUser(username, password);
        if(user != null) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setAddress(address);
            user.setEmail(email);
            user.setTelNum(telnum);
            user.setDoB(dob);
            user.setType("client");
            user.setUserObject(new Client());

            currentUser =  user;
            return true;
        }
        else {
            return false;
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
