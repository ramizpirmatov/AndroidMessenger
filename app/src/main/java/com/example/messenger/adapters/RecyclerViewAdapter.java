package com.example.messenger.adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messenger.utils.Utils;
import com.example.messenger.model.Message;
import com.example.messenger.R;
import com.example.messenger.model.User;
import com.example.messenger.ui.ChatActivity;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>
{

    private List<User> userList;

    public RecyclerViewAdapter(List<User> userList)
    {
        this.userList = userList;
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
        User user = userList.get(holder.getAdapterPosition());
        Message message = user.getLastMessage();

        Bitmap bitmap = Utils.loadImageFromStorage(user.getPath());
        holder.imageView.setImageBitmap(bitmap);
        holder.name.setText(user.getName());
        holder.message.setText(message.getMessage());
        holder.time.setText(message.getTime());
        if (!user.isRead()) holder.numberOfMessage.setText(Utils.getNumberOfUnReadMessagesString(user));
        else holder.numberOfMessage.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ChatActivity.setUser(user);
                Intent intent = new Intent(view.getContext(), ChatActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        ImageView imageView;
        TextView name;
        TextView message;
        TextView time;
        TextView numberOfMessage;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            imageView = itemView.findViewById(R.id.item_image_view);
            name = itemView.findViewById(R.id.item_name_text_view);
            message = itemView.findViewById(R.id.item_message_text_view);
            time = itemView.findViewById(R.id.item_time_text_view);
            numberOfMessage = itemView.findViewById(R.id.item_number_message_text_view);
        }
    }
}
