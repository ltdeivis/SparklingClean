package com.example.sparklingclean.Providers;

import com.example.sparklingclean.Firebase.DB.UserHandler;

public class UserProvider {

    private static UserProvider instance = null;
    private UserHandler currentUser;

    private UserProvider() {

    }

    public void setCurrentUser(UserHandler user) {
        this.currentUser = user;
    }

    public UserHandler getCurrentUser() {
        return currentUser;
    }

    public static UserProvider getInstance() {
        if(instance == null) {
            return new UserProvider();
        }

        return instance;
    }
}
