package com.iluwatar.delegation.simple.printers;

import com.iluwatar.delegation.simple.Printer;

/**
 * Specialised Implementation of {@link Printer} for a Canon Printer, in
 * this case the message to be printed is appended to "Canon Printer : "
 *
 * @see Printer
 */
public class CanonPrinter implements Printer {

  /**
   * {@inheritDoc}
   */
  @Override
  public void print(String message) {
    System.out.print("Canon Printer : " + message);
  }

}
