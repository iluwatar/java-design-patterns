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
import java.util.Optional;
import java.util.Set;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;

/**
 * An active record base supposed to hold all the necessary active record pattern logic.
 *
 * <p>This is the base class which is supposed to be extended by all the domain models that are
 * expected to be persistent.
 */
@RequiredArgsConstructor
public abstract class RecordBase {

  private static final String EXCEPTION_MESSAGE =
      "Couldn't execute database query for the following domain model: ";

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
   * @param clazz the record class.
   * @param <T>   the record type.
   * @return all the domain model related records.
   */
  public static <T extends RecordBase> List<T> findAll(Class<T> clazz) {
    List<T> recordList = new ArrayList<>();
    String selectQuery = Query
        .selectFrom(getDeclaredClassInstance(clazz).getTableName())
        .toString();
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {
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
   * @param id    domain model identifier.
   * @param clazz the record class.
   * @param <T>   the record type.
   * @return the domain model.
   */
  public static <T extends RecordBase> Optional<T> findById(Long id, Class<T> clazz) {
    String selectStatement = Query
        .selectFrom(getDeclaredClassInstance(clazz).getTableName())
        .withKey("id")
        .toString();
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(selectStatement)) {
      pstmt.setLong(1, id);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          T theRecord = getDeclaredClassInstance(clazz);
          theRecord.setFieldsFromResultSet(rs);
          return Optional.of(theRecord);
        }
        return Optional.empty();
      }
    } catch (SQLException e) {
      throw new RecordDataAccessException(EXCEPTION_MESSAGE + clazz.getName() + " with id=" + id,
          e);
    }
  }

  /**
   * Delete the record by its unique identifier.
   *
   * @param id    the record identifier.
   * @param clazz the record class.
   * @param <T>   the record type.
   */
  public static <T extends RecordBase> void delete(Long id, Class<T> clazz) {
    String deleteStatement = Query
        .deleteFrom(getDeclaredClassInstance(clazz).getTableName())
        .withKey("id")
        .toString();
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(deleteStatement)) {
      pstmt.setLong(1, id);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new RecordDataAccessException(EXCEPTION_MESSAGE + clazz.getName() + " with id=" + id,
          e);
    }
  }

  /**
   * Save the record.
   *
   * @param clazz the record class.
   * @param <T>   the record type.
   */
  public <T extends RecordBase> void save(Class<T> clazz) {
    try (Connection connection = getConnection();
         PreparedStatement pstmt = connection.prepareStatement(constructInsertionQuery(clazz),
             Statement.RETURN_GENERATED_KEYS)) {

      setPreparedStatementParams(pstmt);
      pstmt.executeUpdate();

    } catch (SQLException e) {
      throw new RecordDataAccessException(EXCEPTION_MESSAGE + clazz.getName(), e);
    }
  }

  protected abstract void setPreparedStatementParams(PreparedStatement pstmt) throws SQLException;

  /**
   * Constructs an insertion query based on the descending type's fields.
   *
   * @param clazz the record class.
   * @param <T>   the record type.
   * @return a full insert query.
   */
  protected static <T extends RecordBase> String constructInsertionQuery(Class<T> clazz) {
    List<Object> arguments = new ArrayList<>();
    try {
      T recordInstance = getDeclaredClassInstance(clazz);
      InsertionQuery insert = Query.insertInto(clazz.getSimpleName());

      List<Field> standardFields = filterStandardTypes(clazz);

      for (Field field : standardFields) {
        field.setAccessible(true);
        arguments.add(field.get(recordInstance));
        insert.column(field.getName()).value("?");
      }
      return insert.toString();
    } catch (IllegalAccessException ignored) {
      // NOOP
    }
    return null;
  }

  private static <T extends RecordBase> List<Field> filterStandardTypes(Class<T> clazz) {
    return Arrays.stream(clazz.getDeclaredFields())
        .filter(field -> STANDARD_TYPES.contains(field.getType()))
        .toList();
  }

  private static <T extends RecordBase> T getDeclaredClassInstance(Class<T> clazz) {
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
