package com.example.sparklingclean.Firebase.DB;

import android.support.annotation.NonNull;
import android.util.Log;

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

    public void findUser(String email) {

        DB_Interface db_interface = new DB_Interface();
        db_interface.getReference("users").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    String test = data.child("firstName").getValue().toString();
                    Log.d("FirebaseTest", test);
                    User newUser = new User
                            (data.child("firstName").getValue().toString(), data.child("lastName").getValue().toString(), data.child("DoB").getValue().toString(),
                            data.child("address").getValue().toString(), data.child("telephoneNum").getValue().toString(), data.child("username").getValue().toString(),
                            data.child("password").getValue().toString(), data.child("email").getValue().toString());
                    setUser(newUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
