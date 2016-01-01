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
    PrinterController printerController = new PrinterController(new CanonPrinter());
    printerController.print(MESSAGE);

    assertEquals("Canon Printer : Test Message Printed", systemOutRule.getLog());
  }

  @Test
  public void testHpPrinter() throws Exception {
    PrinterController printerController = new PrinterController(new HpPrinter());
    printerController.print(MESSAGE);

    assertEquals("HP Printer : Test Message Printed", systemOutRule.getLog());
  }

  @Test
  public void testEpsonPrinter() throws Exception {
    PrinterController printerController = new PrinterController(new EpsonPrinter());
    printerController.print(MESSAGE);

    assertEquals("Epson Printer : Test Message Printed", systemOutRule.getLog());
  }

}
