package com.example.sparklingclean.Firebase.DB;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Appointment implements Entity {

    public String appTime;
    public String appDate;
    public String clientId;
    public String employeeId;
    public String notes;
    public String pending;

    public Appointment(){

    }

    public Appointment(String appTime, String appDate, String clientId, String notes, String pending){
        this.appTime = appTime;
        this.appDate = appDate;
        this.clientId = clientId;
        this.notes = notes;
        this.pending = pending;
    }

    @Exclude
    @Override
    public Map<String, Object> objectMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("app_time", appTime);
        result.put("app_date", appDate);
        result.put("employee_id", employeeId);
        result.put("client_id", clientId);
        result.put("notes", notes);
        result.put("pending", pending);

        return result;
    }
}
