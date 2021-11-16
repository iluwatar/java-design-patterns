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

import lombok.extern.slf4j.Slf4j;

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
    public Serialization(Position position, final DataSource dataSource) {
        this.position = position;
        this.dataSource = dataSource;
    }

    /**
     * Insert a Position record into the database.
     *
     * @throws SQLException if any
     * @throws IOException if any
     */
    public void insert() throws SQLException, IOException {
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
        }
    }

    /**
     * Read a Position record from the database.
     *
     * @throws SQLException if any
     * @throws IOException if any
     * @throws ClassNotFoundException if any
     */
    public void read () throws SQLException, IOException, ClassNotFoundException {
        var sql = "select ID, NAME, DEPARTMENTS from POSITIONS where ID = ?";
        ObjectInputStream objectInputStream;
        ByteArrayInputStream byteArrayInputStream;
        List<Department> departments = null;
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, position.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Blob departmentsBlob = resultSet.getBlob("departments");
                byteArrayInputStream = new ByteArrayInputStream(departmentsBlob.getBytes(1, (int) departmentsBlob.length()));
                objectInputStream = new ObjectInputStream(byteArrayInputStream);
                departments = (List<Department>) objectInputStream.readObject();
            }
            LOGGER.info(String.format("Read Position: Id = %d | Name = %s | Departments = %s",
                    resultSet.getInt("id"), resultSet.getString("name"), departments));
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
    public void update(Position updatePosition) throws SQLException, IOException {
        var sql = "update POSITIONS set NAME = ?, DEPARTMENTS = ? where ID = ?";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)
        ) {
            objectOutputStream.writeObject(updatePosition.getDepartments());
            objectOutputStream.flush();
            preparedStatement.setString(1, updatePosition.getName());
            preparedStatement.setBlob(2, new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
            preparedStatement.setInt(3, position.getId());
            preparedStatement.executeUpdate();
            LOGGER.info(String.format("Update Position: Id = %d | Name = %s | Departments = %s",
                    position.getId(), updatePosition.getName(), updatePosition.getDepartments()));
        }
    }

    /**
     * Delete a Position record from the database.
     *
     * @throws SQLException if any
     */
    public void delete() throws SQLException {
        var sql = "delete from POSITIONS where ID = ?";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, position.getId());
            preparedStatement.executeUpdate();
            LOGGER.info(String.format("Delete Position: Id = %d | Name = %s | Departments = %s",
                    position.getId(), position.getName(), position.getDepartments()));
        }
    }
}
