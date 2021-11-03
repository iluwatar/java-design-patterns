package com.iluwatar.choreography.response;

import com.iluwatar.choreography.events.DeliverySuccessEvent;

public class OK implements Response {

  private final String message;

  public OK(DeliverySuccessEvent event) {
    this.message = event.prettyPrintSagaId() + event.getMessage();
  }

  @Override
  public String getMessage() {
    return message;
  }
}
