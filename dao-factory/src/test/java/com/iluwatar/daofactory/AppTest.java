package com.iluwatar.daofactory;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** {@link App} */
class AppTest {
  /** Test perform CRUD in main class */
  private CustomerDAO<Long> mockLongCustomerDAO;
  private CustomerDAO<ObjectId> mockObjectIdCustomerDAO;

  @BeforeEach
  void setUp() {
    mockLongCustomerDAO = mock(CustomerDAO.class);
    mockObjectIdCustomerDAO = mock(CustomerDAO.class);
  }

  @Test
  void testPerformCreateCustomerWithLongId() {
    Customer<Long> c1 = new Customer<>(1L, "Test1");
    Customer<Long> c2 = new Customer<>(2L, "Test2");

    when(mockLongCustomerDAO.findAll()).thenReturn(List.of(c1, c2));

    App.performCreateCustomer(mockLongCustomerDAO, List.of(c1, c2));

    verify(mockLongCustomerDAO).save(c1);
    verify(mockLongCustomerDAO).save(c2);
    verify(mockLongCustomerDAO).findAll();
  }

  @Test
  void testPerformUpdateCustomerWithObjectId() {
    ObjectId id = new ObjectId();
    Customer<ObjectId> updatedCustomer = new Customer<>(id, "Updated");

    when(mockObjectIdCustomerDAO.findAll()).thenReturn(List.of(updatedCustomer));

    App.performUpdateCustomer(mockObjectIdCustomerDAO, updatedCustomer);

    verify(mockObjectIdCustomerDAO).update(updatedCustomer);
    verify(mockObjectIdCustomerDAO).findAll();
  }

  @Test
  void testPerformDeleteCustomerWithLongId() {
    Long id = 100L;
    Customer<Long> remainingCustomer = new Customer<>(1L, "Remaining");

    when(mockLongCustomerDAO.findAll()).thenReturn(List.of(remainingCustomer));

    App.performDeleteCustomer(mockLongCustomerDAO, id);

    verify(mockLongCustomerDAO).delete(id);
    verify(mockLongCustomerDAO).findAll();
  }

  @Test
  void testDeleteSchema() {
    App.deleteSchema(mockLongCustomerDAO);
    verify(mockLongCustomerDAO).deleteSchema();
  }
}
