package com.iluwatar.leaderelection.ring;

import com.iluwatar.leaderelection.Message;

public class RingMessage implements Message {

    private RingMessageType type;

    private String content;

    public RingMessage() {}

    public RingMessage(RingMessageType type, String content) {
        this.type = type;
        this.content = content;
    }

    public RingMessageType getType() {
        return type;
    }

    public void setType(RingMessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
