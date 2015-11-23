package com.iluwatar.eda.event;

import com.iluwatar.eda.framework.Message;

public class Event implements Message {
    public Class<? extends Message> getType() {
        return getClass();
    }
}