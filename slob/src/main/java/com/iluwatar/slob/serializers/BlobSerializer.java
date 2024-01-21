package com.iluwatar.slob.serializers;

import com.iluwatar.slob.lob.Forest;

import java.io.*;
import java.sql.SQLException;

public class BlobSerializer extends LobSerializer {

    public static final String typeOfDataForDB = "BLOB";

    /**
     * @throws SQLException
     */
    public BlobSerializer() throws SQLException {
        super(typeOfDataForDB);
    }

    /**
     * @param toSerialize
     * @return
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
     * @param toDeserialize
     * @return
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
