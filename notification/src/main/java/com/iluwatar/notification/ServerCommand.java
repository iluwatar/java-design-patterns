package com.iluwatar.notification;

public class ServerCommand {
    protected DataTransferObject data;

    /**
     * Creates a Server Command with DTO.
     *
     * @param data the DTO to use in this server.
     */
    public ServerCommand(DataTransferObject data){
        this.data = data;
    }

    protected Notification getNotification() {
        return this.data.getNotification();
    }

}
