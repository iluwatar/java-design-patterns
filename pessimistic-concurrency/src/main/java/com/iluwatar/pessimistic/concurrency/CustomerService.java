package com.iluwatar.pessimistic.concurrency;

import java.util.Optional;

import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;

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

    public Optional<Customer> startEditingCustomerInfo(String editingUser, Customer customer) throws LockingException {
        if (lockManager.requestLock(editingUser, customer)) {
            return customerDao.get(customer.getId());
        } else {
            throw new LockingException("Row locked.");
        }
    }

    public void updateCustomerInfo(String editingUser, Customer customer, String newName) throws LockingException {
        Customer tmp = new Customer(newName);
        String[] params = new String[1];
        params[0] = String.valueOf(customer.getId());
        if (customerDao.verifyLock(customer, editingUser)) {
            customerDao.update(tmp, params);
            lockManager.releaseLock(customer);
        } else {
            throw new LockingException("Permission not granted.");
        }
//        System.out.println(customer.isLocked());
    }

}
