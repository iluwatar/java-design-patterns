package com.iluwatar;

import lombok.AllArgsConstructor;

/**
 * Stores the dto and access the notification within it.
 * Acting as a layer supertype in this instance for the domain layer.
 */
@AllArgsConstructor
public class ServerCommand {
  protected DataTransferObject data;

  /**
   * Basic getter to extract information from our data.
   *
   * @return the notification stored within the data
   */
  public Notification getNotification() {
    return data.getNotification();
  }
}
