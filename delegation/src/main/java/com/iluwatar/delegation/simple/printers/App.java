package com.iluwatar.delegation.simple.printers;

import com.iluwatar.delegation.simple.Printer;
import com.iluwatar.delegation.simple.PrinterController;

public class App {

    public static final String MESSAGE_TO_PRINT = "hello world";

    public static void main(String[] args) {
        Printer hpPrinter = new HPPrinter();
        Printer canonPrinter = new CanonPrinter();
        Printer epsonPrinter = new EpsonPrinter();

        PrinterController hpPrinterController = new PrinterController(hpPrinter);
        PrinterController canonPrinterController = new PrinterController(canonPrinter);
        PrinterController epsonPrinterController = new PrinterController(epsonPrinter);

        hpPrinterController.print(MESSAGE_TO_PRINT);
        canonPrinterController.print(MESSAGE_TO_PRINT);
        epsonPrinterController.print(MESSAGE_TO_PRINT);
    }

}
