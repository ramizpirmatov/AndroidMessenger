
package com.example.messenger.db;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.messenger.BuildConfig;
import com.example.messenger.model.Message;
import com.example.messenger.model.MyObjectBox;
import com.example.messenger.model.User;
import com.example.messenger.model.User_;

import java.time.LocalTime;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;


public class DataBaseController
{

    private static BoxStore boxStore;
    private static Box<User> userBox;

    public static Box<User> getUserBox()
    {
        return userBox;
    }

    public static void setUserBox(Box<User> userBox)
    {
        DataBaseController.userBox = userBox;
    }

    public static void init(Context context)
    {
        if (boxStore == null){
            boxStore = MyObjectBox.builder()
                    .androidContext(context.getApplicationContext())
                    .build();

            if (BuildConfig.DEBUG)
            {
                boolean started = new AndroidObjectBrowser(boxStore).start(context.getApplicationContext());
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
        DataBaseController.boxStore = boxStore;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void setMessageForUser(User user, String msg)
    {
        Message message = new Message(msg, LocalTime.now(), true);

        user.getMessages().add(message);

        userBox.put(user);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void setMyMessageForUser(User user, String msg)
    {
        Message message = new Message(msg, LocalTime.now(), false);

        user.getMessages().add(message);

        userBox.put(user);
    }

    public static List<Message> getMessagesByUser(User user)
    {
        return user.getMessages();
    }

    public static User getUserById(long id)
    {
        return boxStore.boxFor(User.class).query().equal(User_.id, id).build().find().get(0);
    }
}

