package com.iluwatar.dependentmapping;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
@Getter(AccessLevel.PACKAGE)
class DataBaseTrack {

  private final String name;

  DataBaseTrack(String name) {
    this.name = name;
  }

}