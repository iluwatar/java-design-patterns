package com.iluwatar.choreography.servicepackage;

import static com.iluwatar.choreography.Util.performAction;

import com.iluwatar.choreography.MainService;
import com.iluwatar.choreography.SagaService;
import com.iluwatar.choreography.events.DeliveryFailureEvent;
import com.iluwatar.choreography.events.PackageEvent;
import com.iluwatar.choreography.events.RequestScheduleDeliveryEvent;
import com.iluwatar.choreography.response.Response;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class PackageService implements SagaService {

  final AtomicLong counter = new AtomicLong();
  private final MainService mainService;
  Random random = new Random();

  public PackageService(MainService mainService) {
    this.mainService = mainService;
  }

  long getNextId() {
    return counter.getAndIncrement();
  }

  boolean checkItemIsInStock() {
    return Math.abs(random.nextInt() % 3) != 0;
  }

  /**
   * Creates a package in response to a RequestScheduleDeliveryEvent event.
   *
   * @param event the event that was received
   * @return a response with either the id of the generated package, or a failure
   */
  public Response getPackage(RequestScheduleDeliveryEvent event) {
    performAction(event, "Gathering items...");
    if (checkItemIsInStock()) {
      final long id = getNextId();
      performAction(event, "Gathering packing materials...");
      performAction(event, "Packing...");
      performAction(event, "Addressing to " + event.getAddress() + "...");
      performAction(event, "Moving package " + id + " to pickup location in warehouse. ");
      return mainService.post(
          new PackageEvent(event.getSagaId(), new Package(id, event.getAddress())));
    } else {
      return mainService.post(new DeliveryFailureEvent(event.getSagaId(),
          null,
          null,
          "Delivery failed! Item is not in stock!"));
    }
  }

  @Override
  public void onSagaFailure(DeliveryFailureEvent failureEvent) {
    failureEvent.getLocalPackage().ifPresent(localPackage ->
        performAction(failureEvent,
            "Putting items from package " + localPackage.getId() + " back..."));
  }
}
