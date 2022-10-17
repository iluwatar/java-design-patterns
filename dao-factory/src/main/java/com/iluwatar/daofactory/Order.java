package com.iluwatar.daofactory;

import java.io.Serializable;

/**
 * Order Transfer Object (Transfer Object for Order class)
 */
public class Order implements Serializable {
    // Variable for Order Object
    private int id = -1;
    private String senderName;
    private String receiverName;
    private String destination;

    public Order() {}

    /**
     * Getter for variable in Order Object
     */
    public int getId() {
        return id;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getDestination() {
        return destination;
    }

    /**
     * Setter for variable in Order Object
     */
    public void setId(int id) {
        this.id = id;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", senderName='" + senderName + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }
}
