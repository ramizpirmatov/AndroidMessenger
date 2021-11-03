package com.example.messenger.model;

import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.JsonAttribute;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@CompiledJson
@Entity
public class Message
{
    @Id
    @JsonAttribute(ignore = true)
    private long id;
    private String message;
    @JsonAttribute(ignore = true)
    private long time;
    @JsonAttribute(ignore = true)
    private boolean isUser;
    private long userId;
    private long conversationId;
    @JsonAttribute(ignore = true)
    public ToOne<User> user;
    @JsonAttribute(ignore = true)
    public ToOne<Conversation> conversation;

    public Message()
    {
    }

    public Message(String message, long time, boolean isUser)
    {
        this.message = message;
        this.time = time;
        this.isUser = isUser;
    }

    public Message(String message, long time, boolean isUser, long userId, long conversationId, Conversation conversation)
    {
        this.message = message;
        this.time = time;
        this.isUser = isUser;
        this.userId = userId;
        this.conversationId = conversationId;
        this.conversation.setTarget(conversation);
    }

    @JsonAttribute(ignore = true)
    public User getUser()
    {
        return user.getTarget();
    }

    @JsonAttribute(ignore = true)
    public void setUser(User user)
    {
        this.user.setTarget(user);
    }

    public long getConversationId()
    {
        return conversationId;
    }

    public void setConversationId(long conversationId)
    {
        this.conversationId = conversationId;
    }

    public void setTime(long time)
    {
        this.time = time;
    }

    public Conversation getConversation()
    {
        return conversation.getTarget();
    }

    public void setConversation(Conversation c)
    {
        conversation.setTarget(c);
    }

    @JsonAttribute(ignore = true)
    public void setUser(boolean user)
    {
        isUser = user;
    }

    public long getUserId()
    {
        return userId;
    }

    public void setUserId(long userId)
    {
        this.userId = userId;
    }

    @JsonAttribute(ignore = true)
    public long getId()
    {
        return id;
    }

    @JsonAttribute(ignore = true)
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

    @JsonAttribute(ignore = true)
    public long getTime()
    {
        return time;
    }

    @JsonAttribute(ignore = true)
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
