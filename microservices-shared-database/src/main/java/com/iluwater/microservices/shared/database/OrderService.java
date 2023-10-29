package com.iluwater.microservices.shared.database;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import lombok.Synchronized;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for order-related operations.
 */
@Service
public class OrderService implements OrderServiceInterface {

  /**
   * Retrieves all the orders associated with a given customer ID.
   *
   * @param customerId The ID of the customer whose orders are to be fetched.
   * @return An Optional containing a list of orders in String array format or an error message if not found.
   * @throws Exception If an error occurs during the operation.
   */
  @Synchronized
  private Optional<String[]> findOrderTotalByCustomerId(int customerId) throws Exception {
    var scanner = new Scanner(new File(DB_FILE));
    var out = new ArrayList<String>();
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      if (line.startsWith("ORDERS")) {
        if (!scanner.nextLine().isEmpty()) {
          while (scanner.hasNextLine() && !(line = scanner.nextLine()).isEmpty()) {
            var parts = line.split(", ");
            if (Integer.parseInt(parts[1]) == customerId) {
              out.addAll(List.of(parts));
            }
          }
        }
      }
    }
    return out.isEmpty() ? Optional.of(new String[]{"No order found for customer " + customerId + "."}) : Optional.of(out.toArray(new String[0]));
  }

  /**
   * Calculate the total amount of all orders for a customer.
   *
   * @param orders A String array of orders.
   * @return The total amount.
   */
  private double calculateAllOrders(String[] orders) {
    int index = 3;
    var out = 0;
    while (index < orders.length) {
      out += Double.parseDouble(orders[index]);
      index += 4;
    }
    return out;
  }

  /**
   * Finds the customer's credit limit in the local database file by their customer ID.
   *
   * @param customerId The ID of the customer to find.
   * @return A positive double value of the customer credit limit if found, otherwise -1.
   * @throws Exception If there's an error during data retrieval.
   */
  @Synchronized
  private double findCreditLimitByCustomerId(int customerId) throws Exception {
    var file = new File(DB_FILE);
    var scanner = new Scanner(file);

    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      if (line.startsWith("CUSTOMERS")) {
        if (!scanner.nextLine().isEmpty()) {
          while (scanner.hasNextLine() && !(line = scanner.nextLine()).isEmpty()) {
            var parts = line.split(", ");
            if (Integer.parseInt(parts[0]) == customerId) {
              return Integer.parseInt(parts[1]);
            }
          }
        }
      }
    }
    return -1;
  }

  /**
   * Creates an order for the given customer ID and amount.
   *
   * @param customerId The ID of the customer making the order.
   * @param amount     The amount of the order.
   * @return The ID of the new order or an error message.
   * @throws Exception If an error occurs during the operation.
   */
  @Synchronized
  private String createOrder(int customerId, double amount) throws Exception {
    var newOrderId = -1;
    var creditLimit = findCreditLimitByCustomerId(customerId);
    var orders = findOrderTotalByCustomerId(customerId);


    if (creditLimit != -1) {
      if (orders.isPresent() && orders.get().length > 1) {
        var orderTotal = calculateAllOrders(orders.get());

        if (orderTotal + amount <= creditLimit) {
          newOrderId = insertOrder(new ArrayList<>(List.of(
              String.valueOf(customerId),
              "ACCEPTED",
              String.valueOf(amount)
          )));
        } else {
          throw new Exception("Exceed the CREDIT_LIMIT.");
        }
      } else {
        throw new Exception("Order for " + customerId + " not found.");
      }
    } else {
      throw new Exception("Customer not found.");
    }
    return newOrderId == -1 ? "New order created failed." : String.valueOf(newOrderId);
  }

  /**
   * Inserts a new order into the database.
   *
   * @param order A list containing order details.
   * @return The ID of the inserted order.
   * @throws Exception If an error occurs during the operation.
   */
  @Synchronized
  private int insertOrder(List<String> order) throws Exception {
    int orderId = -1;
    var path = Paths.get(DB_FILE);
    var lines = new ArrayList<>(Files.readAllLines(path));
    var index = lines.indexOf("ORDERS") + 2;
    while (index < lines.size()) {
      var parts = lines.get(index).split(", ");
      orderId = Integer.parseInt(parts[0]);
      index++;
    }
    orderId++;
    order.add(0, String.valueOf(orderId));
    lines.add(index, String.join(", ", order));
    Files.write(path, lines);
    return orderId;
  }

  /**
   * Public method to initiate order creation.
   *
   * @param customerId The ID of the customer making the order.
   * @param amount     The amount of the order.
   * @return The ID of the new order or an error message.
   * @throws Exception If an error occurs during the operation.
   */
  @Override
  public String makeOrder(int customerId, double amount) throws Exception {
    return createOrder(customerId, amount);
  }

  /**
   * Public method to retrieve all the orders for a given customer.
   *
   * @param customerId The ID of the customer whose orders are to be fetched.
   * @return An Optional containing a list of orders in String array format or an error message if not found.
   * @throws Exception If an error occurs during the operation.
   */
  @Override
  public Optional<String[]> getOrderTotalByCustomerId(int customerId) throws Exception {
    return findOrderTotalByCustomerId(customerId);
  }
}
