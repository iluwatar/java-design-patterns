package com.iluwatar.bulkhead;

/**
 * Service for handling user-facing requests with dedicated resources.
 *
 * <p>
 * This service represents critical user interactions that require high
 * availability and fast response times. By isolating its resources using the
 * Bulkhead pattern, it remains responsive even when other services (like
 * background processing) are experiencing issues or heavy load.
 *
 * <p>
 * Example use cases:
 *
 * <ul>
 * <li>HTTP API requests from users
 * <li>Real-time user interactions
 * <li>Critical business operations
 * </ul>
 */
public class UserService extends BulkheadService {

    private static final int DEFAULT_QUEUE_CAPACITY = 10;

    /**
     * Creates a UserService with specified thread pool size.
     *
     * @param maxThreads maximum number of threads for handling user requests
     */
    public UserService(int maxThreads) {
        super("UserService", maxThreads, DEFAULT_QUEUE_CAPACITY);
    }

    @Override
    protected void handleRejectedTask(Task task) {
        super.handleRejectedTask(task);
    }
}
