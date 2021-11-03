package com.example.messenger.utils;


import android.util.Log;

import com.example.messenger.MessengerApplication;
import com.example.messenger.db.DataBaseController;
import com.example.messenger.handler.ConversationEvent;
import com.example.messenger.handler.DslGsonEvent;
import com.example.messenger.handler.MessageEvent;
import com.example.messenger.handler.NumberValidationEvent;
import com.example.messenger.handler.ValidationResponse;
import com.example.messenger.model.Conversation;
import com.example.messenger.model.Message;
import com.example.messenger.model.User;
import com.example.messenger.model.remote.Conversations;
import com.example.messenger.model.remote.Messages;
import com.example.messenger.model.remote.Users;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Response;

public class MessageManager
{
    private static MessageManager manager;

    private MessageManager()
    {

    }

    public static MessageManager getInstance()
    {
        if (manager == null) manager = new MessageManager();
        return manager;
    }

    @Subscribe
    public void sendMessage(MessageEvent event)
    {
        DataBaseController.putMessage(event.getMessage(), MessengerApplication.getUser(), event.getMessage().getConversation());
        EventBus.getDefault().post(new ConversationEvent(event.getMessage().getConversation()));
        MessengerApplication.getSocket().emit(MessengerApplication.SOCKET_EVENT_NAME, Utils.toDslJson(event.getMessage()));
    }

    public void init()
    {
        MessengerApplication.getSocket().on(MessengerApplication.SOCKET_EVENT_NAME, onNewMessage);
    }

    @Subscribe
    public void setMessage(DslGsonEvent dslGson)
    {
        Message message = Utils.fromDslJson(Message.class, (String) dslGson.getGson());

        message.setUser(true);
        message.setId(0);

        Date date = new Date();
        message.setTime(date.getTime());
        User myself = MessengerApplication.getUser();

        if (myself == null)
        {
            List<User> usersFromDB = DataBaseController.getAllUsers();
            for (User user : usersFromDB)
            {
                if (user.isMe()) {
                    myself = user;
                }
            }
        }


        if (message.getUserId() != myself.getId())
        {
            User user = DataBaseController.getUserById(message.getUserId());
            DataBaseController.putMessage(message, user, DataBaseController.getConversationById(message.getConversationId()));
            EventBus.getDefault().postSticky(new ConversationEvent(DataBaseController.getConversationById(message.getConversationId())));
            EventBus.getDefault().postSticky(message);
        }
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener()
    {
        @Override
        public void call(Object... args)
        {
            setMessage(new DslGsonEvent((String) args[0]));
        }
    };

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void checkNumberValidation(NumberValidationEvent event)
    {

        Call<User> call = MessengerApplication.getRetrofitApi().getMainUser(event.getNumber());

        try
        {
            Response<User> response = call.execute();

            User user = response.body();

            if (!response.isSuccessful() || response.body() == null || user.getName() == null)
            {
                EventBus.getDefault().post(new ValidationResponse(false));
            }
            else
            {
                setUsers();
                if (DataBaseController.getConversations().isEmpty()) setConversations();
                EventBus.getDefault().post(new ValidationResponse(true));

                user.setMe(true);

                MessengerApplication.setUser(user);

                DataBaseController.attachUser(user);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void setUsers()
    {
        Call<Users> usersFromApiCall = MessengerApplication.getRetrofitApi().getUsers();

        try
        {
            Response<Users> response = usersFromApiCall.execute();

            if (response.isSuccessful() || response.body() != null || response.body().getUsers() != null)
            {
                for (User user : response.body().getUsers())
                {
                    DataBaseController.attachUser(user);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void setConversations()
    {
        Call<Conversations> conversationsFromApiCall = MessengerApplication.getRetrofitApi().getConversations();

        try
        {
            Response<Conversations> response = conversationsFromApiCall.execute();

            if (response.isSuccessful() && response.body() != null && response.body().getConversations() != null)
            {
                for (Conversation conversation : response.body().getConversations())
                {
                    DataBaseController.attachConversation(conversation);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void getMessagesFromApiByConversation(Conversation conversation)
    {
        Call<Messages> messagesFromApiCall = MessengerApplication.getRetrofitApi().getMessages(conversation.getId());
        HashMap<Long, User> idUserHashMap = new HashMap<>();
        List<User> users = DataBaseController.getAllUsers();

        for (User user : users) idUserHashMap.put(user.getId(), user);


        try
        {
            Response<Messages> response = messagesFromApiCall.execute();

            if (response.isSuccessful() && response.body() != null && response.body().getMessages() != null)
            {
                for (Message message : response.body().getMessages())
                {
                    message.setId(0);

                    DataBaseController.putMessage(message, idUserHashMap.get(message.getUserId()), conversation);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        EventBus.getDefault().post(new ConversationEvent(conversation));
        EventBus.getDefault().post(conversation.getMessages());
    }
}
