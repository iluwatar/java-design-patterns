package com.iluwatar.eda.advanced;


public interface Message {
    public Class<? extends Message> getType();
}
