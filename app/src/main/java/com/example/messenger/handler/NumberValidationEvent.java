package com.example.messenger.handler;

public class NumberValidationEvent
{
    private String number;

    public NumberValidationEvent(String number)
    {
        this.number = number;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }
}
