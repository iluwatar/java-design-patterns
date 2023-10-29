package com.iluwater.monolithic.architecture;

/**
 * Module interface defining the operations related to customers.
 */
public interface CustomerModuleInterface {

  /**
   * Path to the database file where customer data is stored.
   */
  String DB_FILE = "monolithic-architecture/etc/localdb.txt";

  /**
   * Retrieves the customer details for the given customer ID.
   *
   * @param customerId The ID of the customer whose details are to be fetched.
   * @return An array containing the customer details.
   * @throws Exception If any error occurs while fetching the data.
   */
  String[] getCustomerById(int customerId) throws Exception;

  /**
   * Updates the credit limit for the customer with the specified ID.
   *
   * @param customerId     The ID of the customer whose credit limit needs to be updated.
   * @param newCreditLimit The new credit limit value to be set for the customer.
   * @throws Exception If any error occurs while updating the credit limit.
   */
  void updateCreditLimit(int customerId, double newCreditLimit) throws Exception;

  /**
   * Creates a new customer with the specified initial credit limit.
   *
   * @param creditLimit The initial credit limit for the new customer.
   * @return The ID of the newly created customer.
   * @throws Exception If any error occurs while creating a new customer.
   */
  String newCustomer(double creditLimit) throws Exception;
}
