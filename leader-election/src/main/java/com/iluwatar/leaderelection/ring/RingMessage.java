package com.iluwatar.leaderelection.ring;

import com.iluwatar.leaderelection.Message;
import com.iluwatar.leaderelection.MessageType;

public class RingMessage implements Message {

    private MessageType type;
    private String content;

    public RingMessage() {}

    public RingMessage(MessageType type, String content) {
        this.type = type;
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
