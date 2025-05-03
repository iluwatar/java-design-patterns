package com.iluwatar.daofactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DAOFactoryTest {

  @Test
  void verifyH2CustomerDAOCreation() {
    var daoFactory = DAOFactory.getDataSource(DataSourceType.H2);
    var customerDAO = daoFactory.createCustomerDAO();
    assertTrue(customerDAO instanceof H2CustomerDAO);
  }

  @Test
  void verifyMongoCustomerDAOCreation() {
    var daoFactory = DAOFactory.getDataSource(DataSourceType.Mongo);
    var customerDAO = daoFactory.createCustomerDAO();
    assertTrue(customerDAO instanceof MongoCustomerDAO);
  }

  @Test
  void verifyFlatFileCustomerDAOCreation() {
    var daoFactory = DAOFactory.getDataSource(DataSourceType.FlatFile);
    var customerDAO = daoFactory.createCustomerDAO();
    assertTrue(customerDAO instanceof FlatFileCustomerDAO);
  }
}
