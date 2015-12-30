package com.iluwatar.delegation.simple;

import com.iluwatar.delegation.simple.printers.HpPrinter;

/**
 * Interface that both the Controller and the Delegate will implement.
 *
 * @see com.iluwatar.delegation.simple.printers.CanonPrinter
 * @see com.iluwatar.delegation.simple.printers.EpsonPrinter
 * @see HpPrinter
 */
public interface Printer {

  /**
   * Method that takes a String to print to the screen. This will be implemented on both the
   * controller and the delegate allowing the controller to call the same method on the delegate class.
   *
   * @param message to be printed to the screen
   */
  void print(final String message);
}
