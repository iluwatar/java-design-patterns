package com.iluwatar.choreography.events;

public class RequestScheduleDeliveryEvent extends Event {

  private final String address;

  public RequestScheduleDeliveryEvent(int sagaId, String address) {
    super(sagaId);
    this.address = address;
  }

  public String getAddress() {
    return address;
  }
}
