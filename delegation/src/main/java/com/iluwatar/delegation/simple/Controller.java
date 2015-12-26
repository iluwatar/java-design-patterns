package com.iluwatar.delegation.simple;

public abstract class Controller<T extends Printer> {

    private Printer printer;

    public Controller(Printer printer) {
        this.printer = printer;
    }

    protected Printer getPrinter() {
        return printer;
    }
}
