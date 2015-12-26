package com.iluwatar.delegation.simple.printers;

import com.iluwatar.delegation.simple.Printer;

public class CanonPrinter implements Printer {

    @Override
    public void print(String message) {
        System.out.println("Canon Printer : " + message);
    }

}
