package com.iluwatar.choreography;

import com.iluwatar.choreography.events.Event;

public class Util {
    public static void performAction(Event e, String s)  {
        System.out.println(e.prettyPrintSagaId() + s);
    }
}
