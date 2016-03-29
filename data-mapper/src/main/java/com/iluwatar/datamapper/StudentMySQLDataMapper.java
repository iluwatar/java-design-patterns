/**
 * The MIT License Copyright (c) 2016 Amit Dixit
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.iluwatar.datamapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public final class StudentMySQLDataMapper implements StudentDataMapper {

  @Override
  public final Optional<Student> find(final UUID uniqueID) throws DataMapperException {

    try {
      /* OracleDriver class cant be initilized directly */
      Class.forName("oracle.jdbc.driver.OracleDriver");

      /* Create new connection */
      final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/MySQL", "username", "password");

      /* Create new Oracle compliant sql statement */
      final String statement = "SELECT `guid`, `grade`, `studentID`, `name` FROM `students` where `guid`=?";
      final PreparedStatement dbStatement = connection.prepareStatement(statement);

      /* Set unique id in sql statement */
      dbStatement.setString(1, uniqueID.toString());

      /* Execute the sql query */
      final ResultSet rs = dbStatement.executeQuery();

      while (rs.next()) {

        /* Create new student */
        final Student student = new Student(UUID.fromString(rs.getString("guid")));

        /* Set all values from database in java object */
        student.setName(rs.getString("name"));
        student.setGrade(rs.getString("grade").charAt(0));
        student.setStudentId(rs.getInt("studentID"));

        return Optional.of(student);
      }
    } catch (final SQLException | ClassNotFoundException e) {

      /* Don't couple any Data Mapper to java.sql.SQLException */
      throw new DataMapperException("Error occured reading Students from MySQL data source.", e);
    }

    /* Return empty value */
    return Optional.empty();
  }

  @Override
  public final void update(final Student student) throws DataMapperException {
    try {

      /* OracleDriver class cant be initilized directly */
      Class.forName("oracle.jdbc.driver.OracleDriver");

      /* Create new connection */
      final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/MySQL", "username", "password");

      /* Create new Oracle compliant sql statement */
      final String statement = "UPDATE `students` SET `grade`=?, `studentID`=?, `name`=? where `guid`=?";

      final PreparedStatement dbStatement = connection.prepareStatement(statement);

      /* Set java object values in sql statement */
      dbStatement.setString(1, Character.toString(student.getGrade()));
      dbStatement.setInt(2, student.getStudentId());
      dbStatement.setString(3, student.getName());
      dbStatement.setString(4, student.getGuId().toString());

      /* Execute the sql query */
      dbStatement.executeUpdate();

    } catch (final SQLException | ClassNotFoundException e) {

      /* Don't couple any Data Mapper to java.sql.SQLException */
      throw new DataMapperException("Error occured reading Students from MySQL data source.", e);
    }
  }

  @Override
  public final void insert(final Student student) throws DataMapperException {
    try {

      /* OracleDriver class cant be initilized directly */
      Class.forName("oracle.jdbc.driver.OracleDriver");

      /* Create new connection */
      final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/MySQL", "username", "password");

      /* Create new Oracle compliant sql statement */
      final String statement = "INSERT INTO `students` (`grade`, `studentID`, `name`, `guid`) VALUES (?, ?, ?, ?)";
      final PreparedStatement dbStatement = connection.prepareStatement(statement);

      /* Set java object values in sql statement */
      dbStatement.setString(1, Character.toString(student.getGrade()));
      dbStatement.setInt(2, student.getStudentId());
      dbStatement.setString(3, student.getName());
      dbStatement.setString(4, student.getGuId().toString());

      /* Execute the sql query */
      dbStatement.executeUpdate();

    } catch (final SQLException | ClassNotFoundException e) {

      /* Don't couple any Data Mapper to java.sql.SQLException */
      throw new DataMapperException("Error occured reading Students from MySQL data source.", e);
    }
  }

  @Override
  public final void delete(final Student student) throws DataMapperException {
    try {

      /* OracleDriver class cant be initilized directly */
      Class.forName("oracle.jdbc.driver.OracleDriver");

      /* Create new connection */
      final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/MySQL", "username", "password");

      /* Create new Oracle compliant sql statement */
      final String statement = "DELETE FROM `students` where `guid`=?";
      final PreparedStatement dbStatement = connection.prepareStatement(statement);

      /* Set java object values in sql statement */
      dbStatement.setString(1, student.getGuId().toString());

      /* Execute the sql query */
      dbStatement.executeUpdate();

    } catch (final SQLException | ClassNotFoundException e) {

      /* Don't couple any Data Mapper to java.sql.SQLException */
      throw new DataMapperException("Error occured reading Students from MySQL data source.", e);
    }
  }
}
