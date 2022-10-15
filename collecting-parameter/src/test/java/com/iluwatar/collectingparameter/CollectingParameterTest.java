package com.iluwatar.collectingparameter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.LinkedList;
import java.util.Queue;

public class CollectingParameterTest {

  @Test
  @Timeout(1000)
  public void testCollectingParameter() {
    PrinterQueue printerQueue = PrinterQueue.getInstance();

    PrinterQueue.PrinterItem item1 = new PrinterQueue.PrinterItem(PaperSizes.A4, 1, false, true);
    PrinterQueue.PrinterItem item2 = new PrinterQueue.PrinterItem(PaperSizes.A4, 10, true, false);
    PrinterQueue.PrinterItem item3 = new PrinterQueue.PrinterItem(PaperSizes.A4, 4, true, true);
    PrinterQueue.PrinterItem item4 = new PrinterQueue.PrinterItem(PaperSizes.A3, 9, false, false);
    PrinterQueue.PrinterItem item5 = new PrinterQueue.PrinterItem(PaperSizes.A3, 3, true, true);
    PrinterQueue.PrinterItem item6 = new PrinterQueue.PrinterItem(PaperSizes.A3, 3, false, true);
    PrinterQueue.PrinterItem item7 = new PrinterQueue.PrinterItem(PaperSizes.A3, 3, true, false);
    PrinterQueue.PrinterItem item8 = new PrinterQueue.PrinterItem(PaperSizes.A2, 1, false, false);
    PrinterQueue.PrinterItem item9 = new PrinterQueue.PrinterItem(PaperSizes.A2, 2, false, false);
    PrinterQueue.PrinterItem item10 = new PrinterQueue.PrinterItem(PaperSizes.A2, 1, true, false);
    PrinterQueue.PrinterItem item11 = new PrinterQueue.PrinterItem(PaperSizes.A2, 1, false, true);

    printerQueue.addPrinterItem(item1);
    printerQueue.addPrinterItem(item2);
    printerQueue.addPrinterItem(item3);
    printerQueue.addPrinterItem(item4);
    printerQueue.addPrinterItem(item5);
    printerQueue.addPrinterItem(item6);
    printerQueue.addPrinterItem(item7);
    printerQueue.addPrinterItem(item8);
    printerQueue.addPrinterItem(item9);
    printerQueue.addPrinterItem(item10);
    printerQueue.addPrinterItem(item11);

    Queue<PrinterQueue.PrinterItem> result = new LinkedList<>();
    App.addA4Papers(result);
    App.addA3Papers(result);
    App.addA2Papers(result);

    Queue<PrinterQueue.PrinterItem> testResult = new LinkedList<>();
    testResult.add(item1);
    testResult.add(item2);
    testResult.add(item4);
    testResult.add(item8);

    Assertions.assertArrayEquals(testResult.toArray(), result.toArray());
  }
}
