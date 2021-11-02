package com.iluwatar.choreography.servicedrone;

import com.iluwatar.choreography.MainService;
import com.iluwatar.choreography.SagaService;
import com.iluwatar.choreography.events.DeliveryFailureEvent;
import com.iluwatar.choreography.events.DroneEvent;
import com.iluwatar.choreography.events.PackageEvent;
import com.iluwatar.choreography.response.Response;

import java.util.concurrent.atomic.AtomicInteger;

import static com.iluwatar.choreography.Util.performAction;

public class DroneService implements SagaService {

    final AtomicInteger counter = new AtomicInteger();
    private final MainService mainService;

    public DroneService(MainService mainService) {
        this.mainService = mainService;
    }

    int getNextId() {
        return counter.getAndIncrement();
    }

    public Response getDrone(PackageEvent e) {
        int id = getNextId();
        performAction(e, "Contacting drone " + id + " at base...");
        performAction(e, "Drone " + id + " is preparing for pickup...");
        return mainService.post(new DroneEvent(e.getSagaId(), new Drone(id), e.getaPackage()));
    }

    @Override
    public void onSagaFailure(DeliveryFailureEvent failureEvent) {
        failureEvent.getDrone().ifPresent(drone ->
                performAction(failureEvent, "Setting drone " + drone.getId() + " to standby..."));
    }
}
