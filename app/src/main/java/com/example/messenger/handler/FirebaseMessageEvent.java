package com.example.messenger.handler;

import com.example.messenger.model.Message;

public class FirebaseMessageEvent
{
    Message message;

    public FirebaseMessageEvent(Message message)
    {
        this.message = message;
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
