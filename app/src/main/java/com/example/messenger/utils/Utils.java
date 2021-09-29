package com.example.messenger.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Utils
{


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
}
