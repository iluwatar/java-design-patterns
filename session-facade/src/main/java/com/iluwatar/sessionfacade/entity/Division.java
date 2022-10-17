package com.iluwatar.sessionfacade.entity;

/**
 * Business entity - Division.
 */
public class Division {
    double x;
    double y;

    public Division(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double evaluate() {

        if (this.y == 0) {
            throw new ArithmeticException("/ by zero");
        }
        return this.x / this.y;
    }
}
