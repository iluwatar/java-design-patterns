package com.iluwatar.collectingparameter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.LinkedList;
import java.util.Queue;

class CollectingParameterTest {

  @Test
  @Timeout(1000)
  void testCollectingParameter() {
    PrinterQueue printerQueue = PrinterQueue.getInstance();
    printerQueue.emptyQueue();

    PrinterItem item1 = new PrinterItem(PaperSizes.A4, 1, false, true);
    PrinterItem item2 = new PrinterItem(PaperSizes.A4, 10, true, false);
    PrinterItem item3 = new PrinterItem(PaperSizes.A4, 4, true, true);
    PrinterItem item4 = new PrinterItem(PaperSizes.A3, 9, false, false);
    PrinterItem item5 = new PrinterItem(PaperSizes.A3, 3, true, true);
    PrinterItem item6 = new PrinterItem(PaperSizes.A3, 3, false, true);
    PrinterItem item7 = new PrinterItem(PaperSizes.A3, 3, true, false);
    PrinterItem item8 = new PrinterItem(PaperSizes.A2, 1, false, false);
    PrinterItem item9 = new PrinterItem(PaperSizes.A2, 2, false, false);
    PrinterItem item10 = new PrinterItem(PaperSizes.A2, 1, true, false);
    PrinterItem item11 = new PrinterItem(PaperSizes.A2, 1, false, true);

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

    Queue<PrinterItem> result = new LinkedList<>();
    App.addValidA4Papers(result);
    App.addValidA3Papers(result);
    App.addValidA2Papers(result);

    Queue<PrinterItem> testResult = new LinkedList<>();
    testResult.add(item1);
    testResult.add(item2);
    testResult.add(item4);
    testResult.add(item8);

    Assertions.assertArrayEquals(testResult.toArray(), result.toArray());
  }
}
