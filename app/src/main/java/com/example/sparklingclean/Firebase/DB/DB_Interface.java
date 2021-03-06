package com.example.sparklingclean.Firebase.DB;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class DB_Interface {

    public DB_Interface() {

    }

    /**
     * Pushes entity to database.
     * @param path path from root to node in which to insert
     * @return autogenerated key
     */
    public String push(String path) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        return database.child(path).push().getKey();
    }

    public void insert(String path, Object content) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(path);

        reference.setValue(content);
    }

    public void updateChildren(Map<String, Object> children) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.updateChildren(children);
    }

    public DatabaseReference getReference(String path) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        return database.getReference(path);
    }
}
