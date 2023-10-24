package com.iluwatar.slob.serializers;

import com.iluwatar.slob.lob.Customer;
import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

public abstract class LobSerializer implements Serializable, Closeable {

  public static final DatabaseService databaseService = new DatabaseService();

  /**
   * @throws SQLException
   */
  protected LobSerializer() throws SQLException {
    databaseService.startupService();
  }

  /**
   * @param toSerialize
   * @return
   * @throws SQLException
   * @throws ParserConfigurationException
   * @throws TransformerException
   */
  public abstract Object serialize(Customer toSerialize)
      throws SQLException, ParserConfigurationException, TransformerException;

  /**
   * @param id
   * @param name
   * @param customer
   * @return
   * @throws SQLException
   */
  public int persistToDb(int id, String name, Object customer) throws SQLException {
    databaseService.insert(id, name, customer);
    return id;
  }

  /**
   * @param id
   * @param columnName
   * @return
   * @throws SQLException
   */
  public Object loadFromDb(int id, String columnName) throws SQLException {
    return databaseService.select(id, columnName);
  }

  /**
   * @param toSerialize
   * @return
   * @throws ParserConfigurationException
   * @throws IOException
   * @throws SAXException
   */
  public abstract Customer deSerialize(Object toSerialize)
      throws ParserConfigurationException, IOException, SAXException;

  @Override
  public void close() throws IOException {
    try {
      databaseService.shutDownService();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
