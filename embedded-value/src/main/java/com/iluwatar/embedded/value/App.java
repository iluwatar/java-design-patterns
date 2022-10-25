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
package com.iluwatar.embedded.value;

import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/* 
 * Many small objects make sense in an OO system that don’t make sense as
 * tables in a database. Examples include currency-aware money objects (amount, currency) and date
 * ranges. Although the default thinking is to save an object as a table, no sane
 * person would want a table of money values.
 * 
 * An Embedded Value maps the values of an object to fields in the record of
 * the object’s owner. In this implementation we have an Order object with links to an
 * ShippingAddress object. In the resulting table the fields in the ShippingAddress
 * object map to fields in the Order table rather than make new records
 * themselves.  
 */

@Slf4j
public class App {
                                      
  /**
   * Program entry point.
   *
   * @param args command line args.
   * @throws Exception if any error occurs.
   * 
   */
  public static void main(String[] args) throws Exception {
    final var dataSource = new DataSource();

    final var order1 = new Order("JBL headphone", "Ram", new ShippingAddress("Bangalore", "Karnataka", "560040"));
    final var order2 = new Order("MacBook Pro", "Manjunath",
        new ShippingAddress("Bangalore", "Karnataka", "581204"));
    final var order3 = new Order("Carrie Soto is Back", "Shiva",
        new ShippingAddress("Bangalore", "Karnataka", "560004"));
    
        if (dataSource.createSchema()) {
      LOGGER.info("TABLE CREATED");
      LOGGER.info("Table \"Orders\" schema:\n" + dataSource.getSchema());
    } else {
      LOGGER.error("Error creating table");
      System.exit(0);
    }
    LOGGER.info("Orders Query: {}", dataSource.queryOrders().collect(Collectors.toList()));
    dataSource.insertOrder(order1);
    dataSource.insertOrder(order2);
    dataSource.insertOrder(order3);
    LOGGER.info("Orders Query: {}", dataSource.queryOrders().collect(Collectors.toList()));
    LOGGER.info("Query Order with id=2: {}", dataSource.queryOrder(2));
    LOGGER.info("Remove Order with id=1");
    dataSource.removeOrder(1);
    LOGGER.info("Orders Query: {}", dataSource.queryOrders().collect(Collectors.toList()));
    if (dataSource.deleteSchema()) {
      LOGGER.info("TABLE DROPPED");
    } else {
      LOGGER.error("Error deleting table");
    }
  }
}
