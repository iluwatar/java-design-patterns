package com.iluwatar.eda.advanced;

import com.iluwatar.eda.advanced.events.Event;
import com.iluwatar.eda.advanced.events.UserCreatedEvent;
import com.iluwatar.eda.advanced.events.UserUpdatedEvent;
import com.iluwatar.eda.advanced.handler.UserCreatedEventHandler;
import com.iluwatar.eda.advanced.handler.UserUpdatedEventHandler;

public class App {

    public static void main(String[] args) {
        EventDispatcher dispatcher = new EventDispatcher();
        dispatcher.registerChannel(UserCreatedEvent.class, new UserCreatedEventHandler());
        dispatcher.registerChannel(UserUpdatedEvent.class, new UserUpdatedEventHandler());
        dispatcher.dispatch(new UserCreatedEvent());
        dispatcher.dispatch(new UserUpdatedEvent());
    }
}
