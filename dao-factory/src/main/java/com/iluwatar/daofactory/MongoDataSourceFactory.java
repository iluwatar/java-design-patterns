package com.iluwatar.daofactory;

/**
 * Created by: IntelliJ IDEA
 * User      : dthanh
 * Date      : 16/04/2025
 * Time      : 23:19
 * Filename  : NitriteDataSourceFactory
 */
public class MongoDataSourceFactory extends DAOFactory {
  @Override
  public CustomerDAO getCustomerDAO() {
    return new MongoCustomerDAO();
  }
}
