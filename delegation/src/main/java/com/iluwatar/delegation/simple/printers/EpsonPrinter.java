package com.iluwatar.delegation.simple.printers;

import com.iluwatar.delegation.simple.Printer;

public class EpsonPrinter implements Printer{

    @Override
    public void print(String message) {
        System.out.println("Epson Printer : " + message);
    }

}
