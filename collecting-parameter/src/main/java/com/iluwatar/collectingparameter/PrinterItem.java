package com.iluwatar.collectingparameter;

import java.util.Objects;

public class PrinterItem {
  /**
   * This class represents a Print Item, that should be added to the queue.
   **/
    PaperSizes paperSize;
    int pageCount;
    boolean isDoubleSided;
    boolean isColour;

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

  @Override
  public String toString() {
    return "PrinterItem{" +
            "paperSize=" + paperSize +
            ", pageCount=" + pageCount +
            ", isDoubleSided=" + isDoubleSided +
            ", isColour=" + isColour +
            '}';
  }
}
