package com.iluwatar.delegation.simple.printers;

import com.iluwatar.delegation.simple.Printer;

/**
 * Specialised Implementation of {@link Printer} for a HP Printer, in
 * this case the message to be printed is appended to "HP Printer : "
 *
 * @see Printer
 */
public class HpPrinter implements Printer {

  /**
   * {@inheritDoc}
   */
  @Override
  public void print(String message) {
    System.out.println("HP Printer : " + message);
  }

}
