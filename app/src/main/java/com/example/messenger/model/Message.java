package com.example.messenger.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Message
{
    @Id
    private long id;
    private String message;
    private String time;
    private boolean isUser;
    public long userId;
    public ToOne<User> user;

    public Message()
    {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Message(String message, LocalTime time, boolean isUser)
    {
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;

        this.message = message;
        this.time = time.truncatedTo(ChronoUnit.MINUTES).toString();
        this.isUser = isUser;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getTime()
    {
        return time;
    }

    public boolean isUser()
    {
        return isUser;
    }

    @Override
    public String toString()
    {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", time='" + time + '\'' +
                ", isUser=" + isUser +
                ", userId=" + userId +
                '}';
    }
}
