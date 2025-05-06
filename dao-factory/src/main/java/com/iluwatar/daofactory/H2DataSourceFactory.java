package com.iluwatar.daofactory;

import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;

/** H2DataSourceFactory concrete factory. */
public class H2DataSourceFactory extends DAOFactory {
  private final String DB_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
  private final String USER = "sa";
  private final String PASS = "";

  @Override
  public CustomerDAO createCustomerDAO() {
    return new H2CustomerDAO(createDataSource());
  }

  private DataSource createDataSource() {
    var dataSource = new JdbcDataSource();
    dataSource.setURL(DB_URL);
    dataSource.setUser(USER);
    dataSource.setPassword(PASS);
    return dataSource;
  }
}
