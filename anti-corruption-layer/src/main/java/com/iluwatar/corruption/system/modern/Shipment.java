package com.iluwatar.corruption.system.modern;

public class Shipment {
    private String item;
    private String qty;
    private String price;

    public String getItem() {
        return item;
    }

    public Shipment(String item, String qty, String price) {
        this.item = item;
        this.qty = qty;
        this.price = price;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
