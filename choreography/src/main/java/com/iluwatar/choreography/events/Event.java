package com.iluwatar.choreography.events;

import static com.iluwatar.choreography.Util.*;

public class Event {
    private final int sagaId;

    public Event(int sagaId) {
        this.sagaId = sagaId;
    }

    public int getSagaId() {
        return sagaId;
    }

    public String prettyPrintSagaId() {
        switch (getSagaId()) {
            case 0:
                return (ANSI_BG_RED + "Saga 1 " + ANSI_RESET + "| ");
            case 1:
                return (ANSI_BG_GREEN + "Saga 2 " + ANSI_RESET + "| ");
            case 2:
                return (ANSI_BG_BLUE + "Saga 3 " + ANSI_RESET + "| ");
        }
        return "Unknown| ";
    }
}



