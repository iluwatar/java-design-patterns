package com.iluwatar.eda.advanced;

import com.iluwatar.eda.advanced.events.Event;
import com.iluwatar.eda.advanced.framework.Channel;
import com.iluwatar.eda.advanced.framework.DynamicRouter;

import java.util.HashMap;
import java.util.Map;

public class EventDispatcher implements DynamicRouter<Event> {

    private Map<Class<? extends Event>, Channel> handlers;

    public EventDispatcher() {
        handlers = new HashMap<Class<? extends Event>, Channel>();
    }

    public void registerChannel(Class<? extends Event> contentType,
                                Channel channel) {
        handlers.put(contentType, channel);
    }

    public void dispatch(Event content) {
        handlers.get(content.getClass()).dispatch(content);
    }
}