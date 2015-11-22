package com.iluwatar.eda.simple;

/**
 * The Event class defines the type of event and data related to the event.
 */
public class Event {

    public char type;
    public String data;

    public Event(char type, String data){
        this.type = type;
        this.data = data;
    }
}
