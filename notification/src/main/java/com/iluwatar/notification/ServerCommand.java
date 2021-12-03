package com.iluwatar.notification;

/**
 * ServerCommand contains DTO
 */
public class ServerCommand {

  /**
   * data for this ServerCommand
   */
  protected DataTransferObject data;

  /**
  * Creates a Server Command with DTO.
  *
  * @param data the DTO to use in this server.
  */
  public ServerCommand(final DataTransferObject data) {
    this.data = data;
  }

  /**
   * Get Notification for this ServerCommand
   *
   * @return Notification
   */
  protected Notification getNotification() {

    return this.data.getNotification();
  }

}
