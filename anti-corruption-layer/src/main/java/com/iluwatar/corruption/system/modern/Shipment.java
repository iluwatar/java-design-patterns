package com.iluwatar.corruption.system.modern;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shipment shipment = (Shipment) o;

        if (qty != shipment.qty) return false;
        if (price != shipment.price) return false;
        return Objects.equals(item, shipment.item);
    }

    @Override
    public int hashCode() {
        int result = item != null ? item.hashCode() : 0;
        result = 31 * result + qty;
        result = 31 * result + price;
        return result;
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
