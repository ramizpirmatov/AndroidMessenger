
package com.example.messenger.db;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.messenger.BuildConfig;
import com.example.messenger.model.Message;
import com.example.messenger.model.MyObjectBox;
import com.example.messenger.model.User;
import com.example.messenger.model.User_;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void setMessageForUser(User user, Message message)
    {
        user.getMessages().add(message);
        userBox.put(user);
    }

    public static User getUserById(long id)
    {
        return boxStore.boxFor(User.class).query().equal(User_.id, id).build().find().get(0);
    }
}

