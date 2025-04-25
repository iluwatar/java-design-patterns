package com.iluwatar.daofactory;

/**
 * Created by: IntelliJ IDEA
 * User      : dthanh
 * Date      : 16/04/2025
 * Time      : 23:19
 * Filename  : FlatFileDataSourceFactory
 */
public class FlatFileDataSourceFactory extends DAOFactory {
  @Override
  public CustomerDAO getCustomerDAO() {
    return new FlatFileCustomerDAO();
  }
}
