package com.indianservers.universitynotifications;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by JNTUH on 10-11-2017.
 */

public class sample extends Application {

    @Override
    public void onCreate() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        super.onCreate();
    }
}
