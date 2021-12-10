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

package com.iluwatar.serializedentity;

import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class serializes the Taxonomy object.
 * It then stores it as a BLOB in the database.
 * The class also allows us to read, update, and delete the object.
 */
@Slf4j
public class Serialization {
    /**
     * Public element for creating schema.
     */
    public static final String CREATE_SCHEMA_SQL =
            "CREATE TABLE IF NOT EXISTS SPECIES (ID INT PRIMARY KEY, TAXONOMY BLOB)";

    /**
     * Public element for deleting schema.
     */
    public static final String DELETE_SCHEMA_SQL = "DROP TABLE SPECIES IF EXISTS";

    /**
     * The private element for data source used for creating database connections.
     */
    private final transient DataSource dataSource;

    /**
     * The private element for taxonomy.
     */
    private transient Taxonomy taxonomy;

    /**
     * Public constructor.
     *
     * @param dataSource datasource
     * @param taxonomy taxonomy
     */
    public Serialization(final Taxonomy taxonomy, final DataSource dataSource) {
        this.taxonomy = new Taxonomy(
                taxonomy.getSpeciesId(),
                taxonomy.getDomain(),
                taxonomy.getKingdom(),
                taxonomy.getPhylum(),
                taxonomy.getClassis(),
                taxonomy.getOrder(),
                taxonomy.getFamily(),
                taxonomy.getGenus(),
                taxonomy.getSpecies());
        this.dataSource = dataSource;
    }

    /**
     * Insert a Taxonomy record into the database.
     *
     * @throws SQLException if any
     * @throws IOException if any
     */
    public int insert() throws SQLException, IOException {
        var sql = "insert into SPECIES (ID, TAXONOMY) values (?, ?)";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql);
             ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {

            oos.writeObject(taxonomy);
            oos.flush();

            preparedStatement.setInt(1, taxonomy.getSpeciesId());
            preparedStatement.setBlob(2, new ByteArrayInputStream(baos.toByteArray()));
            preparedStatement.execute();

            LOGGER.info(String.format("Insert Species: Id = %d | Taxonomy = %s", taxonomy.getSpeciesId(), taxonomy));

            return taxonomy.getSpeciesId();
        }
    }

    /**
     * Read a Taxonomy record from the database.
     *
     * @throws SQLException if any
     * @throws IOException if any
     * @throws ClassNotFoundException if any
     */
    public int read() throws SQLException, IOException, ClassNotFoundException {
        var sql = "select ID, TAXONOMY from SPECIES where ID = ?";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            // This value retrieval can't be included in the first try ()
            preparedStatement.setInt(1, taxonomy.getSpeciesId());

            // Make sure ResultSet auto-closes
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    final Blob taxonomyBlob = resultSet.getBlob("taxonomy");
                    final ByteArrayInputStream baos = new ByteArrayInputStream(taxonomyBlob.getBytes(1, (int) taxonomyBlob.length()));
                    final ObjectInputStream objectInputStream = new ObjectInputStream(baos);
                    taxonomy = (Taxonomy) objectInputStream.readObject();
                }

                LOGGER.info(String.format("Read Species: Id = %d | Taxonomy = %s",
                        resultSet.getInt("id"), taxonomy));

                return resultSet.getInt("id");
            }
        }
    }

    /**
     * Update a Taxonomy record stored in the database.
     *
     * @param updateTaxonomy new Taxonomy record to be stored in the database.
     *
     * @throws SQLException if any
     * @throws IOException if any
     */
    public int update(final Taxonomy updateTaxonomy) throws SQLException, IOException {
        var sql = "update SPECIES set TAXONOMY = ? where ID = ?";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql);
             ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {

        oos.writeObject(updateTaxonomy);
        oos.flush();

        preparedStatement.setBlob(1, new ByteArrayInputStream(baos.toByteArray()));
        preparedStatement.setInt(2, taxonomy.getSpeciesId());
        preparedStatement.executeUpdate();

        LOGGER.info(String.format("Update Species: Id = %d | Taxonomy = %s",
                taxonomy.getSpeciesId(), updateTaxonomy));

        return taxonomy.getSpeciesId();
        }
    }

    /**
     * Delete a Taxonomy record from the database.
     *
     * @throws SQLException if any
     */
    public int delete() throws SQLException {
        var sql = "delete from SPECIES where ID = ?";
        try (var connection = dataSource.getConnection();
            var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, taxonomy.getSpeciesId());
            preparedStatement.executeUpdate();

            LOGGER.info(String.format("Delete Species: Id = %d | Taxonomy = %s", taxonomy.getSpeciesId(), taxonomy));

            return taxonomy.getSpeciesId();
        }
    }
}
