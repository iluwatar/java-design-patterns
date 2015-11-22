package com.iluwatar.eda.simple;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Event-driven architecture (EDA) is a software architecture pattern promoting
 * the production, detection, consumption of, and reaction to events.
 * <p/>
 * This main class publishes events to queue. Each event on the queue is consumed
 * and handled depending on the type defined in the {@link Event}
 */
public class App {

    public static void main(String args[]) {

        //create a list of events having different types
        //add these events to a simple queue
        Queue<Event> events = new LinkedList<Event>();
        events.add(new Event('A', "Hello"));
        events.add(new Event('B', "event-driven"));
        events.add(new Event('A', "world!"));

        Event e;

        //the event loop will go through the list of events
        //and handle each one depending on it's type
        while (!events.isEmpty()) {
            e = events.remove();

            //handle events depending on their type
            if (e.type == 'A')
                EventHandler.handleEventA(e);
            if (e.type == 'B')
                EventHandler.handleEventB(e);
        }
    }
}
