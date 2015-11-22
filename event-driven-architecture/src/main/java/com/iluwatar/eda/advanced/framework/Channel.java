package com.iluwatar.eda.advanced.framework;


import com.iluwatar.eda.advanced.events.Event;

public interface Channel<E extends Event> {
    void dispatch(E message);
}