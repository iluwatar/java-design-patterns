package com.iluwatar.eda;

import com.iluwatar.eda.event.Event;
import com.iluwatar.eda.event.UserCreatedEvent;
import com.iluwatar.eda.event.UserUpdatedEvent;
import com.iluwatar.eda.handler.UserCreatedEventHandler;
import com.iluwatar.eda.handler.UserUpdatedEventHandler;

/**
 * An event-driven architecture (EDA) is a framework that orchestrates behavior around the production,
 * detection and consumption of events as well as the responses they evoke.
 * An event is any identifiable occurrence that has significance for system hardware or software.
 * <p/>
 * The example below we uses an {@link EventDispatcher} to link/register  {@link Event} objects to
 * their respective handlers Once an {@link Event} is dispatched,
 * it's respective handler is invoked and the {@link Event} is handled accordingly
 */
public class App {

    public static void main(String[] args) {
        EventDispatcher dispatcher = new EventDispatcher();
        dispatcher.registerChannel(UserCreatedEvent.class, new UserCreatedEventHandler());
        dispatcher.registerChannel(UserUpdatedEvent.class, new UserUpdatedEventHandler());
        dispatcher.dispatch(new UserCreatedEvent());
        dispatcher.dispatch(new UserUpdatedEvent());
    }

}
