package com.iluwater.microservice.shared.database;

import java.util.Optional;

/**
 * Service interface defining the operations related to orders.
 */
public interface IOrderService {

    /**
     * Create a new order for a given customer with a specified total.
     *
     * @param customerId The ID of the customer making the order.
     * @param total The total amount for the order.
     * @return The ID of the newly created order.
     * @throws Exception If there's an error during order creation.
     */
    String makeOrder(int customerId, double total) throws Exception;

    /**
     * Fetches the total of all orders made by a specific customer.
     *
     * @param customerId The ID of the customer whose order totals are to be fetched.
     * @return An Optional containing the order totals in String array format.
     * @throws Exception If there's an error during data retrieval.
     */
    Optional<String[]> getOrderTotalByCustomerId(int customerId) throws Exception;
}
