package com.iluwatar.bulkhead;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents a task to be executed by a bulkhead service.
 *
 * <p>
 * Tasks are characterized by their name, type, and expected duration. This
 * information is useful for logging, monitoring, and demonstrating the bulkhead
 * pattern's behavior under different loads.
 */
@Getter
@RequiredArgsConstructor
public class Task {

    private final String name;
    private final TaskType type;
    private final long durationMs;

    @Override
    public String toString() {
        return "Task{name='" + name + "', type=" + type + ", duration=" + durationMs + "ms}";
    }
}
