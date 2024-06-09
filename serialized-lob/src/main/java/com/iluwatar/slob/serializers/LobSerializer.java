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
package com.iluwatar.slob.serializers;

import com.iluwatar.slob.dbservice.DatabaseService;
import com.iluwatar.slob.lob.Forest;
import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 * A LobSerializer can be used to create an instance of a serializer which can serialize and
 * deserialize an object and persist and load that object into a DB. from their Binary
 * Representation.
 */
public abstract class LobSerializer implements Serializable, Closeable {

  private final transient DatabaseService databaseService;

  /**
   * Constructor initializes {@link LobSerializer#databaseService}.
   *
   * @param dataTypeDb Input provides type of Data to be stored by the Data Base Service
   * @throws SQLException If any issue occurs during instantiation of DB Service or during startup.
   */
  protected LobSerializer(String dataTypeDb) throws SQLException {
    databaseService = new DatabaseService(dataTypeDb);
    databaseService.startupService();
  }

  /**
   * Provides the specification to Serialize the input object.
   *
   * @param toSerialize Input Object to serialize
   * @return Serialized Object
   * @throws ParserConfigurationException if any issue occurs during parsing of input object
   * @throws TransformerException         if any issue occurs during Transformation
   * @throws IOException                  if any issues occur during reading object
   */
  public abstract Object serialize(Forest toSerialize)
      throws ParserConfigurationException, TransformerException, IOException;

  /**
   * Saves the object to DB with the provided ID.
   *
   * @param id     key to be sent to DB service
   * @param name   Object name to store in DB
   * @param object Object to store in DB
   * @return ID with which the object is stored in DB
   * @throws SQLException if any issue occurs while saving to DB
   */
  public int persistToDb(int id, String name, Object object) throws SQLException {
    databaseService.insert(id, name, object);
    return id;
  }

  /**
   * Loads the object from db using the ID and column name.
   *
   * @param id         to query the DB
   * @param columnName column from which object is to be extracted
   * @return Object from DB
   * @throws SQLException if any issue occurs while loading from DB
   */
  public Object loadFromDb(int id, String columnName) throws SQLException {
    return databaseService.select(id, columnName);
  }

  /**
   * Provides the specification to Deserialize the input object.
   *
   * @param toDeserialize object to deserialize
   * @return Deserialized Object
   * @throws ParserConfigurationException If issue occurs during parsing of input object
   * @throws IOException                  if any issues occur during reading object
   * @throws SAXException                 if any issues occur during reading object for XML parsing
   */
  public abstract Forest deSerialize(Object toDeserialize)
      throws ParserConfigurationException, IOException, SAXException, ClassNotFoundException;

  @Override
  public void close() {
    try {
      databaseService.shutDownService();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
