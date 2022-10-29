package com.iluwater;

/**
 * Stores the dto and access the notification within it.
 * Acting as a layer supertype in this instance for the domain layer.
 */
public class ServerCommand {
  protected DataTransferObject data;

  /**
   * Creates a server command.
   *
   * @param data the data to be stored
   */
  public ServerCommand(DataTransferObject data) {
    this.data = data;
  }

  /**
   * Basic getter to extract information from our data.
   *
   * @return the notification stored within the data
   */
  public Notification getNotification() {
    return data.getNotification();
  }
}
