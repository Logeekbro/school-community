package com.db.dbcommunity.message.document;


import com.db.dbcommunity.message.enums.MessageType;

public abstract class UserMessage extends Message {

    private MessageType messageType = MessageType.USER;

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
}
