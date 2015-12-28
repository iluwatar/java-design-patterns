package com.iluwatar.delegation.simple;

import com.iluwatar.delegation.simple.printers.CanonPrinter;
import com.iluwatar.delegation.simple.printers.EpsonPrinter;
import com.iluwatar.delegation.simple.printers.HpPrinter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static org.junit.Assert.assertEquals;

public class DelegateTest {

    private static final String MESSAGE = "Test Message Printed";

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Test
    public void testCanonPrinter() throws Exception {
        AbstractPrinterController abstractController = new PrinterController(new CanonPrinter());
        abstractController.print(MESSAGE);

        assertEquals("Canon Printer : Test Message Printed\n", systemOutRule.getLog());
    }

    @Test
    public void testHPPrinter() throws Exception {
        AbstractPrinterController abstractController = new PrinterController(new HpPrinter());
        abstractController.print(MESSAGE);

        assertEquals("HP Printer : Test Message Printed\n", systemOutRule.getLog());
    }

    @Test
    public void testEpsonPrinter() throws Exception {
        AbstractPrinterController abstractController = new PrinterController(new EpsonPrinter());
        abstractController.print(MESSAGE);

        assertEquals("Epson Printer : Test Message Printed\n", systemOutRule.getLog());
    }

}
