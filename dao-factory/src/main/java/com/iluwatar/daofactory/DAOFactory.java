package com.iluwatar.daofactory;

/**
 * An abstract factory class that provides a way to create concrete DAO (Data Access Object)
 * factories for different data sources types (e.g., H2, Mongo, FlatFile).
 *
 * <p>This class follows the Abstract Factory design pattern, allowing applications to retrieve the
 * approriate DAO implementation without being tightly coupled to a specific data source.
 *
 * @see H2DataSourceFactory
 * @see MongoDataSourceFactory
 * @see FlatFileDataSourceFactory
 */
public abstract class DAOFactory {
  /**
   * Returns a concrete {@link DAOFactory} intance based on the specified data source type.
   *
   * @param dataSourceType The type of data source for which a factory is needed. Supported values:
   *     {@code H2}, {@code Mongo}, {@code FlatFile}
   * @return A {@link DAOFactory} implementation corresponding to the given data source type.
   * @throws IllegalArgumentException if the given data source type is not supported.
   */
  public static DAOFactory getDataSource(DataSourceType dataSourceType) {
    return switch (dataSourceType) {
      case H2 -> new H2DataSourceFactory();
      case Mongo -> new MongoDataSourceFactory();
      case FlatFile -> new FlatFileDataSourceFactory();
    };
  }

  /**
   * Retrieves a {@link CustomerDAO} implementation specific to the underlying data source..
   *
   * @return A data source-specific implementation of {@link CustomerDAO}
   */
  public abstract CustomerDAO createCustomerDAO();
}
