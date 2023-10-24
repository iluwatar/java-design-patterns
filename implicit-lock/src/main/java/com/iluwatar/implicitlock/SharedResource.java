package com.iluwatar.implicitlock;

/**
 * This class represents a shared resource that can be accessed by multiple threads concurrently.
 */
public class SharedResource {
    private State state = State.INITIAL;

    // Method to update the state, synchronized to achieve thread safety
    public synchronized void updateState() {
        if (state == State.INITIAL) {
            state = State.UPDATED;
            System.out.println("State updated by thread: " + Thread.currentThread().getId());
        }
    }

    // Getter method to retrieve the current state
    public synchronized State getState() {
        return state;
    }
}
