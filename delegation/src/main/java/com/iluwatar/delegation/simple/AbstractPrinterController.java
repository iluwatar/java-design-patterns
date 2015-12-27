package com.iluwatar.delegation.simple;

public abstract class AbstractPrinterController<T extends Printer> implements Printer{

    private T printer;

    public AbstractPrinterController(T printer) {
        this.printer = printer;
    }

    protected T getPrinter() {
        return printer;
    }
}
