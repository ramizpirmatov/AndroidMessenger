package com.example.messenger;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Chat extends AppCompatActivity
{

    EditText textMessage;
    ImageButton sendButton;
    RecyclerView recyclerView;
    RecyclerViewChatMessagesAdapter adapter;
    ImageView toolBarImageView;
    TextView toolBarTextView;
    private static User user;
    private int countOfMyMessages = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        textMessage = findViewById(R.id.edit_text_chat_message);
        sendButton = findViewById(R.id.button_send_message);
        recyclerView = findViewById(R.id.recycler_view_chat);
        toolBarImageView = findViewById(R.id.toolbar_contact_image);
        toolBarTextView = findViewById(R.id.toolbar_contact_name);

        toolBarImageView.setImageResource(user.getImageResourceId());
        toolBarTextView.setText(user.getName());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Message> messages = ObjectBox.getMessagesByUser(user);
        user.setRead(true);
        ObjectBox.userBox.put(user);


        for (int i = messages.size() - 1; i >= 0; i--)
        {
            if (messages.get(i).isUser()) break;
            countOfMyMessages++;
        }

        adapter = new RecyclerViewChatMessagesAdapter(messages);
        recyclerView.setAdapter(adapter);

        sendButton.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view)
            {
                if (!textMessage.getText().toString().isEmpty())
                {
                    ObjectBox.setMyMessageForUser(user, textMessage.getText().toString());
                    countOfMyMessages++;

                    if (countOfMyMessages % 4 == 0)
                    {
                        Random random = new Random();
                        ObjectBox.setMessageForUser(user, "random text " + random.nextInt(100));
                    }

                    setRecyclerView();
                    recyclerView.scrollToPosition(adapter.getItemCount() - 1);
                    textMessage.getText().clear();
                }
            }
        });
    }

    public static void setUser(User user)
    {
        Chat.user = user;
    }

    public void setRecyclerView()
    {
        adapter.setMessageList(ObjectBox.getMessagesByUser(user));
        adapter.notifyDataSetChanged();
    }
}