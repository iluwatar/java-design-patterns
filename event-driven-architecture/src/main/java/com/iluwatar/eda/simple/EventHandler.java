package com.iluwatar.eda.simple;

/**
 * The {@link EventHandler} class handles performs actions on {@link Event} objects
 */
public class EventHandler {

    public static void handleEventA(Event e){
        System.out.println(e.data);
    }

    public static void handleEventB(Event e){
        System.out.println(e.data.toUpperCase());
    }
}
