package com.example.sparklingclean.Firebase.DB;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ClientHandler {

    private Client client;
    private String cid;
    private String refPath = "clients/";

    public ClientHandler(){

    }

    public Client getClient() {
        return client;
    }

    public void findClient(String uid) {

        DB_Interface db_interface = new DB_Interface();
        db_interface.getReference("clients").orderByChild("user_id").equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    String test = data.child("client_id").getValue().toString();
                    Log.d("FirebaseTest", test);
                    Client newClient = new Client(data.child("user_id").getValue().toString());
                    setClient(newClient);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addClient(String userId) {
        Client newClient = new Client(userId);
        addClient(newClient);
    }

    public void addClient(Client newClient) {
        DB_Interface db_interface = new DB_Interface();
        cid = db_interface.push(refPath);
        db_interface.insert(refPath + cid, newClient);

        setClient(newClient);
    }

    public void setClient(Client newClient) {
        this.client = newClient;

        DB_Interface db_interface = new DB_Interface();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                client = dataSnapshot.getValue(Client.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        db_interface.getReference(refPath + String.valueOf(cid)).addValueEventListener(listener);
    }

}
