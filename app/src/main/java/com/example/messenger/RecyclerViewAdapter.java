package com.example.messenger;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        User user = userList.get(holder.getAdapterPosition());
        Message message = ObjectBox.getLastMessageByUser(user);

        holder.imageView.setImageResource(user.getImageResourceId());
        holder.name.setText(user.getName());
        holder.message.setText(message.getMessage());
        holder.time.setText(message.getTime());
        holder.numberOfMessage.setText(String.valueOf(user.messages.size()));

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Chat.setUser(user);
                Intent intent = new Intent(view.getContext(), Chat.class);
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
