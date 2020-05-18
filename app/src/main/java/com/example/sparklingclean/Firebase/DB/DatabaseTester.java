package com.example.sparklingclean.Firebase.DB;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseTester {

    public DatabaseTester() {

    }

    public void testWriteToDB(String reference, String content) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(reference);

        myRef.setValue(content);
    }


}
