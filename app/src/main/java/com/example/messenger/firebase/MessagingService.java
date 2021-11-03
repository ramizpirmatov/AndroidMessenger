package com.example.messenger.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.messenger.MessengerApplication;
import com.example.messenger.handler.DslGsonEvent;
import com.example.messenger.utils.MessageManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

public class MessagingService extends FirebaseMessagingService
{
    @Override
    public void onNewToken(@NonNull String s)
    {
        super.onNewToken(s);

        MessengerApplication.getRetrofitApi().postToken(s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage)
    {
        super.onMessageReceived(remoteMessage);

        Map<String, String> remoteData = new HashMap<>();
        remoteData = remoteMessage.getData();

        if (!EventBus.getDefault().isRegistered(MessageManager.getInstance()))
        {
            EventBus.getDefault().register(MessageManager.getInstance());
        }

        EventBus.getDefault().postSticky(new DslGsonEvent(remoteData.get("messageObject")));
    }
}
