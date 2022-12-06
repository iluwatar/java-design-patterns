package com.iluwatar.collectingparameter;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * This class represents a singleton Printer Queue. It contains a queue that can be filled up with {@link PrinterItem}.
 **/
public class PrinterQueue {

  static PrinterQueue currentInstance = null;
  private final Queue<PrinterItem> printerItemQueue;

  /**
   * This class is a singleton. The getInstance method will ensure that only one instance exists at a time.
   */
  public static PrinterQueue getInstance() {
    if (Objects.isNull(currentInstance)) {
      currentInstance = new PrinterQueue();
    }
    return currentInstance;
  }

  /**
   * Empty the printer queue.
   */
  public void emptyQueue() {
    currentInstance.getPrinterQueue().clear();
  }

  /**
   * Private constructor prevents instantiation, unless using the getInstance() method.
   */
  private PrinterQueue() {
    printerItemQueue = new LinkedList<>();
  }

  public Queue<PrinterItem> getPrinterQueue() {
    return currentInstance.printerItemQueue;
  }

  /**
   * Adds a single print job to the queue.
   *
   * @param printerItem The printing job to be added to the queue
   */
  public void addPrinterItem(PrinterItem printerItem) {
    currentInstance.getPrinterQueue().add(printerItem);
  }

}
