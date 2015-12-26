package com.iluwatar.delegation.simple.printers;

import com.iluwatar.delegation.simple.Printer;

public class HPPrinter implements Printer {

    @Override
    public void print(String message) {
        System.out.println("HP Printer : " + message);
    }

}
