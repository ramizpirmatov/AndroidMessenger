package com.example.messenger.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.messenger.model.Message;
import com.example.messenger.model.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class Utils
{
    public static String getNumberOfUnReadMessagesString(User user)
    {
        int count = 0;
        List<Message> messages = user.getMessages();

        for (int i = messages.size() - 1; i > 0; i--)
        {
            if (!messages.get(i).isUser()) break;
            count++;
        }

        return String.valueOf(count);
    }

    public static String getImageName(String path)
    {
        String[] pathPieces = path.split("[/_]");

        return pathPieces[pathPieces.length - 1];
    }

    public static Bitmap loadImageFromStorage(String path)
    {
        Bitmap bitmap = null;
        String name = getImageName(path);

        try
        {
            File f = new File(path, name);
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static int getNumberOfMyLastMessages(List<Message> messages)
    {
        int countOfMyMessages = 0;

        for (int i = messages.size() - 1; i >= 0; i--)
        {
            if (messages.get(i).isUser()) break;
            countOfMyMessages++;
        }

        return countOfMyMessages;
    }
}
