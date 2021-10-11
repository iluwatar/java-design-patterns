package com.iluwatar.domain;

public class Message<T> {

    public MessageHeader getMessageHeader() {
        return messageHeader;
    }

    public void setMessageHeader(MessageHeader messageHeader) {
        this.messageHeader = messageHeader;
    }

    public MessageBody<T> getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(MessageBody<T> messageBody) {
        this.messageBody = messageBody;
    }

    private MessageHeader messageHeader;
  
    private MessageBody<T> messageBody;
  
}