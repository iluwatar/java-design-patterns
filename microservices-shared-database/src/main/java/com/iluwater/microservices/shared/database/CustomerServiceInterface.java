package com.iluwater.microservices.shared.database;

import java.util.Optional;

/**
 * Service interface defining the operations related to customers.
 */
public interface CustomerServiceInterface {

  /**
   * The path to the database file.
   */
  String DB_FILE = "microservice-shared-database/etc/localdb.txt";

  /**
   * Retrieves customer details by their ID.
   *
   * @param customerId The unique ID of the customer to retrieve.
   * @return An Optional containing the customer details as a String array, otherwise an error message.
   * @throws Exception If any error occurs during the operation.
   */
  Optional<String[]> getCustomerById(int customerId) throws Exception;

  /**
   * Updates the credit limit of a specific customer.
   *
   * @param customerId     The unique ID of the customer whose credit limit is to be updated.
   * @param newCreditLimit The new credit limit for the customer.
   * @throws Exception If any error occurs during the operation.
   */
  void updateCreditLimit(int customerId, double newCreditLimit) throws Exception;

  /**
   * Creates a new customer with the specified credit limit.
   *
   * @param creditLimit The credit limit for the new customer.
   * @return The unique ID of the newly created customer.
   * @throws Exception If any error occurs during the operation.
   */
  String newCustomer(double creditLimit) throws Exception;
}
