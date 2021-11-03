package com.example.messenger.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.example.messenger.MessengerApplication;
import com.example.messenger.R;
import com.example.messenger.model.Conversation;
import com.example.messenger.model.Message;
import com.example.messenger.ui.ChatActivity;

import java.text.SimpleDateFormat;
import java.util.List;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.MyViewHolder>
{

    private List<Conversation> conversations;
    private boolean isSelectEnabled;
    private int countOfCheckedChats = 0;
    private Context context;

    public ConversationAdapter(List<Conversation> conversations, Context context)
    {
        this.conversations = conversations;
        this.context = context;
    }

    public boolean isSelectEnabled()
    {
        return isSelectEnabled;
    }

    public void setSelectEnabled(boolean selectEnabled)
    {
        isSelectEnabled = selectEnabled;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_chats, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        if (isSelectEnabled) holder.checkBox.setVisibility(View.VISIBLE);
        else holder.checkBox.setVisibility(View.GONE);

        Conversation conversation = conversations.get(holder.getAdapterPosition());

        Glide.with(context)
                .load(conversation.getProfileImagePath())
                .apply(new RequestOptions().signature(new ObjectKey(String.valueOf(System.currentTimeMillis()))))
                .into(holder.imageView);

        if (conversation.getLastMessage() != null)
        {
            Message conversationLastMessage = conversation.getLastMessage();
            holder.message.setText(conversationLastMessage.getMessage());

            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            holder.time.setText(formatter.format(conversationLastMessage.getTime()));
        }
        holder.name.setText(conversation.getName());

        if (!conversation.isRead() && conversation.getNumberOfUnReadMessages() > 0)
        {
            holder.numberOfMessage.setVisibility(View.VISIBLE);
            holder.numberOfMessage.setText(conversation.getNumberOfUnReadMessages());
        }
        else holder.numberOfMessage.setVisibility(View.GONE);


        holder.checkBox.setChecked(conversation.isSelected());

        holder.checkBox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (conversation.isSelected())
                {
                    conversation.setSelected(false);
                    countOfCheckedChats--;
                }
                else
                {
                    conversation.setSelected(true);
                    countOfCheckedChats++;
                }

                notifyItemChanged(holder.getAdapterPosition());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!isSelectEnabled)
                {
                    Intent intent = new Intent(view.getContext(), ChatActivity.class);
                    intent.putExtra(MessengerApplication.CONVERSATION_ID_NAME, conversation.getId());
                    view.getContext().startActivity(intent);
                }
                else if (conversation.isSelected())
                {
                    conversation.setSelected(false);
                    countOfCheckedChats--;
                    notifyItemChanged(holder.getAdapterPosition());
                }
                else
                {
                    conversation.setSelected(true);
                    countOfCheckedChats++;
                    notifyItemChanged(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return conversations.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView name;
        TextView message;
        TextView time;
        TextView numberOfMessage;
        CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            imageView = itemView.findViewById(R.id.item_image_view);
            name = itemView.findViewById(R.id.item_name_text_view);
            message = itemView.findViewById(R.id.item_message_text_view);
            time = itemView.findViewById(R.id.item_time_text_view);
            numberOfMessage = itemView.findViewById(R.id.item_number_message_text_view);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }

    public int getCountOfCheckedChats()
    {
        return countOfCheckedChats;
    }

    public void setCountOfCheckedChats(int countOfCheckedChats)
    {
        this.countOfCheckedChats = countOfCheckedChats;
    }
}
