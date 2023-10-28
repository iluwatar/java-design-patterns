package com.oscar.microservice.architecture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class App {
    /**
     * Program entry point.
     *
     * @param args command line args
     */

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
