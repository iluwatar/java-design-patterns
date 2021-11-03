package com.iluwatar.choreography;

import com.iluwatar.choreography.events.DeliveryFailureEvent;
import com.iluwatar.choreography.events.DeliverySuccessEvent;
import com.iluwatar.choreography.events.DroneEvent;
import com.iluwatar.choreography.events.Event;
import com.iluwatar.choreography.events.PackageEvent;
import com.iluwatar.choreography.events.RequestScheduleDeliveryEvent;
import com.iluwatar.choreography.response.Failure;
import com.iluwatar.choreography.response.OK;
import com.iluwatar.choreography.response.Response;
import com.iluwatar.choreography.servicedelivery.DeliveryService;
import com.iluwatar.choreography.servicedrone.DroneService;
import com.iluwatar.choreography.servicepackage.PackageService;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class MainService {

  /*
   * These are fake services which will let us see the pattern without too much overhead
   * Normally these would be network requests to other live microservices.
   */

  /**
   * A service for getting a drone.
   */
  final DroneService droneService = new DroneService(this);

  /**
   * A service for sending drones with packages out to delivery.
   */
  final DeliveryService deliveryService = new DeliveryService(this);

  /**
   * A service for creating a package.
   */
  final PackageService packageService = new PackageService(this);

  /**
   * Generates new sagaId values, through which we can track the flow of events through multiple
   * microservices.
   */
  private final AtomicInteger sagaCounter = new AtomicInteger();

  /**
   * The method call that kicks off the whole flow. It creates a sagaId, and submits the first
   * event.
   *
   * @param address the address that we will be sending a package to
   * @return the response that comes back after the saga completes - either OK or Failure
   */
  public Response requestDeliveryTo(String address) {
    return post(new RequestScheduleDeliveryEvent(sagaCounter.getAndIncrement(), address));
  }

  /**
   * The message queue.
   *
   * <p>Events get fed into here, and other services are notified of the event The other services
   * are able to submit events back to the queue as well
   * </p>
   *
   * @param event an event that signifies that some piece of work is complete
   * @return the result of all the work that the current microservice has completed
   */
  public Response post(Event event) {
    if (event == null) {
      return new Failure("No events to process!");
    } else {
      CompletableFuture<Response> deferredResponse;
      if (event instanceof RequestScheduleDeliveryEvent) {
        deferredResponse = CompletableFuture.supplyAsync(
            () -> packageService.getPackage((RequestScheduleDeliveryEvent) event));
      } else if (event instanceof PackageEvent) {
        deferredResponse = CompletableFuture.supplyAsync(
            () -> droneService.getDrone((PackageEvent) event));
      } else if (event instanceof DroneEvent) {
        deferredResponse = CompletableFuture.supplyAsync(
            () -> deliveryService.completeDelivery((DroneEvent) event));
      } else if (event instanceof DeliverySuccessEvent) {
        deferredResponse = CompletableFuture.supplyAsync(
            () -> new OK((DeliverySuccessEvent) event));
      } else if (event instanceof DeliveryFailureEvent) {
        deferredResponse = CompletableFuture.supplyAsync(() -> {
          DeliveryFailureEvent failureEvent = (DeliveryFailureEvent) event;
          List.of(deliveryService,
              droneService,
              packageService).forEach(it -> it.onSagaFailure(failureEvent));
          return new Failure(failureEvent);
        });
      } else {
        deferredResponse = CompletableFuture.supplyAsync(
            () -> new Failure(event.getPrettySagaId() + "Could not handle that type of event!"));
      }
      return getResponse(deferredResponse);
    }
  }

  /**
   * Helper method to await for the future to complete.
   *
   * @param deferredResponse the response that we are waiting for
   * @return the response that was promised
   */
  private Response getResponse(CompletableFuture<Response> deferredResponse) {
    Response response = null;
    try {
      response = deferredResponse.get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    return response;
  }
}
