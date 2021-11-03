package com.iluwatar.choreography.events;

import com.iluwatar.choreography.servicepackage.Package;

public class PackageEvent extends Event {

  private final Package aPackage;

  public PackageEvent(int sagaId, Package aPackage) {
    super(sagaId);
    this.aPackage = aPackage;
  }

  public Package getaPackage() {
    return aPackage;
  }
}
