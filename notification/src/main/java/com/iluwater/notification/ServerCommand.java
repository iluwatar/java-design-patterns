package com.iluwater.notification;

/**
 *
 */
public class ServerCommand {
  protected DataTransferObject data;

  /**
   *
   * @param data
   */
  public ServerCommand(DataTransferObject data) {
    this.data = data;
  }

  /**
   *
   * @return
   */
  public Notification getNotification() {
        return data.getNotification();
  }
}
