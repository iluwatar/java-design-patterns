package com.company;

public class CollectionInPerson implements deliveryStrategy {
    public String deliveryAddress;
    public String deliveryMethod;
    @Override
    public void dologic() {
        System.out.println("You have chosen collection in person");
        System.out.println("Pizza to pick up in " + deliveryAddress);
        deliveryMethod = "Collection in person";



    }
}
