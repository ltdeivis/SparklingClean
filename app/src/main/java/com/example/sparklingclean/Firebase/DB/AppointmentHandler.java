package com.example.sparklingclean.Firebase.DB;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AppointmentHandler {

    private List<Appointment> appointments = new ArrayList<>();
    private List<FirebaseListener> dataLoadListeners = new ArrayList<>();
    private String appId;
    private String refPath = "appointments/";

    public AppointmentHandler(){

    }

    public List<Appointment> getAppointments(){
        return appointments;
    }

    public List<Appointment> getPendingAppointments(){
        List<Appointment> pendingList = new ArrayList<>();
        for(Appointment appointment : appointments) {
            if (appointment.pending.equals("true")) {
                pendingList.add(appointment);
            }
        }

        return pendingList;
    }

    public Appointment getAppointment(String app_date) {
        Appointment app = null;

        for(Appointment appointment : appointments) {
            if(appointment.appDate.equals(app_date)) {
                app = appointment;
            }
        }

        return app;
    }

    public void findClientAppointment(String clientID) {
        DB_Interface db_interface = new DB_Interface();
        db_interface.getReference("appointments").orderByChild("clientId").equalTo(clientID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    String test = data.child("clientId").getValue().toString();
                    Log.d("FirebaseTest", test);
                    Appointment newApp = new Appointment(
                            data.child("appTime").getValue().toString(), data.child("appDate").getValue().toString(),
                            data.child("clientId").getValue().toString(), data.child("notes").getValue().toString(), data.child("pending").getValue().toString());
                    setAppointment(newApp);
                }

                notifyDataLoadListeners();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
                            data.child("client_id").getValue().toString(), data.child("notes").getValue().toString(), data.child("pending").getValue().toString());
                    setAppointment(newApp);
                }

                notifyDataLoadListeners();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addAppointment(String appTime, String appDate, String clientId, String notes, String pending) {
        Appointment newApp = new Appointment(appTime, appDate, clientId, notes, pending);
        addAppointment(newApp);
    }

    public void
    addAppointment(Appointment newApp) {
        DB_Interface db_interface = new DB_Interface();
        appId = db_interface.push(refPath);
        db_interface.insert(refPath + appId, newApp);

        setAppointment(newApp);
    }

    public void setAppointment(Appointment newApp) {
        this.appointments.add(newApp);

        DB_Interface db_interface = new DB_Interface();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //TODO : Need unique value here to make it work, find and update
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        //db_interface.getReference(refPath + String.valueOf(appId)).addValueEventListener(listener);
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

