package com.example.sparklingclean.Firebase.DB;

import java.util.HashMap;
import java.util.Map;

public class Employee implements Entity{

    public String pph;
    public String payDate;
    public String hoursWorked;
    public String userId;

    public Employee(){

    }

    public Employee(String pph, String payDate, String hoursWorked, String userId){
        this.pph = pph;
        this.payDate = payDate;
        this.hoursWorked = hoursWorked;
        this.userId = userId;
    }

    @Override
    public Map<String, Object> objectMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Pay_per_hour", pph);
        result.put("Pay_date", payDate);
        result.put("Hours_worked", hoursWorked);
        result.put("user_id", userId);
        return result;
    }
}
