package com.iluwater.notification;

import lombok.Getter;

/**
 * Layer super type for all Data Transfer Objects.
 * Also contains code for accessing our notification.
 */
public class DataTransferObject {
  @Getter
  protected Notification notification = new Notification();

  public DataTransferObject() {
    //This constructor is purposefully left blank.
    //In practice this may contain a common constructor that can
    //be used by all DataTransferObjects in this layer
  }
}
