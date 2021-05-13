package com.callusage.domain;

public class Message<T> {
    private MessageHeader messageHeader;
    private MessageData<T> messageData;

    public Message(MessageHeader messageHeader, MessageData<T> messageData) {
        this.messageHeader = messageHeader;
        this.messageData = messageData;
    }

    public MessageData<T> getMessageData() {
        return messageData;
    }

    public MessageHeader getMessageHeader() {
        return messageHeader;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageHeader=" + messageHeader +
                ", messageData=" + messageData +
                '}';
    }
}
