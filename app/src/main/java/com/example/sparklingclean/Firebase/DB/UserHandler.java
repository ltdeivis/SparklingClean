package com.example.sparklingclean.Firebase.DB;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserHandler {


    private User user;
    private String uuid;
    private String refPath = "users/";

    private List<FirebaseListener> dataLoadListeners = new ArrayList<>();
    //TODO : Finish calling listeners on other methods

    public UserHandler() {

    }

    public User getUser() {
        return user;
    }

    public void findUser(String username) {

        DB_Interface db_interface = new DB_Interface();
        db_interface.getReference("users").orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    String test = data.child("firstName").getValue().toString();
                    Log.d("FirebaseTest", test);
                    User newUser = new User
                            (data.child("firstName").getValue().toString(), data.child("lastName").getValue().toString(), data.child("DoB").getValue().toString(),
                            data.child("address").getValue().toString(), data.child("telephoneNum").getValue().toString(), data.child("username").getValue().toString(),
                            data.child("password").getValue().toString(), data.child("email").getValue().toString(), data.child("type").getValue().toString());
                    setUser(newUser);
                }
                notifyDataLoadListeners();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void findUser(String username, final String password) {

        DB_Interface db_interface = new DB_Interface();
        db_interface.getReference("users").orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {

                    String name = data.child("firstName").getValue().toString();
                    Log.d("Firebase Database", "User found : " + name + ", Password expected " + data.child("password").getValue().toString());

                    if(data.child("password").getValue().toString().equals(password)) {
                        Log.d("Firebase Database", "Woo");
                        User newUser = new User
                                (data.child("firstName").getValue().toString(), data.child("lastName").getValue().toString(), data.child("DoB").getValue().toString(),
                                        data.child("address").getValue().toString(), data.child("telephoneNum").getValue().toString(), data.child("username").getValue().toString(),
                                        data.child("password").getValue().toString(), data.child("email").getValue().toString(), data.child("type").getValue().toString());
                        setUser(newUser);
                        break;
                    }
                }
                notifyDataLoadListeners();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addUser(String firstName, String lastName, String DoB, String address,
                        String telephoneNum, String username, String password, String email, String type) {
        User newUser = new User(firstName, lastName, DoB, address, telephoneNum, username, password, email, type);
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

    public void addDataLoadListener(FirebaseListener listener) {
        dataLoadListeners.add(listener);
    }

    public void removeDataLoadListener(FirebaseListener listener) {
        dataLoadListeners.remove(listener);
    }

    private void notifyDataLoadListeners() {
        for(FirebaseListener listener : dataLoadListeners) {
            listener.onLoadFinish();
        }
    }
}
