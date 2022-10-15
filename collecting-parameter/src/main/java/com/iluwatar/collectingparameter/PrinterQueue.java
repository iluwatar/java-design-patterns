package com.iluwatar.collectingparameter;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class PrinterQueue {


    static PrinterQueue currentInstance = null;
    public static Queue<PrinterItem> printerItemQueue;

    public static PrinterQueue getInstance() {
        if (Objects.isNull(currentInstance)) {
            currentInstance = new PrinterQueue();
        }
        return currentInstance;
    }

    private PrinterQueue() {
        printerItemQueue = new LinkedList<>();
    }

    public void addPrinterItem (PrinterItem printerItem) {
        printerItemQueue.add(printerItem);
    }


    public static class PrinterItem {
        PaperSizes paperSize;
        int pageCount;
        boolean isDoubleSided;
        boolean isColour;

        PrinterItem (PaperSizes paperSize, int pageCount, boolean isDoubleSided, boolean isColour) {

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

}
