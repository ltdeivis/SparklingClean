package com.example.sparklingclean.Firebase.DB;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class UserHandler {

    private User user;
    private String uuid;
    private String refPath = "users/";

    public UserHandler() {

    }

    public User getUser() {
        return user;
    }

    public void addUser(String firstName, String lastName, String DoB, String address,
                        String telephoneNum, String username, String password, String email) {
        User newUser = new User(firstName, lastName, DoB, address, telephoneNum, username, password, email);
        addUser(newUser);
    }

    public void addUser(User newUser) {
        DB_Interface db_interface = new DB_Interface();
        uuid = db_interface.push(refPath);
        db_interface.insert(refPath + uuid, newUser);

        setUser(newUser);
    }

    public void setUser(User newUser) {
        this.user = newUser;

        DB_Interface db_interface = new DB_Interface();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        db_interface.getReference(refPath + String.valueOf(uuid)).addValueEventListener(listener);
    }
}
