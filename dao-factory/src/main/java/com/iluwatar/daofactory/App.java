package com.iluwatar.daofactory;

import java.sql.SQLException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by: IntelliJ IDEA
 * User      : dthanh
 * Date      : 16/04/2025
 * Time      : 23:08
 * Filename  : ${NAME}
 */
@Slf4j
public class App {

  private static final Logger logger = LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);



  public static void main(String[] args) throws SQLException {
    final var h2DAO = DAOFactory.getDataSource(DataSourceEnum.H2);
    final var mongoDAO = DAOFactory.getDataSource(DataSourceEnum.Mongo);
    final var flatFileDAO = DAOFactory.getDataSource(DataSourceEnum.FlatFile);

    final var h2CustomerDAO = h2DAO.getCustomerDAO();
    final var mongoCustomerDAO = mongoDAO.getCustomerDAO();
    final var flatFileCustomerDAO = flatFileDAO.getCustomerDAO();

    // Perform CRUD H2 Database
    Customer<Long> customerInmemory1 = new Customer<>(1L, "Green");
    Customer<Long> customerInmemory2 = new Customer<>(2L, "Red");
    Customer<Long> customerInmemory3 = new Customer<>(3L, "Blue");
    Customer<Long> customerUpdateInmemory = new Customer<>(1L, "Yellow");

    LOGGER.debug("H2 - Create customer");
    performCreateCustomer(h2CustomerDAO,
        List.of(customerInmemory1, customerInmemory2, customerInmemory3));
    LOGGER.debug("H2 - Update customer");
    performUpdateCustomer(h2CustomerDAO, customerUpdateInmemory);
    LOGGER.debug("H2 - Delete customer");
    performDeleteCustomer(h2CustomerDAO, 3L);
    deleteSchema(h2CustomerDAO);

    // Perform CRUD MongoDb
    ObjectId idCustomerMongo1 = new ObjectId();
    ObjectId idCustomerMongo2 = new ObjectId();
    Customer<ObjectId> customer4 = new Customer<>(idCustomerMongo1, "Masca");
    Customer<ObjectId> customer5 = new Customer<>(idCustomerMongo2, "Elliot");
    Customer<ObjectId> customerUpdateMongo = new Customer<>(idCustomerMongo2, "Henry");

    LOGGER.debug("Mongo - Create customer");
    performCreateCustomer(mongoCustomerDAO, List.of(customer4, customer5));
    LOGGER.debug("Mongo - Update customer");
    performUpdateCustomer(mongoCustomerDAO, customerUpdateMongo);
    LOGGER.debug("Mongo - Delete customer");
    performDeleteCustomer(mongoCustomerDAO, idCustomerMongo2);
    deleteSchema(mongoCustomerDAO);

    // Perform CRUD Flat file
    Customer<Long> customerFlatFile1 = new Customer<>(1L, "Duc");
    Customer<Long> customerFlatFile2 = new Customer<>(2L, "Quang");
    Customer<Long> customerFlatFile3 = new Customer<>(3L, "Nhat");
    Customer<Long> customerUpdateFlatFile = new Customer<>(1L, "Thanh");
    LOGGER.debug("Flat file - Create customer");
    performCreateCustomer(flatFileCustomerDAO,
        List.of(customerFlatFile1, customerFlatFile2, customerFlatFile3));
    LOGGER.debug("Flat file - Update customer");
    performUpdateCustomer(flatFileCustomerDAO, customerUpdateFlatFile);
    LOGGER.debug("Flat file - Delete customer");
    performDeleteCustomer(flatFileCustomerDAO, 3L);
    deleteSchema(flatFileCustomerDAO);
  }

  public static void deleteSchema(CustomerDAO<?> customerDAO) {
    customerDAO.deleteSchema();
  }

  public static <ID> void performCreateCustomer(CustomerDAO<ID> customerDAO,
                                                List<Customer<ID>> customerList) {
    for (Customer<ID> customer : customerList) {
      customerDAO.save(customer);
    }
    List<Customer<ID>> customers = customerDAO.findAll();
    for (Customer<ID> customer : customers) {
      LOGGER.debug(customer.toString());
    }
  }

  public static <ID> void performUpdateCustomer(CustomerDAO<ID> customerDAO,
                                                Customer<ID> customerUpdate) {
    customerDAO.update(customerUpdate);
    List<Customer<ID>> customers = customerDAO.findAll();
    for (Customer<ID> customer : customers) {
      LOGGER.error(customer.toString());
    }
  }

  public static <ID> void performDeleteCustomer(CustomerDAO<ID> customerDAO,
                                                ID customerId) {
    customerDAO.delete(customerId);
    List<Customer<ID>> customers = customerDAO.findAll();
    for (Customer<ID> customer : customers) {
      LOGGER.debug(customer.toString());
    }
  }
}