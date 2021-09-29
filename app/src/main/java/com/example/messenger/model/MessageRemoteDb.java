package com.example.messenger.model;

public class MessageRemoteDb
{
    private long userId;
    private String message;

    public MessageRemoteDb(long userId, String message)
    {
        this.userId = userId;
        this.message = message;
    }

    public long getUserId()
    {
        return userId;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
