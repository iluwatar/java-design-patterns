package com.iluwatar.collectingparameter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

public class PrinterQueueTest {

  @Test
  @Timeout(1000)
  public void singletonTest() {
    PrinterQueue printerQueue1 = PrinterQueue.getInstance();
    PrinterQueue printerQueue2 = PrinterQueue.getInstance();
    assertSame(printerQueue1, printerQueue2);
  }

  @Test
  @Timeout(1000)
  public void getPrinterQueueTest() {
    PrinterQueue printerQueue = PrinterQueue.getInstance();
    PrinterQueue.PrinterItem item1 = new PrinterQueue.PrinterItem(PaperSizes.A4, 5, false, false);
    PrinterQueue.PrinterItem item2 = new PrinterQueue.PrinterItem(PaperSizes.A4, 10, true, false);
    PrinterQueue.PrinterItem item3 = new PrinterQueue.PrinterItem(PaperSizes.A3, 2, false, false);
    printerQueue.addPrinterItem(item1);
    printerQueue.addPrinterItem(item2);
    printerQueue.addPrinterItem(item3);
    Queue<PrinterQueue.PrinterItem> testQueue = new LinkedList<>();
    testQueue.add(item1);
    testQueue.add(item2);
    testQueue.add(item3);
    Assertions.assertArrayEquals(testQueue.toArray(), printerQueue.getPrinterQueue().toArray());
  }

  @Test()
  @Timeout(1000)
  public void negativePageCount() throws IllegalArgumentException {
    Assertions.assertThrows(IllegalArgumentException.class, () -> new PrinterQueue.PrinterItem(PaperSizes.A4, -1, true, true));
  }

  @Test()
  @Timeout(1000)
  public void nullPageSize() throws IllegalArgumentException {
    Assertions.assertThrows(IllegalArgumentException.class, () -> new PrinterQueue.PrinterItem(null, 1, true, true));
  }

}
