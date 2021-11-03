package com.iluwatar.choreography.events;

import com.iluwatar.choreography.servicepackage.Package;

public class PackageEvent extends Event {

  private final Package localPackage;

  public PackageEvent(int sagaId, Package localPackage) {
    super(sagaId);
    this.localPackage = localPackage;
  }

  public Package getLocalPackage() {
    return localPackage;
  }
}
