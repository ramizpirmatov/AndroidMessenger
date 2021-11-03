package com.example.messenger.handler;

import com.example.messenger.model.Conversation;

public class ConversationEvent
{
    private Conversation conversation;

    public ConversationEvent(Conversation conversation)
    {
        this.conversation = conversation;
    }

    public Conversation getConversation()
    {
        return conversation;
    }

    public void setConversation(Conversation conversation)
    {
        this.conversation = conversation;
    }
}
