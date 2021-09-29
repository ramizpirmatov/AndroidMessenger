package com.example.messenger.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messenger.R;
import com.example.messenger.adapters.RecyclerViewChatMessagesAdapter;
import com.example.messenger.db.DataBaseController;
import com.example.messenger.handler.MessageEvent;
import com.example.messenger.model.Message;
import com.example.messenger.model.User;
import com.example.messenger.utils.MessageManager;
import com.example.messenger.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.time.LocalTime;
import java.util.List;

public class ChatActivity extends AppCompatActivity
{

    private EditText textMessage;
    private ImageButton sendButton;
    private RecyclerView recyclerView;
    private RecyclerViewChatMessagesAdapter adapter;
    private ImageView toolBarImageView;
    private TextView toolBarTextView;
    private Toolbar toolbar;
    private static User user;
    private MessageManager manager;
    private List<Message> messageList;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        manager = new MessageManager();
        manager.init();
        EventBus.getDefault().register(manager);
        EventBus.getDefault().register(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        textMessage = findViewById(R.id.edit_text_chat_message);
        sendButton = findViewById(R.id.button_send_message);
        recyclerView = findViewById(R.id.recycler_view_chat);
        toolBarImageView = findViewById(R.id.toolbar_contact_image);
        toolBarTextView = findViewById(R.id.toolbar_contact_name);
        messageList = user.getMessages();

        Bitmap bitmap = Utils.loadImageFromStorage(user.getPath());
        toolBarImageView.setImageBitmap(bitmap);
        toolBarTextView.setText(user.getName());


        user.setRead(true);
        DataBaseController.getUserBox().put(user);

        adapter = new RecyclerViewChatMessagesAdapter(messageList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.scrollToPosition(adapter.getItemCount() - 1);

        sendButton.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view)
            {
                String messageTxt = textMessage.getText().toString();
                if (!messageTxt.isEmpty())
                {
                    Message message = new Message(messageTxt, LocalTime.now(), false);
                    EventBus.getDefault().post(new MessageEvent(user, message));
                    insertRecyclerView(null);
                    textMessage.getText().clear();
                }
            }
        });
    }

    @Override
    protected void onDestroy()
    {
        EventBus.getDefault().unregister(manager);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(myIntent);
        return true;
    }

    public static void setUser(User user)
    {
        ChatActivity.user = user;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void insertRecyclerView(Message message)
    {
            if (message != null){
                messageList.add(message);
            }
        adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }

}