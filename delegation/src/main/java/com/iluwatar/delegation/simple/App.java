package com.iluwatar.delegation.simple;

import com.iluwatar.delegation.simple.printers.CanonPrinter;
import com.iluwatar.delegation.simple.printers.EpsonPrinter;
import com.iluwatar.delegation.simple.printers.HPPrinter;

public class App {

    public static final String MESSAGE_TO_PRINT = "hello world";

    public static void main(String[] args) {
        AbstractPrinterController hpPrinterController = new PrinterController(new HPPrinter());
        AbstractPrinterController canonPrinterController = new PrinterController(new CanonPrinter());
        AbstractPrinterController epsonPrinterController = new PrinterController(new EpsonPrinter());

        hpPrinterController.print(MESSAGE_TO_PRINT);
        canonPrinterController.print(MESSAGE_TO_PRINT);
        epsonPrinterController.print(MESSAGE_TO_PRINT);
    }

}
