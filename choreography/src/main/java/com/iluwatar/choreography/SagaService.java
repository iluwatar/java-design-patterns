package com.iluwatar.choreography;

import com.iluwatar.choreography.events.DeliveryFailureEvent;

public interface SagaService {
    void onSagaFailure(DeliveryFailureEvent failureEvent);
}
