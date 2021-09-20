
package com.example.messenger;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;


public class ObjectBox
{

    private static BoxStore boxStore;
    public static Box<User> userBox;

    public static void init(Context context)
    {
        if (boxStore == null){
            boxStore = MyObjectBox.builder()
                    .androidContext(context.getApplicationContext())
                    .build();

            if (BuildConfig.DEBUG)
            {
                boolean started = new AndroidObjectBrowser(boxStore).start(context.getApplicationContext());
                Log.i("ObjectBrowser", "Started: " + started);
            }

            userBox = boxStore.boxFor(User.class);
        }

    }

    public static BoxStore getBoxStore()
    {
        return boxStore;
    }

    public static void setBoxStore(BoxStore boxStore)
    {
        ObjectBox.boxStore = boxStore;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void setMessageForUser(User user, String msg)
    {
        Message message = new Message(msg, LocalTime.now(), true);

        user.messages.add(message);

        userBox.put(user);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void setMyMessageForUser(User user, String msg)
    {
        Message message = new Message(msg, LocalTime.now(), false);

        user.messages.add(message);

        userBox.put(user);
    }

    public static List<Message> getMessagesByUser(User user)
    {
        return user.messages;
    }

    public static User getUserById(long id)
    {
        return boxStore.boxFor(User.class).query().equal(User_.id, id).build().find().get(0);
    }

    public static Message getLastMessageByUser(User user)
    {
           return user.messages.get(user.messages.size() - 1);
    }
}

