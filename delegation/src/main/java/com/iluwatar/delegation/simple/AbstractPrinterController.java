package com.iluwatar.delegation.simple;

/**
 * Extra layer of abstraction for the controller to allow the controller in this example {@link PrinterController} to
 * be as clean as possible. This just provides the default constructor and a simple getter method. The generic of
 * T allows any implementation of {@link Printer}
 *
 * @param <T> Printer
 * @see Printer
 * @see PrinterController
 */
public abstract class AbstractPrinterController<T extends Printer> implements Printer {


  private T printer;

  /**
   * @param printer instance of T {@link Printer} this instance is the delegate
   */
  public AbstractPrinterController(T printer) {
    this.printer = printer;
  }

  /**
   * Helper method to return the current instance of T {@link Printer} in order for
   * the controller to call operations on the {@link Printer}
   *
   * @return instance of Printer
   * @see Printer
   */
  protected T getPrinter() {
    return printer;
  }
}
