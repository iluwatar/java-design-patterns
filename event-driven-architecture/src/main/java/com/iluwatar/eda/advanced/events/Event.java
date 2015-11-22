package com.iluwatar.eda.advanced.events;

import com.iluwatar.eda.advanced.framework.Message;

public class Event implements Message {
    public Class<? extends Message> getType() {
        return getClass();
    }
}