package com.iluwatar.choreography.events;

import com.iluwatar.choreography.servicedrone.Drone;
import com.iluwatar.choreography.servicepackage.Package;
import java.util.Optional;

public class DeliveryFailureEvent extends Event {

  private final String message;
  private final Drone drone;
  private final Package aPackage;

  public DeliveryFailureEvent(int sagaId, Drone drone, Package aPackage, String message) {
    super(sagaId);
    this.message = message;
    this.drone = drone;
    this.aPackage = aPackage;
  }

  public String getMessage() {
    return message;
  }

  public Optional<Drone> getDrone() {
    return Optional.ofNullable(drone);
  }

  public Optional<Package> getaPackage() {
    return Optional.ofNullable(aPackage);
  }
}
