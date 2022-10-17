package com.iluwatar.sessionfacade.entity;

/**
 * Business entity - Subtraction.
 */
public class Subtraction {
    double x;
    double y;

    public Subtraction(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double evaluate() {
        return this.x - this.y;
    }
}
