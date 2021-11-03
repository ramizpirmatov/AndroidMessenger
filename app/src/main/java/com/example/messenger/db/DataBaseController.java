
package com.example.messenger.db;

import android.content.Context;

import com.example.messenger.BuildConfig;
import com.example.messenger.model.Conversation;
import com.example.messenger.model.Message;
import com.example.messenger.model.MyObjectBox;
import com.example.messenger.model.User;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;


public class DataBaseController
{

    private static BoxStore boxStore;
    private static Box<Conversation> conversationBox;
    private static Box<User> userBox;

    public static void init(Context context)
    {
        if (boxStore == null || boxStore.isClosed())
        {
            boxStore = MyObjectBox.builder().androidContext(context.getApplicationContext())
                .build();

            if (BuildConfig.DEBUG)
            {
                new AndroidObjectBrowser(boxStore).start(context.getApplicationContext());
            }

            conversationBox = boxStore.boxFor(Conversation.class);
            userBox = boxStore.boxFor(User.class);
        }

    }



    public static User getUserById(long id)
    {
        return userBox.get(id);
    }

    public static Conversation getConversationById(long id)
    {
        return conversationBox.get(id);
    }

    public static List<Conversation> getConversations()
    {
        return conversationBox.getAll();
    }

    public static void attachUser(User user)
    {
        userBox.attach(user);
        userBox.put(user);
    }

    public static void attachConversation(Conversation conversation)
    {
        conversationBox.attach(conversation);
        conversationBox.put(conversation);
    }

    public static void putMessage(Message message, User user, Conversation conversation)
    {
        boxStore.boxFor(Conversation.class).attach(conversation);
        conversation.getMessages().add(message);
        conversationBox.put(conversation);

        user.getMessages().add(message);
        message.setUser(user);
        userBox.put(user);
    }

    public static List<User> getAllUsers()
    {
        return userBox.getAll();
    }
}

