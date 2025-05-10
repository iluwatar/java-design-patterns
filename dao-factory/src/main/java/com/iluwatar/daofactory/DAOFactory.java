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
   * Retrieves a {@link CustomerDAO} implementation specific to the underlying data source..
   *
   * @return A data source-specific implementation of {@link CustomerDAO}
   */
  public abstract CustomerDAO createCustomerDAO();
}
