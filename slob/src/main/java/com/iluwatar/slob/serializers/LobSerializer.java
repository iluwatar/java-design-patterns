package com.iluwatar.slob.serializers;

import com.iluwatar.slob.dbservice.DatabaseService;
import com.iluwatar.slob.lob.Forest;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;

public abstract class LobSerializer implements Serializable, Closeable {

    public final DatabaseService databaseService;

    /**
     * @throws SQLException
     */
    protected LobSerializer(String typeOfDataForDB) throws SQLException {
        databaseService = new DatabaseService(typeOfDataForDB);
        databaseService.startupService();
    }

    /**
     * @param toSerialize
     * @return
     * @throws SQLException
     * @throws ParserConfigurationException
     * @throws TransformerException
     */
    public abstract Object serialize(Forest toSerialize) throws SQLException, ParserConfigurationException, TransformerException, IOException;

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
    public abstract Forest deSerialize(Object toSerialize) throws ParserConfigurationException, IOException, SAXException, ClassNotFoundException;

    @Override
    public void close() throws IOException {
        try {
            databaseService.shutDownService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
