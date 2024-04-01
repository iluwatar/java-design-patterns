package com.iluwatar.activerecord;

import com.iluwatar.activerecord.base.RecordBase;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Courier extends RecordBase<Courier> {

  private Long id;
  private String firstName;
  private String lastName;

  @Override
  protected String getTableName() {
    return "courier";
  }

  @Override
  protected void setFieldsFromResultSet(ResultSet rs) throws SQLException {

  }

  @Override
  protected void setPreparedStatementParams(PreparedStatement pstmt) throws SQLException {

  }
}
