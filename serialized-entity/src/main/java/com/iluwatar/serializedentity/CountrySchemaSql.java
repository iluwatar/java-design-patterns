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
package com.iluwatar.serializedentity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;

/**
 * Country Schema SQL Class.
 */
@Slf4j
public class CountrySchemaSql implements CountryDao {
  public static final String CREATE_SCHEMA_SQL = "CREATE TABLE IF NOT EXISTS WORLD (ID INT PRIMARY KEY, COUNTRY BLOB)";

  public static final String DELETE_SCHEMA_SQL = "DROP TABLE WORLD IF EXISTS";

  private Country country;
  private DataSource dataSource;

  /**
   * Public constructor.
   *
   * @param dataSource datasource
   * @param country country
   */
  public CountrySchemaSql(Country country, DataSource dataSource) {
    this.country = new Country(
            country.getCode(),
            country.getName(),
            country.getContinents(),
            country.getLanguage()
    );
    this.dataSource = dataSource;
  }

  /**
   * This method will serialize a Country object and store it to database.
   * @return int type, if successfully insert a serialized object to database then return country code, else return -1.
   * @throws IOException if any.
   */
  @Override
  public int insertCountry() throws IOException {
    var sql = "INSERT INTO WORLD (ID, COUNTRY) VALUES (?, ?)";
    try (var connection = dataSource.getConnection();
         var preparedStatement = connection.prepareStatement(sql);
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         ObjectOutputStream oss = new ObjectOutputStream(baos)) {

      oss.writeObject(country);
      oss.flush();

      preparedStatement.setInt(1, country.getCode());
      preparedStatement.setBlob(2, new ByteArrayInputStream(baos.toByteArray()));
      preparedStatement.execute();
      return country.getCode();
    } catch (SQLException e) {
      LOGGER.info("Exception thrown " + e.getMessage());
    }
    return -1;
  }

  /**
   * This method will select a data item from database and deserialize it.
   * @return int type, if successfully select and deserialized object from database then return country code,
   *     else return -1.
   * @throws IOException if any.
   * @throws ClassNotFoundException if any.
   */
  @Override
  public int selectCountry() throws IOException, ClassNotFoundException {
    var sql = "SELECT ID, COUNTRY FROM WORLD WHERE ID = ?";
    try (var connection = dataSource.getConnection();
         var preparedStatement = connection.prepareStatement(sql)) {

      preparedStatement.setInt(1, country.getCode());

      try (ResultSet rs = preparedStatement.executeQuery()) {
        if (rs.next()) {
          Blob countryBlob = rs.getBlob("country");
          ByteArrayInputStream baos = new ByteArrayInputStream(countryBlob.getBytes(1, (int) countryBlob.length()));
          ObjectInputStream ois = new ObjectInputStream(baos);
          country = (Country) ois.readObject();
          LOGGER.info("Country: " + country);
        }
        return rs.getInt("id");
      }
    } catch (SQLException e) {
      LOGGER.info("Exception thrown " + e.getMessage());
    }
    return -1;
  }

}
