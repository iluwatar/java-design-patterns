package com.iluwater.microservice.shared.database;

import java.util.Optional;

public interface ICustomerService {
    Optional<String[]> getCustomerById(int customerId) throws Exception;
    void updateCreditLimit(int customerId, double newCreditLimit) throws Exception;
    String newCustomer(double creditLimit) throws Exception;
}

