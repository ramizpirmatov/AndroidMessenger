package com.example.messenger;

import android.app.Application;
import android.util.Log;

public class InitObjectBox extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TAG", "onCreate: init cagirildi");
        ObjectBox.init(this);
    }
}
