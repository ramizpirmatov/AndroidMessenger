package com.example.messenger.model;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class User
{
    @Id(assignable = true)
    public long id;
    private String name;
    private String path;
    private boolean isRead = false;
    private boolean isSelected = false;
    private long conversationId;
    private boolean isMe = false;
    private String phoneNumber;
    @Backlink
    public ToMany<Message> messages;
    @Backlink
    public ToMany<Conversation> conversations;

    public User()
    {
    }

    public User(String name)
    {
        this.name = name;
    }

    public User(String name, long id)
    {
        this.name = name;
        this.id = id;
    }

    public User(String name, String path)
    {
        this.name = name;
        this.path = path;
    }

    public ToMany<Message> getMessages()
    {
        return messages;
    }

    public void setMessages(ToMany<Message> messages)
    {
        this.messages = messages;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public boolean isMe()
    {
        return isMe;
    }

    public void setMe(boolean me)
    {
        isMe = me;
    }

    public long getConversationId()
    {
        return conversationId;
    }

    public void setConversationId(int conversationId)
    {
        this.conversationId = conversationId;
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

    public void setPath(String path)
    {
        this.path = path;
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

    @Override
    public String toString()
    {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", isRead=" + isRead +
                ", isSelected=" + isSelected +
                ", conversationId=" + conversationId +
                ", isMe=" + isMe +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", messages=" + messages +
                ", conversations=" + conversations +
                '}';
    }
}
