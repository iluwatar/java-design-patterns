package com.iluwatar.remote.facade;

public class Product {
    private String name;
    private double price;

    public Product (String nm, double prc) {
        name = nm;
        price = prc;
    }

     //getters and setters
    public String getName() { return name; }
    public double getPrice() { return price; }

}
