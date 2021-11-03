package com.example.messenger.model;

import java.util.List;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class Conversation
{
    @Id(assignable = true)
    public long id;
    private String name;
    private String profileImagePath = "";
    private boolean isRead = false;
    private boolean isSelected = false;
    public ToMany<Message> messages;
    public ToMany<User> users;


    public Conversation()
    {
    }

    public Conversation(String name)
    {
        this.name = name;
    }

    public Conversation(String name, String profileImagePath)
    {
        this.name = name;
        this.profileImagePath = profileImagePath;
    }

    public List<User> getUsers()
    {
        return users;
    }

    public void setUsers(ToMany<User> users)
    {
        this.users = users;
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

    public String getProfileImagePath()
    {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath)
    {
        this.profileImagePath = profileImagePath;
    }

    public boolean isRead()
    {
        return isRead;
    }

    public void setRead(boolean read)
    {
        isRead = read;
    }

    public boolean isSelected()
    {
        return isSelected;
    }

    public void setSelected(boolean selected)
    {
        isSelected = selected;
    }

    public List<Message> getMessages()
    {
        return messages;
    }

    public void setMessages(ToMany<Message> messages)
    {
        this.messages = messages;
    }

    public Message getLastMessage()
    {
        if (!messages.isEmpty()) return messages.get(messages.size() - 1);
        return null;
    }

    public int getNumberOfUnReadMessages()
    {
        int count = 0;

        for (int i = getMessages().size() - 1; i >= 0; i--)
        {
            if (!getMessages().get(i).isUser()) break;
            count++;
        }

        return count;
    }
}
