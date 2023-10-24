package com.iluwatar.implicitlock;

/**
 * Represents a worker thread that updates the shared resource's state.
 */
public class WorkerThread implements Runnable{
    private SharedResource sharedResource;

    public WorkerThread(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        // Call the synchronized method to update the state
        sharedResource.updateState();
    }
}
