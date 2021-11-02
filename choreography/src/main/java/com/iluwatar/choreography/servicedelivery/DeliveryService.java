package com.iluwatar.choreography.servicedelivery;

import com.iluwatar.choreography.MainService;
import com.iluwatar.choreography.SagaService;
import com.iluwatar.choreography.events.DeliveryFailureEvent;
import com.iluwatar.choreography.events.DeliverySuccessEvent;
import com.iluwatar.choreography.events.DroneEvent;
import com.iluwatar.choreography.response.Response;

import java.util.List;

import static com.iluwatar.choreography.Util.performAction;

public class DeliveryService implements SagaService {

    public static String WALLABY_WAY = "42 Wallaby Way, Sydney, Australia";

    public static String BUCKINGHAM = "Buckingham Palace, London, England";
    final private List<String> validAddresses = List.of(
            WALLABY_WAY,
            BUCKINGHAM
    );

    private final MainService mainService;

    public DeliveryService(MainService mainService) {
        this.mainService = mainService;
    }

    public Response completeDelivery(DroneEvent e) {
        String address = e.getaPackage().getAddress();
        if (validAddresses.contains(address)) {
            performAction(e, "Drone " + e.getDrone().getId() + " is flying to " + address + "...");
            performAction(e, "Dropping off package " + e.getaPackage().getId() + "...");
            performAction(e, "Returning to base...");
            return mainService.post(new DeliverySuccessEvent(e.getSagaId(), "Delivery Completed"));
        } else {
            return mainService.post(new DeliveryFailureEvent(e.getSagaId(),
                    e.getDrone(),
                    e.getaPackage(),
                    "Could not complete delivery! Address not found."));
        }
    }

    @Override
    public void onSagaFailure(DeliveryFailureEvent failureEvent) {
        // no cleanup to perform, as this is the terminal step
    }
}
