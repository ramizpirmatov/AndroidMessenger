package com.example.messenger.handler;

public class ValidationResponse
{
    private boolean isAvailable = false;

    public ValidationResponse(boolean isAvailable)
    {
        this.isAvailable = isAvailable;
    }

    public boolean isAvailable()
    {
        return isAvailable;
    }

    public void setAvailable(boolean available)
    {
        isAvailable = available;
    }
}
