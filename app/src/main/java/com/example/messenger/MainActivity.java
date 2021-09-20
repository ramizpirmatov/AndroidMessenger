package com.example.messenger;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;

import java.util.List;

import io.objectbox.Box;

public class MainActivity extends AppCompatActivity
{

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*
        ObjectBox.userBox.put(new User("Jhonny", R.drawable.person1));
        ObjectBox.userBox.put(new User("Jessica", R.drawable.person2));
        ObjectBox.userBox.put(new User("Josh", R.drawable.person3));

        ObjectBox.setMessageForUser(ObjectBox.userBox.get(1),"The Lord will help you and see you through. Don't stress.");
        ObjectBox.setMessageForUser(ObjectBox.userBox.get(2),"How are you sis?");
        ObjectBox.setMessageForUser(ObjectBox.userBox.get(3),"I'm trying to measure how social media influences relationship");
*/

        List<User> userList = ObjectBox.userBox.getAll();
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(userList);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_chats);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}