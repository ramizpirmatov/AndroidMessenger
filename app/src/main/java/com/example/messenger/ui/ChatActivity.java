package com.example.messenger.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.messenger.utils.Utils;
import com.example.messenger.model.Message;
import com.example.messenger.db.DataBaseController;
import com.example.messenger.R;
import com.example.messenger.adapters.RecyclerViewChatMessagesAdapter;
import com.example.messenger.model.User;

import java.util.List;
import java.util.Random;

public class ChatActivity extends AppCompatActivity
{

    private EditText textMessage;
    private ImageButton sendButton;
    private RecyclerView recyclerView;
    private RecyclerViewChatMessagesAdapter adapter;
    private ImageView toolBarImageView;
    private TextView toolBarTextView;
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

        Bitmap bitmap = Utils.loadImageFromStorage(user.getPath());
        toolBarImageView.setImageBitmap(bitmap);
        toolBarTextView.setText(user.getName());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Message> messages = DataBaseController.getMessagesByUser(user);
        user.setRead(true);
        DataBaseController.getUserBox().put(user);

        countOfMyMessages = Utils.getNumberOfMyLastMessages(messages);

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
                    DataBaseController.setMyMessageForUser(user, textMessage.getText().toString());
                    countOfMyMessages++;

                    if (countOfMyMessages % 4 == 0)
                    {
                        Random random = new Random();
                        DataBaseController.setMessageForUser(user, "random text " + random.nextInt(100));
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
        ChatActivity.user = user;
    }

    public void setRecyclerView()
    {
        adapter.setMessageList(DataBaseController.getMessagesByUser(user));
        adapter.notifyDataSetChanged();
    }
}