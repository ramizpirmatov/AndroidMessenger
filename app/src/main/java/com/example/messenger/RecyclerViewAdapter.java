package com.example.messenger;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        Message message = ObjectBox.getLastMessageByUser(user);

        loadImageFromStorage(user.getPath(), holder, getImageName(user.getPath()));
        holder.name.setText(user.getName());
        holder.message.setText(message.getMessage());
        holder.time.setText(message.getTime());
        if (!user.isRead()) holder.numberOfMessage.setText(String.valueOf(getNumberOfMessages(user.messages)));
        else holder.numberOfMessage.setVisibility(View.GONE);

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

    private int getNumberOfMessages(List<Message> messages)
    {
        int count = 0;

        for (int i = 0; i < messages.size(); i++)
        {
            if (!messages.get(i).isUser()) break;
            count++;
        }

        return count;
    }

    private String getImageName(String path)
    {
        String[] pathPieces = path.split("[/_]");

        return pathPieces[pathPieces.length - 1];
    }

    private void loadImageFromStorage(String path, MyViewHolder holder, String name)
    {
        Log.d("TAG", "loadImageFromStorage: " + name);
        Log.d("TAG", "loadImageFromStoragePath: " + path);
        try
        {
            File f = new File(path, name);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            holder.imageView.setImageBitmap(b);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
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
