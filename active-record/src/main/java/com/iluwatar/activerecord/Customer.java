package com.iluwatar.activerecord;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Customer extends RecordBase<Customer> {

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
    this.customerNumber = rs.getString("customer_number");
    this.firstName = rs.getString("first_name");
    this.lastName = rs.getString("last_name");
  }

  @Override
  protected void setPreparedStatementParams(PreparedStatement pstmt) throws SQLException {

  }
}
