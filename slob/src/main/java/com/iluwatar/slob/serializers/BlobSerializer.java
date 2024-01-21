package com.iluwatar.slob.serializers;

import com.iluwatar.slob.lob.Forest;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.sql.SQLException;

/**
 * Creates a Serializer that uses Binary serialization and deserialization of objects
 * graph to and from their Binary Representation.
 */
public class BlobSerializer extends LobSerializer {

    public static final String TYPE_OF_DATA_FOR_DB = "BINARY";

    public BlobSerializer() throws SQLException {
        super(TYPE_OF_DATA_FOR_DB);
    }

    /**
     * Serialize the input object graph to its Binary Representation using Object Stream
     * @param toSerialize Object which is to be serialized
     * @return Serialized object
     * @throws IOException {@inheritDoc}
     */
    @Override
    public Object serialize(Forest toSerialize) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(toSerialize);
        oos.close();
        return new ByteArrayInputStream(baos.toByteArray());
    }

    /**
     * Deserialize the input Byte Array Stream using Object Stream and return its Object Graph Representation
     * @param toDeserialize Input Object to De-serialize
     * @return Deserialized Object
     * @throws ClassNotFoundException {@inheritDoc}
     * @throws IOException {@inheritDoc}
     */
    @Override
    public Forest deSerialize(Object toDeserialize) throws IOException, ClassNotFoundException {
        InputStream bis = (InputStream) toDeserialize;
        Forest forest;
        try (ObjectInput in = new ObjectInputStream(bis)) {
            forest = (Forest) in.readObject();
        }
        return forest;
    }
}
