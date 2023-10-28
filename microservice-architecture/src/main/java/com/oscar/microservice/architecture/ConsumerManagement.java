package com.oscar.microservice.architecture;

public class ConsumerManagement {
    private final Consumer consumer = new Consumer();

    public String createConsumer() {
        return consumer.create();
    }

    public String findConsumer(Long consumerId) {
        return consumer.find(consumerId);
    }

    public String modifyConsumer(Long consumerId, String details) {
        return consumer.modify(consumerId, details);
    }

    public String deleteConsumer(Long consumerId) {
        return consumer.delete(consumerId);
    }
}
