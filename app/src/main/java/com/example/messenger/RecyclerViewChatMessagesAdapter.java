package com.example.messenger;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewChatMessagesAdapter extends RecyclerView.Adapter<RecyclerViewChatMessagesAdapter.BaseViewHolder>
{

    private List<Message> messageList;

    public void setMessageList(List<Message> messageList)
    {
        this.messageList = messageList;
    }

    public RecyclerViewChatMessagesAdapter(List<Message> messageList)
    {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_chat, parent, false);

        if (viewType == ViewType.VIEW_TYPE_USER_MSG)
        {
            return new UserMessageViewHolder(view);
        }

        return new MyMessageViewHolder(view);
    }

    @Override
    public int getItemViewType(int position)
    {
        return messageList.get(position).isUser() ? ViewType.VIEW_TYPE_USER_MSG : ViewType.VIEW_TYPE_MY_MSG;
    }

    @IntDef({ViewType.VIEW_TYPE_MY_MSG, ViewType.VIEW_TYPE_USER_MSG})
    @interface ViewType{
        int VIEW_TYPE_MY_MSG = 0;
        int VIEW_TYPE_USER_MSG = 1;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position)
    {
        Message message = messageList.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount()
    {
        return messageList.size();
    }

    public class UserMessageViewHolder extends BaseViewHolder
    {

        TextView textViewLeft;

        public UserMessageViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textViewLeft = itemView.findViewById(R.id.chat_text_view_message);
        }

        @Override
        void bind(Message message)
        {
            textViewLeft.setVisibility(View.VISIBLE);
            textViewRight.setVisibility(View.GONE);
            textViewLeft.setText(message.getMessage());
        }
    }

    public class MyMessageViewHolder extends BaseViewHolder
    {

        TextView textViewRight;

        public MyMessageViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textViewRight = itemView.findViewById(R.id.chat_text_view_my_message);
        }

        @Override
        void bind(Message message)
        {
            textViewRight.setVisibility(View.VISIBLE);
            textViewLeft.setVisibility(View.GONE);
            textViewRight.setText(message.getMessage());
        }
    }

    public abstract static class BaseViewHolder extends RecyclerView.ViewHolder
    {

        TextView textViewLeft;
        TextView textViewRight;

        public BaseViewHolder(@NonNull View itemView)
        {
            super(itemView);

            textViewLeft = itemView.findViewById(R.id.chat_text_view_message);
            textViewRight = itemView.findViewById(R.id.chat_text_view_my_message);
        }

        void bind(Message message)
        {

        }
    }
}
