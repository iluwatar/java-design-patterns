package com.iluwatar.collectingparameter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class PrinterQueueTest {

  @Test
  @Timeout(1000)
  void singletonTest() {
    PrinterQueue printerQueue1 = PrinterQueue.getInstance();
    PrinterQueue printerQueue2 = PrinterQueue.getInstance();
    assertSame(printerQueue1, printerQueue2);
  }

  @Test()
  @Timeout(1000)
  void negativePageCount() throws IllegalArgumentException {
    Assertions.assertThrows(IllegalArgumentException.class, () -> new PrinterItem(PaperSizes.A4, -1, true, true));
  }

  @Test()
  @Timeout(1000)
  void nullPageSize() throws IllegalArgumentException {
    Assertions.assertThrows(IllegalArgumentException.class, () -> new PrinterItem(null, 1, true, true));
  }

}