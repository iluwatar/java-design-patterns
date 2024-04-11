package com.iluwatar.activerecord.base;

import com.iluwatar.activerecord.base.Query.InsertionQuery;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;

/**
 * An active record base supposed to hold all the necessary active record pattern logic.
 *
 * <p>This is the base class which is supposed to be extended by all the domain models that are
 * expected to be persistent.
 *
 * @param <T> an active record type.
 */
@RequiredArgsConstructor
public abstract class RecordBase<T extends RecordBase<?>> {

  private static final String EXCEPTION_MESSAGE =
      "Couldn't execute database query for the following domain model :";

  private static DataSource dataSource;

  private static final Set<Class<?>> STANDARD_TYPES = Set.of(
      Boolean.class, Character.class, Byte.class, Short.class, Integer.class,
      Long.class, Float.class, Double.class, String.class
  );

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
   * Get a database connection.
   *
   * @return the connection {@link Connection}.
   */
  protected static Connection getConnection() {
    try {
      return dataSource.getConnection();
    } catch (SQLException e) {
      throw new RecordDataAccessException("Unable to acquire database connection", e);
    }
  }

  /**
   * A database table name of the underlying domain model.
   *
   * @return the table name.
   */
  protected abstract String getTableName();

  /**
   * Set all the fields into the underlying domain model from the result set.
   *
   * @param rs the result set {@link ResultSet}.
   * @throws SQLException an SQL exception.
   */
  protected abstract void setFieldsFromResultSet(ResultSet rs) throws SQLException;

  /**
   * Find all the records for a corresponding domain model.
   *
   * @return all the domain model related records.
   */
  public static <T extends RecordBase<?>> List<T> findAll(Class<T> clazz) {
    List<T> recordList = new ArrayList<>();
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(constructFindAllQuery(clazz))) {
      try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
          T theRecord = getDeclaredClassInstance(clazz);
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
  public static <T extends RecordBase<?>> T findById(Long id, Class<T> clazz) {
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(constructFindByIdQuery(clazz))) {
      pstmt.setLong(1, id);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          T theRecord = getDeclaredClassInstance(clazz);
          theRecord.setFieldsFromResultSet(rs);
          return theRecord;
        }
        return getDeclaredClassInstance(clazz);
      }
    } catch (SQLException e) {
      throw new RecordDataAccessException(EXCEPTION_MESSAGE + clazz.getName() + " with id=" + id,
          e);
    }
  }

  /**
   * Save the record.
   */
  public static <T extends RecordBase<?>> void save(Class<T> clazz) {
    try (Connection connection = getConnection();
         PreparedStatement pstmt = connection.prepareStatement(constructInsertionQuery(clazz),
             Statement.RETURN_GENERATED_KEYS)) {

      setPreparedStatementParams(pstmt, clazz);
      pstmt.executeUpdate();

    } catch (SQLException e) {
      throw new RecordDataAccessException(EXCEPTION_MESSAGE + clazz.getName(), e);
    }
  }

  private static <T extends RecordBase<?>> void setPreparedStatementParams(PreparedStatement pstmt,
                                                                           Class<T> clazz)
      throws SQLException {
    List<Field> standardFields = filterStandardTypes(clazz);

  }

  protected static <T extends RecordBase<?>> String constructInsertionQuery(Class<T> clazz) {
    List<Object> arguments = new ArrayList<>();
    try {
      InsertionQuery insert = Query.insertInto(clazz.getSimpleName());

      List<Field> standardFields = filterStandardTypes(clazz);

      for (Field field : standardFields) {
        field.setAccessible(true);
        arguments.add(field.get(clazz)); // FIXME: it doesn't work and fail - fix it
        insert.column(field.getName()).value(String.valueOf(field.get(clazz)));
      }
      return insert.toString();
    } catch (IllegalAccessException ignored) {
      // NOOP
    }
    return null;
  }

  private static <T extends RecordBase<?>> List<Field> filterStandardTypes(Class<T> clazz) {
    return Arrays.stream(clazz.getDeclaredFields())
        .filter(field -> STANDARD_TYPES.contains(field.getType()))
        .toList();
  }

  // TODO: implement Select query within the Query class
  private static <T extends RecordBase<?>> String constructFindByIdQuery(Class<T> clazz) {
    return constructFindAllQuery(clazz) + " WHERE id = ?";
  }

  private static <T extends RecordBase<?>> String constructFindAllQuery(Class<T> clazz) {
    return "SELECT * FROM " + getDeclaredClassInstance(clazz).getTableName();
  }

  private static <T extends RecordBase<?>> T getDeclaredClassInstance(Class<T> clazz) {
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
