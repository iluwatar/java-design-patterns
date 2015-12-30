package com.iluwatar.delegation.simple;

import com.iluwatar.delegation.simple.printers.CanonPrinter;
import com.iluwatar.delegation.simple.printers.EpsonPrinter;
import com.iluwatar.delegation.simple.printers.HpPrinter;

/**
 * In this example the delegates are {@link EpsonPrinter}, {@link HpPrinter} and {@link CanonPrinter} they all implement
 * {@link Printer}. The {@link AbstractPrinterController} and through inheritance {@link PrinterController} also
 * implement {@link Printer}. However neither provide the functionality of {@link Printer} by printing to the screen,
 * they actually call upon the instance of {@link Printer} that they were instantiated with. Therefore delegating the
 * behaviour to another class.
 */
public class App {

  public static final String MESSAGE_TO_PRINT = "hello world";

  /**
   * Program entry point
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    PrinterController hpPrinterController = new PrinterController(new HpPrinter());
    PrinterController canonPrinterController = new PrinterController(new CanonPrinter());
    PrinterController epsonPrinterController = new PrinterController(new EpsonPrinter());

    hpPrinterController.print(MESSAGE_TO_PRINT);
    canonPrinterController.print(MESSAGE_TO_PRINT);
    epsonPrinterController.print(MESSAGE_TO_PRINT);
  }

}
