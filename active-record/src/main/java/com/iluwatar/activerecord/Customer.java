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
package com.iluwatar.activerecord;

import com.iluwatar.activerecord.base.RecordBase;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The customer domain model.
 */
@Getter
@Setter
@ToString
public class Customer extends RecordBase {

  private Long id;
  private String customerNumber;
  private String firstName;
  private String lastName;
  private List<Order> orders;

  public Customer findByNumber(String customerNumber) {
    // TODO
    return null;
  }

  public void addOrder(Order order) {
    orders.add(order);
  }

  @Override
  protected String getTableName() {
    return "customer";
  }

  @Override
  protected void setFieldsFromResultSet(ResultSet rs) throws SQLException {
    this.id = rs.getLong("id");
    this.customerNumber = rs.getString("customerNumber");
    this.firstName = rs.getString("firstName");
    this.lastName = rs.getString("lastName");
  }

  @Override
  protected void setPreparedStatementParams(PreparedStatement pstmt) throws SQLException {
    pstmt.setLong(1, id);
    pstmt.setString(2, customerNumber);
    pstmt.setString(3, firstName);
    pstmt.setString(4, lastName);
  }
}
