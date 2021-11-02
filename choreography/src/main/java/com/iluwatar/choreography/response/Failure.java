package com.iluwatar.choreography.response;

import com.iluwatar.choreography.events.DeliveryFailureEvent;
import com.iluwatar.choreography.events.Event;

public class Failure implements Response {
    private final String message;

    public Failure(String message) {
        this.message = message;
    }

    public Failure(DeliveryFailureEvent event) {
        this.message = event.prettyPrintSagaId() + event.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
