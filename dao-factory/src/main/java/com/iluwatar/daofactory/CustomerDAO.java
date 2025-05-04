package com.iluwatar.daofactory;

import java.util.List;
import java.util.Optional;

/**
 * The Data Access Object (DAO) pattern provides an abstraction layer between the application and
 * the database. It encapsulates data access logic, allowing the application to work with domain
 * objects instead of direct database operations.
 *
 * <p>Implementations handle specific storage mechanisms (e.g., in-memory, databases) while keeping
 * client code unchanged.
 *
 * @see H2CustomerDAO
 * @see MongoCustomerDAO
 * @see FlatFileCustomerDAO
 */
public interface CustomerDAO<T> {
  /**
   * Persist the given customer
   *
   * @param customer the customer to persist
   */
  void save(Customer<T> customer);

  /**
   * Update the given customer
   *
   * @param customer the customer to update
   */
  void update(Customer<T> customer);

  /**
   * Delete the customer with the given id
   *
   * @param id the id of the customer to delete
   */
  void delete(T id);

  /**
   * Find all customers
   *
   * @return a list of customers
   */
  List<Customer<T>> findAll();

  /**
   * Find the customer with the given id
   *
   * @param id the id of the customer to find
   * @return the customer with the given id
   */
  Optional<Customer<T>> findById(T id);

  /**
   * Delete the customer schema. After executing the statements, this function will be called to
   * clean up the data and delete the records.
   */
  void deleteSchema();
}
