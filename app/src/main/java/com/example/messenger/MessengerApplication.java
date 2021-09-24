package com.example.messenger;

import android.app.Application;
import android.util.Log;

import com.example.messenger.db.DataBaseController;

public class MessengerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TAG", "onCreate: init cagirildi");
        DataBaseController.init(this);
    }
}
