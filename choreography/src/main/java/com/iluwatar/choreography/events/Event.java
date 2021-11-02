package com.iluwatar.choreography.events;

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
                return (ANSI_BG_RED + "Thread 1 " + ANSI_RESET + "| ");
            case 1:
                return (ANSI_BG_GREEN + "Thread 2 " + ANSI_RESET + "| ");
            case 2:
                return (ANSI_BG_BLUE + "Thread 3 " + ANSI_RESET + "| ");
        }
        return "Unknown| ";
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BG_RED = "\u001B[41m";
    public static final String ANSI_BG_GREEN = "\u001B[42m";
    public static final String ANSI_BG_BLUE = "\u001B[44m";
}



