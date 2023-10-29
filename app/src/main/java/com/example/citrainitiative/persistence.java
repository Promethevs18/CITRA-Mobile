package com.example.citrainitiative;

import com.google.firebase.database.FirebaseDatabase;

public class persistence extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
