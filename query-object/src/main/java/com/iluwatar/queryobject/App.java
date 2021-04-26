package com.iluwatar.queryobject;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This application is an usecase of Query Object Pattern. In this scenario, a data source with 2
 * customers is instantiated. The user then performs 2 types of queries using the query objects
 * provided along with the data source. The method provided along with the data source
 * accepts one or more query objects and transform them into inner query logic (In reality,
 * it is usually implemented with a complicated SQL statement with WHERE, JOIN and many
 * other conditions) which will normally not be concerned by the user.
 */

public class App {
  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * The program entry point.
   *
   * @param args The command line argument. In this example it is not used.
   */
  public static void main(String[] args) {

    Dataset<Customer> customerDataset = new Dataset(
            Arrays.asList(
                    new Customer(
                            "Customer 1",
                            Arrays.asList(
                                    new CustomerOrder(
                                            Collections.singletonList(
                                                    new CustomerOrderDetail(1, 100)
                                            )
                                    ),
                                    new CustomerOrder(
                                            Arrays.asList(
                                                    new CustomerOrderDetail(3, 10),
                                                    new CustomerOrderDetail(4, 20)
                                            )
                                    )
                            )
                    ),
                    new Customer(
                            "Customer 2",
                            Arrays.asList(
                                    new CustomerOrder(
                                            Collections.singletonList(
                                                    new CustomerOrderDetail(1, 100)
                                            )
                                    ),
                                    new CustomerOrder(
                                            Arrays.asList(
                                                    new CustomerOrderDetail(5, 10),
                                                    new CustomerOrderDetail(2, 20)
                                            )
                                    )
                            )
                    )
            )
    );

    LOGGER.info("Created dataset with 2 customers.");

    Repository<Customer> customerRepository = new Repository<>(customerDataset.entities);
    double purchaseAmount = 200;
    QueryObject<Customer> queryObject = new CustomerSalesWithPurchaseMoreThan(purchaseAmount);

    LOGGER.info(
            String.format(
                    "Created query to find all the customers that have spent more than %f.",
                    purchaseAmount));

    Collection<Customer> resultCustomers = customerRepository.query(queryObject);
    for (var result : resultCustomers) {
      LOGGER.info(
              String.format(
                      "%s has more than %f purchase amount!",
              result.name, purchaseAmount));
    }

    var orderNumber = 1;
    queryObject = new CustomersWithOrdersAmountMoreThan(orderNumber);

    LOGGER.info(
            String.format(
                    "Created query to find all the customers that have made more than %d orders.",
            orderNumber));

    resultCustomers = customerRepository.query(queryObject);
    for (var results : resultCustomers) {
      LOGGER.info(
              String.format(
                      "%s has more than %d orders!",
                      results.name, orderNumber));
    }
  }
}
