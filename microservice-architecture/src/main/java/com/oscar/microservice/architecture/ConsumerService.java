package com.oscar.microservice.architecture;

import org.springframework.beans.factory.annotation.Autowired;

public class ConsumerService {
    @Autowired
    private ConsumerManagement consumerManagement;

    public String createConsumer() {
        return consumerManagement.createConsumer();
    }

    public String findConsumer(Long consumerId) {
        return consumerManagement.findConsumer(consumerId);
    }

    public String modifyConsumer(Long consumerId, String details) {
        return consumerManagement.modifyConsumer(consumerId, details);
    }

    public String deleteConsumer(Long consumerId) {
        return consumerManagement.deleteConsumer(consumerId);
    }
}
