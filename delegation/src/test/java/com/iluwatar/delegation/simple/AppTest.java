package com.iluwatar.delegation.simple;

import com.iluwatar.delegation.simple.printers.CanonPrinter;
import com.iluwatar.delegation.simple.printers.EpsonPrinter;
import com.iluwatar.delegation.simple.printers.HPPrinter;
import org.junit.Test;

public class AppTest {

    public static final String MESSAGE_TO_PRINT = "hello world";

    @Test
    public void main() {
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
