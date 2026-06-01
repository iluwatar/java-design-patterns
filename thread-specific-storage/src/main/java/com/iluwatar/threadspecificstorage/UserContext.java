package com.iluwatar.threadspecificstorage;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Thread-Specific Object
 *
 * <p>Provides a service or data that is only accessible via a particular thread.
 */
@Data
@AllArgsConstructor
public class UserContext {
  private Long userId;
}
