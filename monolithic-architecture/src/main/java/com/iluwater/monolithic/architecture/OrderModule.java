package com.iluwater.monolithic.architecture;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import lombok.Synchronized;

/**
 * Module class responsible for order-related operations.
 */
public class OrderModule implements OrderModuleInterface {

  /**
   * Retrieves all the orders associated with a given customer ID.
   *
   * @param customerId The ID of the customer whose orders are to be fetched.
   * @return An array of string containing a list of orders format or an error message if not found.
   * @throws Exception If an error occurs during the operation.
   */
  @Synchronized
  private String[] findOrderTotalByCustomerId(int customerId) throws Exception {

    var path = Paths.get(DB_FILE);
    var lines = Files.readAllLines(path);
    var out = new ArrayList<String>();

    boolean orderSectionStarted = false;
    boolean infoStart = false;

    for (String line : lines) {
      if (line.startsWith("ORDERS")) {
        orderSectionStarted = true;
        continue;
      }

      if ((orderSectionStarted || infoStart) && line.isEmpty()) {
        break; // end of ORDERS section
      }

      if (orderSectionStarted) {
        infoStart = true;
        orderSectionStarted = false;
        continue;
      }

      if (infoStart) {
        var parts = line.split(", ");
        if (Integer.parseInt(parts[1]) == customerId) {
          out.addAll(List.of(parts));
        }
      }
    }

    return out.isEmpty() ? new String[]{"No order found for customer " + customerId + "."} : out.toArray(new String[0]);

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
    var customer = customerModule.getCustomerById(customerId);

    if (customer.length == 1) {
      throw new Exception(customer[0]);
    }

    var creditLimit = Double.parseDouble(customer[1]);
    var orders = findOrderTotalByCustomerId(customerId);


    if (creditLimit != -1) {

      var orderTotal = orders.length == 1 ? 0 : calculateAllOrders(orders);

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
  public String[] getOrderTotalByCustomerId(int customerId) throws Exception {
    return findOrderTotalByCustomerId(customerId);
  }
}
