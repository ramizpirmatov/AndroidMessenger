package com.example.messenger.model;

import com.example.messenger.model.Message;

import java.util.List;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class User
{

    @Id
    private long id;
    private String name;
    private String path;
    @Backlink
    private ToMany<Message> messages;
    private boolean isRead = false;

    public User()
    {
    }

    public User(String name, String path)
    {
        this.name = name;
        this.path = path;
    }

    public String getNumberOfUnReadMessagesString()
    {
        int count = 0;

        for (int i = messages.size() - 1; i >= 0; i--)
        {
            if (!messages.get(i).isUser()) break;
            count++;
        }

        return String.valueOf(count);
    }

    public ToMany<Message> getMessages()
    {
        return messages;
    }

    public Message getLastMessage()
    {
        return messages.get(messages.size() - 1);
    }

    public boolean isRead()
    {
        return isRead;
    }

    public void setRead(boolean read)
    {
        isRead = read;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPath()
    {
        return path;
    }
}
