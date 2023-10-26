package com.iluwater.microservice.shared.database;

import java.util.Optional;

public interface IOrderService {
    String makeOrder(int customerId, double total) throws Exception;
    Optional<String[]> getOrderTotalByCustomerId(int customerId);
}
