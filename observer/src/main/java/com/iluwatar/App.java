package com.iluwatar;

/**
 *
 * Observer pattern defines one-to-many relationship between objects. The target
 * object sends change notifications to its registered observers.
 *
 */
public class App {

    public static void main(String[] args) {

        Weather weather = new Weather();
        weather.addObserver(new Orcs());
        weather.addObserver(new Hobbits());

        weather.timePasses();
        weather.timePasses();
        weather.timePasses();
        weather.timePasses();

    }
}
