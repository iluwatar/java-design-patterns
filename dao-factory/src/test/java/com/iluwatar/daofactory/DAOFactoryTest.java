package com.iluwatar.daofactory;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;

/** {@link DAOFactory} */
class DAOFactoryTest {

  @Test
  void verifyH2CustomerDAOCreation() {
    var daoFactory = DAOFactory.getDataSource(DataSourceType.H2);
    var customerDAO = daoFactory.createCustomerDAO();
    assertInstanceOf(H2CustomerDAO.class, customerDAO);
  }

  @Test
  void verifyMongoCustomerDAOCreation() {
    var daoFactory = DAOFactory.getDataSource(DataSourceType.Mongo);
    var customerDAO = daoFactory.createCustomerDAO();
    assertInstanceOf(MongoCustomerDAO.class, customerDAO);
  }

  @Test
  void verifyFlatFileCustomerDAOCreation() {
    var daoFactory = DAOFactory.getDataSource(DataSourceType.FlatFile);
    var customerDAO = daoFactory.createCustomerDAO();
    assertInstanceOf(FlatFileCustomerDAO.class, customerDAO);
  }
}
