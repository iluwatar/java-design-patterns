package com.iluwatar.adapter;

public class WarshipAdapter implements RowingBoat {

    private final Warship warship;

    public WarshipAdapter() {
        warship = new Warship();
    }

    @Override
    public void row() {
        warship.fire();
    }
}
