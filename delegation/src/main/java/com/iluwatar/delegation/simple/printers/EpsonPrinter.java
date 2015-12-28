package com.iluwatar.delegation.simple.printers;

import com.iluwatar.delegation.simple.Printer;

/**
 * Specialised Implementation of {@link Printer} for a Epson Printer, in
 * this case the message to be printed is appended to "Epson Printer : "
 *
 * @see Printer
 */
public class EpsonPrinter implements Printer{

    /**
     * {@inheritDoc}
     */
    @Override
    public void print(String message) {
        System.out.println("Epson Printer : " + message);
    }

}
