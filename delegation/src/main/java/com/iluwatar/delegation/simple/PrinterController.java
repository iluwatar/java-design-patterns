package com.iluwatar.delegation.simple;

public class PrinterController extends AbstractPrinterController {

  public PrinterController(Printer printer) {
    super(printer);
  }

  /**
   * This method is implemented from {@link Printer} however instead on providing an
   * implementation, it instead calls upon the class passed through the constructor. This is the delegate,
   * hence the pattern. Therefore meaning that the caller does not care of the implementing class only the owning
   * controller.
   *
   * @param message to be printed to the screen
   */
  @Override
  public void print(String message) {
    getPrinter().print(message);
  }
}
