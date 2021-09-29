package com.example.messenger;

import android.app.Application;

import com.example.messenger.db.DataBaseController;
import com.google.gson.Gson;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class MessengerApplication extends Application {

    private static Socket mSocket;
    private static final Gson gson = new Gson();

    {
        try {
            if (mSocket == null || !mSocket.connected())
            {
                mSocket = IO.socket("https://tayqatech-messenger-server.herokuapp.com/");
                mSocket.connect();
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static Socket getSocket() {
        return mSocket;
    }

    public static Gson getGson()
    {
        return gson;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DataBaseController.init(this);
    }
}
