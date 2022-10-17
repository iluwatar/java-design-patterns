package com.iluwatar.sessionfacade.entity;

/**
 * Business entity - Multiplication.
 */
public class Multiplication {
    double x;
    double y;

    public Multiplication(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double evaluate() {
        return this.x * this.y;
    }
}
