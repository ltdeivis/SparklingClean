package com.example.sparklingclean.Firebase.DB;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class User implements Entity {

    public String firstName;
    public String lastName;
    public String DoB;
    public String address;
    public String telephoneNum;
    public String username;
    public String password;
    public String email;
    public String type;

    public User(){

    }

    public User(String firstName, String lastName, String DoB, String address, String telephoneNum, String username, String password, String email, String type){
        this.firstName = firstName;
        this.lastName = lastName;
        this.DoB = DoB;
        this.address = address;
        this.telephoneNum = telephoneNum;
        this.username = username;
        this.password = password;
        this.email = email;
        this.type = type;
    }


    @Exclude
    @Override
    public Map<String, Object> objectMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("first_name", firstName);
        result.put("last_name", lastName);
        result.put("date_of_birth", DoB);
        result.put("address", address);
        result.put("telephone_number", telephoneNum);
        result.put("username", username);
        result.put("password", password);
        result.put("email", email);
        result.put("type", type);

        return result;
    }
}
