package com.iluwatar.notification;

import lombok.Getter;

/**
 * We use a Data Transfer Object to communicate with Server Command
 */
public class DataTransferObject {
  /**
   * Instantiates a DataTransferObject
   */
  protected DataTransferObject() {
    // This constructor is intentionally empty. Nothing special is needed here.
  }

  /**
   * Notification for this DataTransferObject
   */
  @Getter
  public Notification notification = new Notification();


}
