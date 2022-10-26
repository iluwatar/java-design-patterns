package com.iluwatar.collectingparameter;

import java.util.Objects;

/**
 * This class represents a Print Item, that should be added to the queue.
 **/
public class PrinterItem {
  PaperSizes paperSize;
  int pageCount;
  boolean isDoubleSided;
  boolean isColour;

  /**
   * The {@link PrinterItem} constructor.
   **/
  public PrinterItem(PaperSizes paperSize, int pageCount, boolean isDoubleSided, boolean isColour) {
    if (!Objects.isNull(paperSize)) {
      this.paperSize = paperSize;
    } else {
      throw new IllegalArgumentException();
    }

    if (pageCount > 0) {
      this.pageCount = pageCount;
    } else {
      throw new IllegalArgumentException();
    }

    this.isColour = isColour;
    this.isDoubleSided = isDoubleSided;

  }
}
