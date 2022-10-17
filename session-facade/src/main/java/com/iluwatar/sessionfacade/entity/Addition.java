package com.iluwatar.sessionfacade.entity;

/**
 * Business entity - Addition.
 */

public class Addition {
    double x;
    double y;

    public Addition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double evaluate() {
        return this.x + this.y;
    }
}
