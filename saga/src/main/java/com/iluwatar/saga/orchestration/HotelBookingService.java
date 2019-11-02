package com.iluwatar.saga.orchestration;

public class HotelBookingService extends Service<String> {
    @Override
    public String getName() {
        return "booking a Hotel";
    }
}
