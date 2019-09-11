package com.iluwatar.leaderelection;

public interface Message {

    MessageType getType();

    String getContent();

}
