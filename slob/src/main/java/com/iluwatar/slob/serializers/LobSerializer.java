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

  protected LobSerializer() throws SQLException {
    databaseService.startupService();
  }

  public abstract Object serialize(Customer toSerialize)
      throws SQLException, ParserConfigurationException, TransformerException;

  public int persistToDb(int id, String name, Object customer) throws SQLException {
    databaseService.insert(id, name, customer);
    return id;
  }

  public Object loadFromDb(int id, String columnName) throws SQLException {
    return databaseService.select(id, columnName);
  }

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
