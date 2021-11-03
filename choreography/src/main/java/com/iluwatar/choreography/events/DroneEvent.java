package com.iluwatar.choreography.events;

import com.iluwatar.choreography.servicedrone.Drone;
import com.iluwatar.choreography.servicepackage.Package;

public class DroneEvent extends Event {

  private final Drone drone;
  private final Package localPackage;

  /**
   * An event that announces that a drone has been successfully provisioned.
   *
   * <p>
   * It passes information on to the next event in the saga.
   * </p>
   *
   * @param sagaId       the id of the current saga
   * @param drone        the id of the provisioned drone
   * @param localPackage the id of the package the drone is delivering
   */
  public DroneEvent(int sagaId, Drone drone, Package localPackage) {
    super(sagaId);
    this.drone = drone;
    this.localPackage = localPackage;
  }

  public Drone getDrone() {
    return drone;
  }

  public Package getLocalPackage() {
    return localPackage;
  }
}
