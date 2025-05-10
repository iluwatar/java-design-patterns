/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.daofactory;

/**
 * {@code DAOFactoryProvider} is a utility class responsible for providing concrete implementations
 * of the {@link DAOFactory} interface based on the specified data source type.
 *
 * <p>This class acts as an entry point to obtain DAO factories for different storage mechanisms
 * such as relational databases (e.g., H2), document stores (e.g., MongoDB), or file-based systems.
 * It uses the {@link DataSourceType} enumeration to determine which concrete factory to
 * instantiate.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * DAOFactory factory = DAOFactoryProvider.getDataSource(DataSourceType.H2);
 * }</pre>
 */
public class DAOFactoryProvider {

  private DAOFactoryProvider() {}

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
      case MONGO -> new MongoDataSourceFactory();
      case FLAT_FILE -> new FlatFileDataSourceFactory();
    };
  }
}
