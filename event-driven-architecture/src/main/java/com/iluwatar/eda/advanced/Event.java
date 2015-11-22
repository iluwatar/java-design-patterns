package com.iluwatar.eda.advanced;

public class Event implements Message {
    public Class<? extends Message> getType() {
        return getClass();
    }
}