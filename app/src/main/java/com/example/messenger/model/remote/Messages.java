package com.example.messenger.model.remote;

import com.example.messenger.model.Message;

import java.util.List;

public class Messages
{
    private List<Message> messages;

    public List<Message> getMessages()
    {
        return messages;
    }

    public void setMessages(List<Message> messages)
    {
        this.messages = messages;
    }
}
