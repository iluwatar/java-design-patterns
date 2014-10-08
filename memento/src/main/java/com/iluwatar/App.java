package com.iluwatar;

import java.util.Stack;

/**
 *
 * Memento pattern is for storing and restoring object state. The object (Star)
 * gives out a "memento" (StarMemento) that contains the state of the object.
 * Later on the memento can be set back to the object restoring the state.
 *
 */
public class App {

    public static void main(String[] args) {
        Stack<StarMemento> states = new Stack<>();

        Star star = new Star(StarType.SUN, 10000000, 500000);
        System.out.println(star);
        states.add(star.getMemento());
        star.timePasses();
        System.out.println(star);
        states.add(star.getMemento());
        star.timePasses();
        System.out.println(star);
        states.add(star.getMemento());
        star.timePasses();
        System.out.println(star);
        states.add(star.getMemento());
        star.timePasses();
        System.out.println(star);
        while (states.size() > 0) {
            star.setMemento(states.pop());
            System.out.println(star);
        }
    }
}
