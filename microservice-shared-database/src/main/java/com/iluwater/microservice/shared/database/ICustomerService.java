package com.iluwater.microservice.shared.database;

import java.util.Optional;

public interface ICustomerService {
    Optional<String[]> getCustomerById(int customerId);
    void updateCreditLimit(int customerId, double newCreditLimit);
    String newCustomer(double creditLimit);
}

