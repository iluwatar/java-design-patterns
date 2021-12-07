/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.serialized.lob;

import java.io.*;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.extern.slf4j.Slf4j;

/**
 * A class that performs serialization by first instantiating DataSource to crete a testing database and Position to
 * create a Position object that represents the graph relationship between Positions and Departments. Then, the class
 * serializes the Position object and stores it as a BLOB into the database. Next, the class manipulates this stored
 * database object by reading, updating, and eventually deleting it.
 */
@Slf4j
public class Serialization {
    /**
     * Public element for creating schema.
     */
    public static final String CREATE_SCHEMA_SQL =
            "CREATE TABLE IF NOT EXISTS POSITIONS (ID INT PRIMARY KEY, NAME VARCHAR, DEPARTMENTS BLOB)";

    /**
     * Public element for deleting schema.
     */
    public static final String DELETE_SCHEMA_SQL = "DROP TABLE POSITIONS IF EXISTS";

    /**
     * The private element for data source used for creating database connections.
     */
    private final DataSource dataSource;

    /**
     * The private element for position.
     */
    private final Position position;

    /**
     * Public constructor.
     *
     * @param dataSource datasource
     * @param position position
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public Serialization(Position position, final DataSource dataSource) {
        this.position = new Position(position.getId(), position.getName(), position.getDepartments());
        this.dataSource = dataSource;
    }

    /**
     * Insert a Position record into the database.
     *
     * @throws SQLException if any
     * @throws IOException if any
     */
    public int insert() throws SQLException, IOException {
        var sql = "insert into POSITIONS (ID, NAME, DEPARTMENTS) values (?, ?, ?)";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {

            objectOutputStream.writeObject(position.getDepartments());
            objectOutputStream.flush();

            preparedStatement.setInt(1, position.getId());
            preparedStatement.setString(2, position.getName());
            preparedStatement.setBlob(3, new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
            preparedStatement.execute();

            LOGGER.info(String.format("Insert Position: Id = %d | Name = %s | Departments = %s",
                        position.getId(), position.getName(), position.getDepartments()));

            return position.getId();
        }
    }

    /**
     * Read a Position record from the database.
     *
     * @throws SQLException if any
     * @throws IOException if any
     * @throws ClassNotFoundException if any
     */
    @SuppressFBWarnings("RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE")
    public int read () throws SQLException, IOException, ClassNotFoundException {
        var sql = "select ID, NAME, DEPARTMENTS from POSITIONS where ID = ?";
        List<Department> departments = null;
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            // This value retrieval can't be included in the first try ()
            preparedStatement.setInt(1, position.getId());

            // Make sure ResultSet auto-closes
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    Blob departmentsBlob = resultSet.getBlob("departments");
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(departmentsBlob.getBytes(1, (int) departmentsBlob.length()));
                    ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                    departments = (List<Department>) objectInputStream.readObject();
                }

                LOGGER.info(String.format("Read Position: Id = %d | Name = %s | Departments = %s",
                        resultSet.getInt("id"), resultSet.getString("name"), departments));

                return resultSet.getInt("id");
            }
        }
    }

    /**
     * Update a Position record stored in the database.
     *
     * @param updatePosition new Position record to be stored in the database.
     *
     * @throws SQLException if any
     * @throws IOException if any
     */
    public int update(Position updatePosition) throws SQLException, IOException {
        var sql = "update POSITIONS set NAME = ?, DEPARTMENTS = ? where ID = ?";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {

        objectOutputStream.writeObject(updatePosition.getDepartments());
        objectOutputStream.flush();

        preparedStatement.setString(1, updatePosition.getName());
        preparedStatement.setBlob(2, new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        preparedStatement.setInt(3, position.getId());
        preparedStatement.executeUpdate();

        LOGGER.info(String.format("Update Position: Id = %d | Name = %s | Departments = %s",
                position.getId(), updatePosition.getName(), updatePosition.getDepartments()));

        return position.getId();
        }
    }

    /**
     * Delete a Position record from the database.
     *
     * @throws SQLException if any
     */
    @SuppressFBWarnings("RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE")
    public int delete() throws SQLException {
        var sql = "delete from POSITIONS where ID = ?";
        try (var connection = dataSource.getConnection();
            var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, position.getId());
            preparedStatement.executeUpdate();

            LOGGER.info(String.format("Delete Position: Id = %d | Name = %s | Departments = %s",
                    position.getId(), position.getName(), position.getDepartments()));

            return position.getId();
        }
    }
}
