package com.iluwatar;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Layer super type for all Data Transfer Objects.
 * Also contains code for accessing our notification.
 */
@Getter
@NoArgsConstructor
public class DataTransferObject {

  private final Notification notification = new Notification();

}
