package com.iluwatar.notification;

import lombok.Getter;

/**
 * We use a Data Transfer Object to communicate with Server Command.
 */
public class DataTransferObject {

  /**
   * Notification for this DataTransferObject.
   */
  @Getter
  public Notification notification = new Notification();

  /**
   * Instantiates a DataTransferObject.
   */
  protected DataTransferObject() {
    // This constructor is intentionally empty. Nothing special is needed here.
  }

}
