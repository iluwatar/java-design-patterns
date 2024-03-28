package com.iluwatar.activerecord;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class RecordBase {

  private final DataSource dataSource;

  protected Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }

  /**
   * Returns an underlying table name defined within the domain model.
   *
   * @return the table name.
   */
  protected abstract String getTableName();

  protected abstract void setFieldsFromResultSet(ResultSet rs);

  public <T extends RecordBase> List<T> findAll(Class<T> clazz) {
    List<T> recordList = new ArrayList<>();
    try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(constructGetByIdQuery(clazz))) {
      try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
          T record = getDeclaredClassInstance(clazz);
          record.setFieldsFromResultSet(rs);
          recordList.add(record);
        }
        return recordList;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Find a domain model by its ID.
   *
   * @param id    domain model identifier.
   * @param clazz domain model class.
   * @param <T>   domain model type.
   * @return the domain model.
   */
  public <T extends RecordBase> T findById(Long id, Class<T> clazz) {
    try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(constructGetByIdQuery(clazz))) {
      pstmt.setLong(1, id);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          T record = getDeclaredClassInstance(clazz);
          record.setFieldsFromResultSet(rs);
          return record;
        }
        return getDeclaredClassInstance(clazz);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void save() {
    // TODO
  }

  private <T extends RecordBase> String constructGetByIdQuery(Class<T> clazz) {
    return "SELECT * FROM " + getDeclaredClassInstance(clazz).getTableName()
        + " WHERE id = ?";
  }

  private <T extends RecordBase> T getDeclaredClassInstance(Class<T> clazz) {
    try {
      return clazz.getDeclaredConstructor().newInstance();
    } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException |
             InstantiationException e) {
      throw new IllegalStateException(
          "Unable to create a new instance of the class=" + clazz.getName(), e);
    }
  }

}
