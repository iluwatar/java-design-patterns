package com.iluwatar.microservice.architecture;

import java.util.HashMap;
import java.util.Map;

public class Consumer {
    private final Map<Long, String> consumers = new HashMap<>();
    private long currentId = 0;

    public String create() {
        currentId++;
        consumers.put(currentId, "Sample Consumer Details for ID: " + currentId);
        return "Consumer Created with ID: " + currentId;
    }

    public String find(Long consumerId) {
        return consumers.getOrDefault(consumerId, "Consumer not found");
    }

    public String modify(Long consumerId, String details) {
        if (consumers.containsKey(consumerId)) {
            consumers.put(consumerId, details);
            return "Modified consumer with ID: " + consumerId;
        }
        return "Consumer not found";
    }

    public String delete(Long consumerId) {
        if (consumers.containsKey(consumerId)) {
            consumers.remove(consumerId);
            return "Deleted consumer with ID: " + consumerId;
        }
        return "Consumer not found";
    }
}
