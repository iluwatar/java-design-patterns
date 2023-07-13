package com.iluwatar.layers.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "dao")
@EntityScan(basePackages = "entity")
@ComponentScan(basePackages = {"com.iluwatar.layers", "service", "dto", "exception", "view" ,"dao"})
public class LayersApp {

    public static void main(String[] args) {
        SpringApplication.run(LayersApp.class, args);

    }

}
