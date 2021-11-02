package com.iluwatar.choreography.events;

public class DeliverySuccessEvent extends Event {
    private final String message;

    public DeliverySuccessEvent(int sagaId, String message) {
        super(sagaId);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

