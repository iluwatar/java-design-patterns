package com.iluwatar.daofactory;

/**
 * Created by: IntelliJ IDEA
 * User      : dthanh
 * Date      : 16/04/2025
 * Time      : 23:16
 * Filename  : DAOFactory
 */
public abstract class DAOFactory {
  public static DAOFactory getDataSource(DataSourceEnum dataSourceEnum) {
    return switch (dataSourceEnum) {
      case H2 -> new H2DataSourceFactory();
      case Mongo -> new MongoDataSourceFactory();
      case FlatFile -> new FlatFileDataSourceFactory();
    };
  }

  public abstract CustomerDAO getCustomerDAO();
}
