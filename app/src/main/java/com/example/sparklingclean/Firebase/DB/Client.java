package com.example.sparklingclean.Firebase.DB;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Client implements Entity {

    public String userId;

    public Client(){

    }

    public Client(String userId){
        this.userId = userId;
    }

    @Exclude
    @Override
    public Map<String, Object> objectMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("user_id", userId);

        return result;
    }
}
