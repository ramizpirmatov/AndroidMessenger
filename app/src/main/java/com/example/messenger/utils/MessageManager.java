package com.example.messenger.utils;


import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messenger.MessengerApplication;
import com.example.messenger.adapters.RecyclerViewChatMessagesAdapter;
import com.example.messenger.db.DataBaseController;
import com.example.messenger.handler.MessageEvent;
import com.example.messenger.model.Message;
import com.example.messenger.model.MessageRemoteDb;
import com.example.messenger.ui.ChatActivity;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.time.LocalTime;

import io.socket.emitter.Emitter;

public class MessageManager
{
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Subscribe
    public void sendMessage(MessageEvent event)
    {
        MessageRemoteDb messageRemote = new MessageRemoteDb(event.getUser().getId(), event.getMessage().getMessage());

        DataBaseController.setMessageForUser(event.getUser(), event.getMessage());

        String json = MessengerApplication.getGson().toJson(messageRemote);

        MessengerApplication.getSocket().emit("message", json);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void init()
    {
        MessengerApplication.getSocket().on("message", onNewMessage);
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener()
    {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void call(Object... args)
        {
            Gson gson = new Gson();
            MessageRemoteDb data = gson.fromJson((String) args[0], MessageRemoteDb.class);

            Message message = new Message(data.getMessage(), LocalTime.now(), true);

            DataBaseController.setMessageForUser(DataBaseController.getUserById(data.getUserId()), message);
            EventBus.getDefault().post(message);
        }
    };
}
