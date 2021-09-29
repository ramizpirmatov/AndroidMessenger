package com.example.messenger.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import com.example.messenger.db.DataBaseController;
import com.example.messenger.R;
import com.example.messenger.adapters.RecyclerViewAdapter;
import com.example.messenger.model.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*String person1 = saveToInternalStorage(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.person1), "person1.jpg");
        String person2 = saveToInternalStorage(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.person2), "person2.jpg");
        String person3 = saveToInternalStorage(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.person3), "person3.jpg");

        DataBaseController.getUserBox().put(new User("Jhonny", person1));
        DataBaseController.getUserBox().put(new User("Jessica", person2));
        DataBaseController.getUserBox().put(new User("Josh", person3));*/

        setRecyclerView();

    }

    @Override
    protected void onResume()
    {
        setRecyclerView();
        super.onResume();
    }

    private void setRecyclerView()
    {
        List<User> userList = DataBaseController.getUserBox().getAll();
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(userList);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_chats);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private String saveToInternalStorage(Bitmap bitmapImage, String name){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(name, Context.MODE_PRIVATE);
        File myPath=new File(directory, name);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
}