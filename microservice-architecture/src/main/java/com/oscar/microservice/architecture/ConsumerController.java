package com.oscar.microservice.architecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/consumers")
public class ConsumerController {
    @Autowired
    private ConsumeService consumeService;

    @PostMapping("/create")
    public String createConsumer() {
        return consumeService.createConsumer();
    }

    @GetMapping("/get/{consumerId}")
    public String findConsumer(@PathVariable Long consumerId) {
        return consumeService.findConsumer(consumerId);
    }

    @PutMapping("/modify/{consumerId}")
    public String modifyConsumer(@PathVariable Long consumerId, @RequestParam String details) {
        return consumeService.modifyConsumer(consumerId, details);
    }

    @DeleteMapping("/delete/{consumerId}")
    public String deleteConsumer(@PathVariable Long consumerId) {
        return consumeService.deleteConsumer(consumerId);
    }
}
