package com.iluwatar.notification;

public class DataTransferObject {
    protected DataTransferObject () {}

    private Notification notification = new Notification();

    /**
     * Get a Notification object.
     *
     * @return Notification object.
     */
    public Notification getNotification() {
        return notification;
    }

}
