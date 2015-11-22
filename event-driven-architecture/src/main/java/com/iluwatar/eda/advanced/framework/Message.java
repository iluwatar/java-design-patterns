package com.iluwatar.eda.advanced.framework;


public interface Message {
    public Class<? extends Message> getType();
}
