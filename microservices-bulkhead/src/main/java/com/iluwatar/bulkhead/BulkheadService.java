package com.iluwatar.bulkhead;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract base class for services implementing the Bulkhead pattern.
 *
 * <p>
 * This class provides resource isolation by maintaining a dedicated thread pool
 * for each service. When the thread pool is exhausted, new tasks are rejected
 * quickly (fail-fast behavior) rather than blocking or consuming resources from
 * other services.
 *
 * <p>
 * Key features:
 *
 * <ul>
 * <li>Dedicated thread pool with configurable size
 * <li>Bounded queue for pending tasks
 * <li>Fail-fast rejection policy when capacity is reached
 * <li>Metrics for monitoring resource usage
 * </ul>
 */
public abstract class BulkheadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BulkheadService.class);

    protected final ThreadPoolExecutor executor;
    protected final String serviceName;

    /**
     * Creates a new BulkheadService with specified thread pool configuration.
     *
     * @param serviceName name of the service for logging
     * @param maxPoolSize maximum number of threads in the pool
     * @param queueCapacity maximum number of tasks that can be queued
     */
    protected BulkheadService(String serviceName, int maxPoolSize, int queueCapacity) {
        this.serviceName = serviceName;
        this.executor
                = new ThreadPoolExecutor(
                        maxPoolSize,
                        maxPoolSize,
                        60L,
                        TimeUnit.SECONDS,
                        new ArrayBlockingQueue<>(queueCapacity),
                        new ThreadPoolExecutor.AbortPolicy());
        LOGGER.info(
                "Created {} with {} threads and queue capacity {}",
                serviceName,
                maxPoolSize,
                queueCapacity);
    }

    /**
     * Submits a task for execution. Returns false if the task is rejected due
     * to resource exhaustion.
     *
     * @param task the task to execute
     * @return true if task was accepted, false if rejected
     */
    public boolean submitTask(Task task) {
        try {
            executor.execute(() -> processTask(task));
            LOGGER.info("[{}] Task '{}' submitted successfully", serviceName, task.getName());
            return true;
        } catch (RejectedExecutionException e) {
            LOGGER.warn(
                    "[{}] Task '{}' REJECTED - bulkhead is full (fail-fast)", serviceName, task.getName());
            handleRejectedTask(task);
            return false;
        }
    }

    /**
     * Processes the given task. Subclasses can override for custom behavior.
     *
     * @param task the task to process
     */
    protected void processTask(Task task) {
        LOGGER.info(
                "[{}] Processing task '{}' (type: {}, duration: {}ms)",
                serviceName,
                task.getName(),
                task.getType(),
                task.getDurationMs());
        try {
            Thread.sleep(task.getDurationMs());
            LOGGER.info("[{}] Task '{}' completed successfully", serviceName, task.getName());
        } catch (InterruptedException e) {
            LOGGER.error("[{}] Task '{}' interrupted", serviceName, task.getName());
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Handles a rejected task. Subclasses can override for custom rejection
     * handling.
     *
     * @param task the rejected task
     */
    protected void handleRejectedTask(Task task) {
        LOGGER.info(
                "[{}] Rejected task '{}' - consider retry or fallback logic",
                serviceName,
                task.getName());
    }

    /**
     * Gets the number of currently active threads.
     *
     * @return number of active threads
     */
    public int getActiveThreads() {
        return executor.getActiveCount();
    }

    /**
     * Gets the current queue size.
     *
     * @return number of tasks in queue
     */
    public int getQueueSize() {
        return executor.getQueue().size();
    }

    /**
     * Shuts down the thread pool gracefully.
     */
    public void shutdown() {
        LOGGER.info("[{}] Shutting down thread pool", serviceName);
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
