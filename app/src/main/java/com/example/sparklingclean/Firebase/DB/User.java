package com.example.sparklingclean.Firebase.DB;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class User {

    public String firstName;
    public String lastName;
    public String DoB;
    public String address;
    public String telephoneNum;
    public String username;
    public String password;
    public String email;

    public User(){

    }

    public User(String firstName, String lastName, String DoB, String address, String telephoneNum, String username, String password, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.DoB = DoB;
        this.address = address;
        this.telephoneNum = telephoneNum;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Map<String, Object> userMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("first name", firstName);
        result.put("last name", lastName);
        result.put("date of birth", DoB);
        result.put("address", address);
        result.put("telephone number", telephoneNum);
        result.put("username", username);
        result.put("password", password);
        result.put("email", email);

        return result;
    }
}
