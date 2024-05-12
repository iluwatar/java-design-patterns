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

import java.sql.SQLException;
import java.util.stream.Stream;

/*
 * Abstract class which contains the required SQL queries and basic methods declaration.
 * 
 * The main thing to consider is that the ShippingAddress object doesn't have it's own class
 * but it's values are stored into the Orders table as city, state, pincode
 * 
 */
interface DataSourceInterface {
  
  String JDBC_URL = "jdbc:h2:mem:Embedded-Value";
  
  String CREATE_SCHEMA = "CREATE TABLE Orders (Id INT AUTO_INCREMENT, item VARCHAR(50) NOT NULL, orderedBy VARCHAR(50)"
      + ", city VARCHAR(50), state VARCHAR(50), pincode CHAR(6) NOT NULL, PRIMARY KEY(Id))";
  
  String GET_SCHEMA = "SHOW COLUMNS FROM Orders";
  
  String INSERT_ORDER = "INSERT INTO Orders (item, orderedBy, city, state, pincode) VALUES(?, ?, ?, ?, ?)";
  
  String QUERY_ORDERS = "SELECT * FROM Orders";
  
  String QUERY_ORDER = QUERY_ORDERS + " WHERE Id = ?";
  
  String REMOVE_ORDER = "DELETE FROM Orders WHERE Id = ?";
  
  String DELETE_SCHEMA = "DROP TABLE Orders";
  
  boolean createSchema() throws SQLException;
  
  String getSchema() throws SQLException;
  
  boolean insertOrder(Order order) throws SQLException;
  
  Stream<Order> queryOrders() throws SQLException;
  
  Order queryOrder(int id) throws SQLException;
  
  void removeOrder(int id) throws Exception;
  
  boolean deleteSchema() throws Exception;
}
