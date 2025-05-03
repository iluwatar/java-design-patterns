package com.iluwatar.daofactory;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

@Slf4j
public class App {


  public static void main(String[] args) {
    var daoFactory = DAOFactory.getDataSource(DataSourceType.H2);
    var customerDAO = daoFactory.createCustomerDAO();

    // Perform CRUD H2 Database
    if (customerDAO instanceof H2CustomerDAO h2CustomerDAO) {
      h2CustomerDAO.deleteSchema();
      h2CustomerDAO.createSchema();
    }
    Customer<Long> customerInmemory1 = new Customer<>(1L, "Green");
    Customer<Long> customerInmemory2 = new Customer<>(2L, "Red");
    Customer<Long> customerInmemory3 = new Customer<>(3L, "Blue");
    Customer<Long> customerUpdateInmemory = new Customer<>(1L, "Yellow");

    LOGGER.debug("H2 - Create customer");
    performCreateCustomer(customerDAO,
        List.of(customerInmemory1, customerInmemory2, customerInmemory3));
    LOGGER.debug("H2 - Update customer");
    performUpdateCustomer(customerDAO, customerUpdateInmemory);
    LOGGER.debug("H2 - Delete customer");
    performDeleteCustomer(customerDAO, 3L);
    deleteSchema(customerDAO);

    // Perform CRUD MongoDb
    daoFactory = DAOFactory.getDataSource(DataSourceType.Mongo);
    customerDAO = daoFactory.createCustomerDAO();
    ObjectId idCustomerMongo1 = new ObjectId();
    ObjectId idCustomerMongo2 = new ObjectId();
    Customer<ObjectId> customer4 = new Customer<>(idCustomerMongo1, "Masca");
    Customer<ObjectId> customer5 = new Customer<>(idCustomerMongo2, "Elliot");
    Customer<ObjectId> customerUpdateMongo = new Customer<>(idCustomerMongo2, "Henry");

    LOGGER.debug("Mongo - Create customer");
    performCreateCustomer(customerDAO, List.of(customer4, customer5));
    LOGGER.debug("Mongo - Update customer");
    performUpdateCustomer(customerDAO, customerUpdateMongo);
    LOGGER.debug("Mongo - Delete customer");
    performDeleteCustomer(customerDAO, idCustomerMongo2);
    deleteSchema(customerDAO);

    // Perform CRUD Flat file
    daoFactory = DAOFactory.getDataSource(DataSourceType.FlatFile);
    customerDAO = daoFactory.createCustomerDAO();
    Customer<Long> customerFlatFile1 = new Customer<>(1L, "Duc");
    Customer<Long> customerFlatFile2 = new Customer<>(2L, "Quang");
    Customer<Long> customerFlatFile3 = new Customer<>(3L, "Nhat");
    Customer<Long> customerUpdateFlatFile = new Customer<>(1L, "Thanh");
    LOGGER.debug("Flat file - Create customer");
    performCreateCustomer(customerDAO,
        List.of(customerFlatFile1, customerFlatFile2, customerFlatFile3));
    LOGGER.debug("Flat file - Update customer");
    performUpdateCustomer(customerDAO, customerUpdateFlatFile);
    LOGGER.debug("Flat file - Delete customer");
    performDeleteCustomer(customerDAO, 3L);
    deleteSchema(customerDAO);
  }

  public static void deleteSchema(CustomerDAO<?> customerDAO) {
    customerDAO.deleteSchema();
  }

  public static <T> void performCreateCustomer(CustomerDAO<T> customerDAO,
                                               List<Customer<T>> customerList) {
    for (Customer<T> customer : customerList) {
      customerDAO.save(customer);
    }
    List<Customer<T>> customers = customerDAO.findAll();
    for (Customer<T> customer : customers) {
      LOGGER.debug(customer.toString());
    }
  }

  public static <T> void performUpdateCustomer(CustomerDAO<T> customerDAO,
                                               Customer<T> customerUpdate) {
    customerDAO.update(customerUpdate);
    List<Customer<T>> customers = customerDAO.findAll();
    for (Customer<T> customer : customers) {
      LOGGER.debug(customer.toString());
    }
  }

  public static <T> void performDeleteCustomer(CustomerDAO<T> customerDAO,
                                               T customerId) {
    customerDAO.delete(customerId);
    List<Customer<T>> customers = customerDAO.findAll();
    for (Customer<T> customer : customers) {
      LOGGER.debug(customer.toString());
    }
  }
}