package com.example.sparklingclean.Firebase.DB;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class EmployeeHandler {

    private Employee employee;
    private String empId;
    private String refPath = "employees/";

    public EmployeeHandler(){

    }

    public Employee getEmp(){
        return employee;
    }

    public void findEmployee(String uid) {

        DB_Interface db_interface = new DB_Interface();
        db_interface.getReference("employees").orderByChild("user_id").equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    String test = data.child("employee_id").getValue().toString();
                    Log.d("FirebaseTest", test);
                    Employee newEmp = new Employee(
                             data.child("pay_per_hour").getValue().toString(),data.child("pay_date").getValue().toString(),
                             data.child("hours_worked").getValue().toString(), data.child("user_id").getValue().toString());
                    setEmp(newEmp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addEmp(String pph, String payDate, String hoursWorked) {
        Employee newEmp = new Employee();
        addEmp(newEmp);
    }

    public void addEmp(Employee newEmp) {
        DB_Interface db_interface = new DB_Interface();
        empId = db_interface.push(refPath);
        db_interface.insert(refPath + empId, newEmp);

        setEmp(newEmp);
    }

    public void setEmp(Employee newEmp) {
        this.employee = newEmp;

        DB_Interface db_interface = new DB_Interface();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                employee = dataSnapshot.getValue(Employee.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        db_interface.getReference(refPath + String.valueOf(empId)).addValueEventListener(listener);
    }
}
