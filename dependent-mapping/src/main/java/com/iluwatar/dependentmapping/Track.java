package com.iluwatar.dependentmapping;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

/**
 * The type Track.
 */
@Data
@Getter(AccessLevel.PACKAGE)
class Track {

  private final String name;

  Track(String name) {
    this.name = name;
  }

}
