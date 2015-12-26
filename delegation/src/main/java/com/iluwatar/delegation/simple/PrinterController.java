package com.iluwatar.delegation.simple;

public class PrinterController extends Controller implements Printer {

    public PrinterController(Printer printer) {
        super(printer);
    }

    @Override
    public void print(String message) {
        getPrinter().print(message);
    }
}
