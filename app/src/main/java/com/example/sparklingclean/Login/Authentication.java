package com.example.sparklingclean.Login;

import com.example.sparklingclean.Firebase.DB.FirebaseListener;
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

    public void loginUser() {
        userHandler.findUser(username, password);
    }

    public void registerUser(String firstName, String lastName, String address, String email, String telnum, String dob, String type) {
        userHandler.addUser(firstName, lastName, dob, address, telnum, username, password, email, type);
    }

    public User getCurrentUser() {
        return userHandler.getUser();
    }

    public UserHandler getUserHandler() {
        return userHandler;
    }

    public void addDataListener(FirebaseListener listener) {
        userHandler.addDataLoadListener(listener);
    }

    public void removeDataListener(FirebaseListener listener) {
        userHandler.removeDataLoadListener(listener);
    }
}
