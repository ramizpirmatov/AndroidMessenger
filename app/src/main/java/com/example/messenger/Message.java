package com.example.messenger;

import java.time.LocalDateTime;
import java.time.LocalTime;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Message {
    @Id
    private long id;
    private String message;
    private String time;
    private boolean isUser;
    public ToOne<User> user;

    public Message()
    {
    }

    public Message(String message, LocalTime time, boolean isUser) {
        this.message = message;
        this.time = time.toString();
        this.isUser = isUser;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time.toString();
    }

    public boolean isUser()
    {
        return isUser;
    }

    public void setUser(boolean user)
    {
        isUser = user;
    }
}
