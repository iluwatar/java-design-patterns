package com.iluwatar.notification;

public class ServerCommand {
    protected DataTransferObject data;

    public ServerCommand(DataTransferObject data){
        this.data = data;
    }

    protected Notification getNotification() {
        return this.data.getNotification();
    }

}
