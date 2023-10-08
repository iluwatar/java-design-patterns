package com.iluwatar.layers.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = LayersApp.class)
class LayersAppTests {

    private final ApplicationContext applicationContext;

    @Autowired
    LayersAppTests(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    @Test
    void contextLoads() {
        assertNotNull(applicationContext);
    }

}
