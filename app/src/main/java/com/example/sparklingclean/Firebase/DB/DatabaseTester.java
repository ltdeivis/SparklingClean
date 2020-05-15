package com.example.sparklingclean.Firebase.DB;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseTester {

    public DatabaseTester() {
        testWriteToDB();
    }

    private void testWriteToDB() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Yare Yare Daze");
    }
}
