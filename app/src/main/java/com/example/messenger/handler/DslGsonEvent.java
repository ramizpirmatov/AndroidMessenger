package com.example.messenger.handler;

public class DslGsonEvent
{
    private String gson;

    public DslGsonEvent(String gson)
    {
        this.gson = gson;
    }

    public String getGson()
    {
        return gson;
    }

    public void setGson(String gson)
    {
        this.gson = gson;
    }
}
