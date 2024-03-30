package com.iluwatar.activerecord;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;

/**
 * An active record base supposed to hold all the necessary active record pattern logic.
 * <p>
 * This is the base class which is supposed to be extended by all the domain models that are
 * expected to be persistent.
 *
 * @param <T> an active record type.
 */
@RequiredArgsConstructor
public abstract class RecordBase<T extends RecordBase<?>> {

  private static final String EXCEPTION_MESSAGE = "Couldn't execute database query for the following domain model :";

  private static DataSource dataSource;

  @SuppressWarnings({"unchecked"})
  private final Class<T> clazz = (Class<T>) getClass();

  /**
   * Sets the data source. Preferably to eb done during the application startup or within the
   * configuration.
   *
   * @param dataSource the data source {@link DataSource}.
   */
  public static void setDataSource(DataSource dataSource) {
    RecordBase.dataSource = dataSource;
  }

  /**
   * Get an SQL exception for the sake of all other internal persistence methods.
   *
   * @return the connection {@link Connection}.
   */
  protected Connection getConnection() {
    try {
      return dataSource.getConnection();
    } catch (SQLException e) {
      throw new RecordDataAccessException("Unable to acquire database connection", e);
    }
  }

  /**
   * Returns an underlying table name defined within the domain model.
   *
   * @return the table name.
   */
  protected abstract String getTableName();

  /**
   * Constructs an INSERT query which is being used for an insertion purposes.
   *
   * @return an insertion query.
   */
  protected abstract String constructInsertQuery();

  /**
   * Set all the fields into the underlying domain model from the result set.
   *
   * @param rs the result set {@link ResultSet}.
   * @throws SQLException an SQL exception.
   */
  protected abstract void setFieldsFromResultSet(ResultSet rs) throws SQLException;

  /**
   * Set the prepared statement parameters for the SQL statement to insert/update record.
   *
   * @param pstmt prepared statement {@link PreparedStatement}.
   * @throws SQLException an SQL exception.
   */
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
          T theRecord = getDeclaredClassInstance();
          theRecord.setFieldsFromResultSet(rs);
          recordList.add(theRecord);
        }
        return recordList;
      }
    } catch (SQLException e) {
      throw new RecordDataAccessException(EXCEPTION_MESSAGE + clazz.getName(), e);
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
          T theRecord = getDeclaredClassInstance();
          theRecord.setFieldsFromResultSet(rs);
          return theRecord;
        }
        return getDeclaredClassInstance();
      }
    } catch (SQLException e) {
      throw new RecordDataAccessException(EXCEPTION_MESSAGE + clazz.getName() + " with id=" + id,
          e);
    }
  }

  /**
   * Save the record.
   */
  public void save() {
    try (Connection connection = getConnection();
        PreparedStatement pstmt = connection.prepareStatement(constructInsertQuery(),
            Statement.RETURN_GENERATED_KEYS)) {

      setPreparedStatementParams(pstmt);
      pstmt.executeUpdate();

    } catch (SQLException e) {
      throw new RecordDataAccessException(EXCEPTION_MESSAGE + clazz.getName(), e);
    }
  }

  private String constructFindByIdQuery() {
    return constructFindAllQuery() + " WHERE id = ?";
  }

  private String constructFindAllQuery() {
    return "SELECT * FROM " + getDeclaredClassInstance().getTableName();
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
