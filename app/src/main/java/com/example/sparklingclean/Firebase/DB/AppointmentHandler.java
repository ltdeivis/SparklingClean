package com.example.sparklingclean.Firebase.DB;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class AppointmentHandler {

    private Appointment appointment;
    private String appId;
    private String refPath = "appointments/";

    public AppointmentHandler(){

    }

    public Appointment getAppointment(){
        return appointment;
    }

    public void findAppointment(String appDate) {

        DB_Interface db_interface = new DB_Interface();
        db_interface.getReference("appointments").orderByChild("app_date").equalTo(appDate).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    String test = data.child("app_date").getValue().toString();
                    Log.d("FirebaseTest", test);
                    Appointment newApp = new Appointment(
                            data.child("app_time").getValue().toString(), data.child("app_date").getValue().toString(),
                            data.child("client_id").getValue().toString(), data.child("notes").getValue().toString());
                    setAppointment(newApp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addAppointment(String appTime, String appDate, String clientId, String notes) {
        Appointment newApp = new Appointment(appTime, appDate, clientId, notes);
        addAppointment(newApp);
    }

    public void addAppointment(Appointment newApp) {
        DB_Interface db_interface = new DB_Interface();
        appId = db_interface.push(refPath);
        db_interface.insert(refPath + appId, newApp);

        setAppointment(newApp);
    }

    public void setAppointment(Appointment newApp) {
        this.appointment = newApp;

        DB_Interface db_interface = new DB_Interface();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointment = dataSnapshot.getValue(Appointment.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        db_interface.getReference(refPath + String.valueOf(appId)).addValueEventListener(listener);
    }
}

