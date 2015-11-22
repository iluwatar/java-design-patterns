package com.iluwatar.eda.advanced;

public class Handler implements Channel<Event> {
    public void dispatch(Event message) {
        System.out.println(message.getClass());
    }
}