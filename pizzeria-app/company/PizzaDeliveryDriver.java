package com.company;
public class PizzaDeliveryDriver implements Observer {

    private String name;

    PizzaDeliveryDriver(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.printf("%s last updates: %s%n", name, message);
    }
}
