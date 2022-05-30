package com.iluwatar.pessimistic.concurrency;

import java.util.Optional;

import javax.persistence.EntityManagerFactory;

public class CustomerService {
  private final CustomerDao customerDao;
  private LockManager lockManager;

  public CustomerService(final EntityManagerFactory emf) {
    this.customerDao = new CustomerDao(emf);
    this.lockManager = LockManager.getLockManager("CUSTOMER");
  }

  public void clearTable() {
    customerDao.deleteAll();
  }

  public void save(Customer customer) {
    customerDao.save(customer);
  }

  /**
   * Sends a request to the database to lock resource, it is ready for edit if data is returned.
   * @param editingUser user initiating the edit
   * @param customer customer object to be edited on
   * @return the data on the requested customer
   * @throws LockingException if row is already locked
   */
  public Optional<Customer> startEditingCustomerInfo(String editingUser, Customer customer)
      throws LockingException {
    if (lockManager.requestLock(editingUser, customer)) {
      return customerDao.get(customer.getId());
    } else {
      throw new LockingException("Row locked.");
    }
  }

  /**
   *  Updates the customer's data with new data if the editingUser is consistent with locking user.
   * @param editingUser user doing the edit
   * @param customer customer object to update
   * @param newName update information
   * @throws LockingException if editingUser is not consistent with locking user
   */
  public void updateCustomerInfo(String editingUser, Customer customer, String newName)
      throws LockingException {
    Customer tmp = new Customer(newName);
    String[] params = new String[1];
    params[0] = String.valueOf(customer.getId());
    if (customerDao.verifyLock(customer, editingUser)) {
      customerDao.update(tmp, params);
      lockManager.releaseLock(customer);
    } else {
      throw new LockingException("Permission not granted.");
    }
  }
}
