package com.iluwatar.activerecord;

import java.sql.ResultSet;
import java.util.List;
import javax.sql.DataSource;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer extends RecordBase {

  private Long id;
  private String customerNumber;
  private String firstName;
  private String lastName;
  private List<Order> orders;

  public Customer(DataSource dataSource) {
    super(dataSource);
  }

  public Customer findByNumber(String customerNumber) {
    // TODO
    return null;
//    return new Customer();
  }

  @Override
  protected String getTableName() {
    return "customer";
  }

  @Override
  protected void setFieldsFromResultSet(ResultSet rs) {

  }
}
