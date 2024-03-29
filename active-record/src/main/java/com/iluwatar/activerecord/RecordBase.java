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
import lombok.Setter;

/**
 * An active record base supposed to hold all the necessary active record pattern logic.
 *
 * @param <T> an active record type.
 */
@RequiredArgsConstructor
public abstract class RecordBase<T extends RecordBase<?>> {

  @Setter
  private static DataSource dataSource;

  @SuppressWarnings({"unchecked"})
  private final Class<T> clazz = (Class<T>) getClass();

  protected Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }

  /**
   * Returns an underlying table name defined within the domain model.
   *
   * @return the table name.
   */
  protected abstract String getTableName();

  protected abstract void setFieldsFromResultSet(ResultSet rs) throws SQLException;

  protected abstract void setPreparedStatementParams(PreparedStatement pstmt) throws SQLException;

  /**
   * Find all the records for a corresponding domain model.
   *
   * @return all the domain model related records.
   */
  public List<T> findAll() {
    List<T> recordList = new ArrayList<>();
    try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(constructFindAllQuery())) {
      try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
          T record = getDeclaredClassInstance();
          record.setFieldsFromResultSet(rs);
          recordList.add(record);
        }
        return recordList;
      }
    } catch (SQLException e) {
      throw new RuntimeException(
          "Unable to find all the records for the following domain model : " + clazz.getName()
              + " due to the data persistence error", e);
    }
  }

  /**
   * Find a domain model by its ID.
   *
   * @param id domain model identifier.
   * @return the domain model.
   */
  public T findById(Long id) {
    try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(constructFindByIdQuery())) {
      pstmt.setLong(1, id);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          T record = getDeclaredClassInstance();
          record.setFieldsFromResultSet(rs);
          return record;
        }
        return getDeclaredClassInstance();
      }
    } catch (SQLException e) {
      throw new RuntimeException(
          "Unable to find a record for the following domain model : " + clazz.getName() + " by id="
              + id
              + " due to the data persistence error", e);
    }
  }

  /**
   * Save the record.
   */
  public void save() {
    try (Connection connection = getConnection();
        // TODO
        PreparedStatement pstmt = connection.prepareStatement(null,
            PreparedStatement.RETURN_GENERATED_KEYS)) {

      setPreparedStatementParams(pstmt);
      pstmt.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException(
          "Unable to save the record for the following domain model : " + clazz.getName()
              + " due to the data persistence error", e);
    }
  }


  private String constructFindAllQuery() {
    return "SELECT * FROM " + getDeclaredClassInstance().getTableName();
  }

  private String constructFindByIdQuery() {
    return "SELECT * FROM " + getDeclaredClassInstance().getTableName()
        + " WHERE id = ?";
  }

  private T getDeclaredClassInstance() {
    try {
      return clazz.getDeclaredConstructor().newInstance();
    } catch (InvocationTargetException
             | NoSuchMethodException
             | IllegalAccessException
             | InstantiationException e) {
      throw new IllegalStateException(
          "Unable to create a new instance of the class=" + clazz.getName(), e);
    }
  }

}
