package com.iluwatar.choreography.events;

import com.iluwatar.choreography.servicedrone.Drone;
import com.iluwatar.choreography.servicepackage.Package;
import java.util.Optional;

public class DeliveryFailureEvent extends Event {

  private final String message;
  private final Drone drone;
  private final Package localPackage;

  /**
   * An event for announcing that the entire saga has failed.
   *
   * @param sagaId       the id of the current saga
   * @param drone        the id of the drone
   * @param localPackage the id of the package
   * @param message      the failure message
   */
  public DeliveryFailureEvent(int sagaId, Drone drone, Package localPackage, String message) {
    super(sagaId);
    this.message = message;
    this.drone = drone;
    this.localPackage = localPackage;
  }

  public String getMessage() {
    return message;
  }

  public Optional<Drone> getDrone() {
    return Optional.ofNullable(drone);
  }

  public Optional<Package> getLocalPackage() {
    return Optional.ofNullable(localPackage);
  }
}
