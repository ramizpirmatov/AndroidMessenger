package com.example.messenger.handler;


import com.example.messenger.model.Message;
import com.example.messenger.model.User;

public class MessageEvent
{
    private User user;
    private Message message;

    public MessageEvent(User user, Message message)
    {
        this.user = user;
        this.message = message;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Message getMessage()
    {
        return message;
    }

    public void setMessage(Message message)
    {
        this.message = message;
    }
}
