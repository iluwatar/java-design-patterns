package com.iluwatar.corruption.system.modern;

public class Shipment {
    private String item;
    private int qty;
    private int price;

    public String getItem() {
        return item;
    }

    public Shipment(String item, int qty, int price) {
        this.item = item;
        this.qty = qty;
        this.price = price;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
