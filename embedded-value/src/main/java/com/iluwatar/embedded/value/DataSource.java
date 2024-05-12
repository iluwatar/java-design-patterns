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

import static java.sql.PreparedStatement.RETURN_GENERATED_KEYS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;



/**
 * Communicates with H2 database with the help of JDBC API.
 * Inherits the SQL queries and methods from @link AbstractDataSource class.
 */
@Slf4j
public class DataSource implements DataSourceInterface {
  private Connection conn;

  // Statements are objects which are used to execute queries which will not be repeated.
  private Statement getschema;
  private Statement deleteschema;
  private Statement queryOrders;

  // PreparedStatements are used to execute queries which will be repeated.
  private PreparedStatement insertIntoOrders;
  private PreparedStatement removeorder;
  private PreparedStatement queyOrderById;
  
  /**
   * {@summary Establish connection to database.
   * Constructor to create DataSource object.}   
   */
  public DataSource() {
    try {
      conn = DriverManager.getConnection(JDBC_URL);
      LOGGER.info("Connected to H2 in-memory database: " + conn.getCatalog());
    } catch (SQLException e) {
      LOGGER.error(e.getLocalizedMessage(), e.getCause());
    }
  }

  @Override
  public boolean createSchema() {
    try (Statement createschema = conn.createStatement()) {
      createschema.execute(CREATE_SCHEMA);
      insertIntoOrders = conn.prepareStatement(INSERT_ORDER, RETURN_GENERATED_KEYS);
      getschema = conn.createStatement();
      queryOrders = conn.createStatement();
      removeorder = conn.prepareStatement(REMOVE_ORDER);
      queyOrderById = conn.prepareStatement(QUERY_ORDER);
      deleteschema = conn.createStatement();
    } catch (SQLException e) {
      LOGGER.error(e.getLocalizedMessage(), e.getCause());
      return false;
    }
    return true;
  }

  @Override
  public String getSchema() {
    try {
      var resultSet = getschema.executeQuery(GET_SCHEMA);
      var sb = new StringBuilder();
      while (resultSet.next()) {
        sb.append("Col name: ").append(resultSet.getString(1)).append(",  Col type: ").append(resultSet.getString(2)).append("\n");
      }
      getschema.close();
      return sb.toString();
    } catch (Exception e) {
      LOGGER.error("Error in retrieving schema: {}", e.getLocalizedMessage(), e.getCause());
    }
    return "Schema unavailable";
  }
  
  @Override
  public boolean insertOrder(Order order) {
    try {
      conn.setAutoCommit(false);
      insertIntoOrders.setString(1, order.getItem());
      insertIntoOrders.setString(2, order.getOrderedBy());
      var address = order.getShippingAddress();
      insertIntoOrders.setString(3, address.getCity());
      insertIntoOrders.setString(4, address.getState());
      insertIntoOrders.setString(5, address.getPincode());
      var affectedRows = insertIntoOrders.executeUpdate();
      if (affectedRows == 1) {
        var rs = insertIntoOrders.getGeneratedKeys();
        rs.last();
        var insertedAddress = new ShippingAddress(address.getCity(), address.getState(), address.getPincode());
        var insertedOrder = new Order(rs.getInt(1), order.getItem(), order.getOrderedBy(),
            insertedAddress); 
        conn.commit();
        LOGGER.info("Inserted: {}", insertedOrder);
      } else {
        conn.rollback();
      }
    } catch (Exception e) {
      LOGGER.error(e.getLocalizedMessage());
    } finally {
      try {
        conn.setAutoCommit(true);
      } catch (SQLException e) {
        LOGGER.error(e.getLocalizedMessage());
      }
    }
    return true;
  }

  @Override
  public Stream<Order> queryOrders() {
    var ordersList = new ArrayList<Order>();
    try (var rSet = queryOrders.executeQuery(QUERY_ORDERS)) {
      while (rSet.next()) {
        var order = new Order(rSet.getInt(1), rSet.getString(2), rSet.getString(3),
            new ShippingAddress(rSet.getString(4), rSet.getString(5),
            rSet.getString(6)));
        ordersList.add(order);
      }
    } catch (SQLException e) {
      LOGGER.error(e.getMessage(), e.getCause());
    }
    return ordersList.stream();
  }

  /**
   * Query order by given id.
   * @param id as the parameter
   * @return Order objct
   * @throws SQLException in case of unexpected events
   */

  @Override
  public Order queryOrder(int id) throws SQLException {
    Order order = null;
    queyOrderById.setInt(1, id);
    try (var rSet = queyOrderById.executeQuery()) {
      queyOrderById.setInt(1, id);
      if (rSet.next()) {
        var address = new ShippingAddress(rSet.getString(4),
            rSet.getString(5), rSet.getString(6));
        order = new Order(rSet.getInt(1), rSet.getString(2), rSet.getString(3), address);
      }
    } catch (Exception e) {
      LOGGER.error(e.getLocalizedMessage(), e.getCause());
    }
    return order;
  }

  @Override
  public void removeOrder(int id) throws Exception {
    try {
      conn.setAutoCommit(false);
      removeorder.setInt(1, id);
      if (removeorder.executeUpdate() == 1) {
        LOGGER.info("Order with id " + id + " successfully removed");
      } else {
        LOGGER.info("Order with id " + id + " unavailable.");
      }
    } catch (Exception e) {
      LOGGER.error(e.getLocalizedMessage(), e.getCause());
      conn.rollback();
    } finally {
      conn.setAutoCommit(true);
    }
  }

  @Override
  public boolean deleteSchema()  {
    try {
      deleteschema.execute(DELETE_SCHEMA);
      queryOrders.close();
      queyOrderById.close();
      deleteschema.close();
      insertIntoOrders.close();
      conn.close();
      return true;
    } catch (Exception e) {
      LOGGER.error(e.getLocalizedMessage(), e.getCause());
    }
    return false;
  }
}