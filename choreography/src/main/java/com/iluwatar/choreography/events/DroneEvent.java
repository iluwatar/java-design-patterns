package com.iluwatar.choreography.events;

import com.iluwatar.choreography.servicedrone.Drone;
import com.iluwatar.choreography.servicepackage.Package;

public class DroneEvent extends Event {

    private final Drone drone;
    private final Package aPackage;

    public DroneEvent( int sagaId, Drone drone, Package aPackage) {
        super(sagaId);
        this.drone = drone;
        this.aPackage = aPackage;
    }

    public Drone getDrone() {
        return drone;
    }

    public Package getaPackage() {
        return aPackage;
    }
}
