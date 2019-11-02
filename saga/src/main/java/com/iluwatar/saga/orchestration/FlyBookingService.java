package com.iluwatar.saga.orchestration;

public class FlyBookingService extends Service<String> {
    @Override
    public String getName() {
        return "booking a Fly";
    }
}
