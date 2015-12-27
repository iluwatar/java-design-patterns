package com.iluwatar.delegation.simple;

public abstract class AbstractPrinterController<T extends Printer> implements Printer{

    private Printer printer;

    public AbstractPrinterController(Printer printer) {
        this.printer = printer;
    }

    protected Printer getPrinter() {
        return printer;
    }
}
