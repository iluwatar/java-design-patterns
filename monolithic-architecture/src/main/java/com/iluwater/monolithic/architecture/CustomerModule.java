package com.iluwater.monolithic.architecture;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import lombok.Synchronized;

/**
 * Module class for managing customer-related operations.
 */

public class CustomerModule implements CustomerModuleInterface {


  /**
   * Finds a customer in the local database file by their customer ID.
   *
   * @param customerId The ID of the customer to find.
   * @return An array of string containing the customer data if found, otherwise an error message.
   * @throws Exception If there's an error during data retrieval.
   */
  @Synchronized
  private String[] findCustomerById(int customerId) throws Exception {
    var path = Paths.get(DB_FILE);
    var lines = Files.readAllLines(path);

    boolean customerSectionStarted = false;
    boolean infoStart = false;

    for (String line : lines) {
      if (line.startsWith("CUSTOMERS")) {
        customerSectionStarted = true;
        continue;
      }

      if ((customerSectionStarted || infoStart) && line.isEmpty()) {
        break; // end of CUSTOMERS section
      }

      if (customerSectionStarted) {
        infoStart = true;
        customerSectionStarted = false;
        continue;
      }

      if (infoStart) {
        var parts = line.split(", ");
        if (Integer.parseInt(parts[0]) == customerId) {
          return parts;
        }
      }
    }

    return new String[]{"Error getting data of " + customerId + " : customerID not found"};
  }


  /**
   * Updates the credit limit for a given customer.
   *
   * @param customerId     The ID of the customer.
   * @param newCreditLimit The new credit limit for the customer.
   * @throws Exception If there's an error during data update.
   */
  @Synchronized
  private void setCreditLimit(int customerId, double newCreditLimit) throws Exception {
    var path = Paths.get(DB_FILE);
    var lines = new ArrayList<>(Files.readAllLines(path));
    for (int i = 0; i < lines.size(); i++) {
      if (lines.get(i).startsWith("CUSTOMERS")) {
        i += 2;
        while (i < lines.size() && !lines.get(i).isEmpty()) {
          var parts = lines.get(i).split(", ");
          if (Integer.parseInt(parts[0]) == customerId) {
            parts[1] = String.valueOf(newCreditLimit);
            lines.set(i, String.join(", ", parts));
            Files.write(path, lines);
            return;
          }
          i++;
        }
      }
    }
    throw new Exception("Customer not found.");
  }

  /**
   * Creates a new customer with a given credit limit.
   *
   * @param creditLimit The credit limit for the new customer.
   * @return The ID of the newly created customer.
   * @throws Exception If there's an error during customer creation.
   */
  @Synchronized
  private String createCustomer(double creditLimit) throws Exception {
    int newCustomerId = -1;
    var path = Paths.get(DB_FILE);
    var lines = new ArrayList<>(Files.readAllLines(path));
    var index = lines.indexOf("CUSTOMERS") + 2;
    while (!lines.get(index).isEmpty()) {
      var parts = lines.get(index).split(", ");
      newCustomerId = Integer.parseInt(parts[0]);
      index++;
    }
    newCustomerId++;
    var customerData = String.join(", ", String.valueOf(newCustomerId), String.valueOf(creditLimit));
    lines.add(index, customerData);
    Files.write(path, lines);

    return newCustomerId == -1 ? "New customer created failed." : String.valueOf(newCustomerId);
  }

  /**
   * Retrieves customer details by their ID.
   *
   * @param customerId The ID of the customer to retrieve.
   * @return An array of sting containing the customer details if found, otherwise an error message.
   * @throws Exception If there's an error during data retrieval.
   */
  @Override
  public String[] getCustomerById(int customerId) throws Exception {
    return findCustomerById(customerId);
  }

  /**
   * Updates the credit limit for a specified customer.
   *
   * @param customerId     The ID of the customer to update.
   * @param newCreditLimit The new credit limit for the customer.
   * @throws Exception If there's an error during data update.
   */
  @Override
  public void updateCreditLimit(int customerId, double newCreditLimit) throws Exception {
    setCreditLimit(customerId, newCreditLimit);
  }

  /**
   * Creates a new customer and assigns them a credit limit.
   *
   * @param creditLimit The credit limit for the new customer.
   * @return The ID of the newly created customer.
   * @throws Exception If there's an error during customer creation.
   */
  @Override
  public String newCustomer(double creditLimit) throws Exception {
    return createCustomer(creditLimit);
  }
}
