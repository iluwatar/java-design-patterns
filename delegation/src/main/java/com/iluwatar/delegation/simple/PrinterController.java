package com.iluwatar.delegation.simple;

public class PrinterController extends AbstractPrinterController {

    public PrinterController(Printer printer) {
        super(printer);
    }

    @Override
    public void print(String message) {
        getPrinter().print(message);
    }
}
