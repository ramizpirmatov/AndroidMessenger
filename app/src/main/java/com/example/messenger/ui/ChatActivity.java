package com.example.messenger.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.messenger.MessengerApplication;
import com.example.messenger.R;
import com.example.messenger.adapters.MessagesAdapter;
import com.example.messenger.db.DataBaseController;
import com.example.messenger.handler.MessageEvent;
import com.example.messenger.model.Conversation;
import com.example.messenger.model.Message;
import com.example.messenger.model.User;
import com.example.messenger.utils.MessageManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity
{

    private EditText textMessage;
    private ImageButton sendButton;
    private RecyclerView recyclerView;
    private MessagesAdapter adapter;
    private ImageView toolBarImageView;
    private TextView toolBarTextView;
    private Toolbar toolbar;
    private Conversation conversation;
    private List<Message> messageList;
    private User myself;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        if (!EventBus.getDefault().isRegistered(MessageManager.getInstance())) EventBus.getDefault().register(MessageManager.getInstance());
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

        sendButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String messageTxt = textMessage.getText().toString();
                if (!messageTxt.isEmpty())
                {
                    Date date = new Date();

                    Message message = new Message(messageTxt, date.getTime(), false, myself.getId(), conversation.getId(), conversation);
                    EventBus.getDefault().post(new MessageEvent(message));
                    insertRecyclerView(null);
                    textMessage.getText().clear();
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onResume()
    {
        long conversationId = getIntent().getLongExtra(MessengerApplication.CONVERSATION_ID_NAME, 2);

        conversation = DataBaseController.getConversationById(conversationId);
        conversation.setRead(true);

        myself = MessengerApplication.getUser();

        Glide.with(getApplicationContext())
                .load(conversation.getProfileImagePath())
                .into(toolBarImageView);

        toolBarTextView.setText(conversation.getName());

        conversation.setRead(true);
        DataBaseController.attachConversation(conversation);

        messageList = conversation.getMessages();
        if (messageList.isEmpty())
        {
            EventBus.getDefault().post(conversation);
        }
        else
        {
            setTextMessages(messageList);
        }




        super.onResume();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setTextMessages(List<Message> messages)
    {
        messageList = messages;
        adapter = new MessagesAdapter(messageList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }

    @Override
    protected void onDestroy()
    {
        EventBus.getDefault().unregister(MessageManager.getInstance());
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void insertRecyclerView(Message message)
    {
        if (message != null)
        {
            messageList.add(message);
        }

        adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }
}