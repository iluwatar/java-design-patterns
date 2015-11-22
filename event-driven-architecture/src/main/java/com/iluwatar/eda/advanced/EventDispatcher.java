package com.iluwatar.eda.advanced;

import java.util.HashMap;
import java.util.Map;

public class EventDispatcher implements DynamicRouter<Event> {
    private Map<Class<? extends Event>, Handler> handlers;

    public EventDispatcher() {
        handlers = new HashMap<Class<? extends Event>, Handler>();
    }

    public void registerChannel(Class<? extends Event> contentType,
                                Channel<? extends Event> channel) {
        handlers.put(contentType, (Handler)channel);
    }

    public void dispatch(Event content) {
        handlers.get(content.getClass()).dispatch(content);
    }
}