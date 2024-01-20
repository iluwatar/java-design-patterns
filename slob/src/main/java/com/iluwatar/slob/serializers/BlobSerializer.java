package com.iluwatar.slob.serializers;

import com.iluwatar.slob.lob.Forest;

import java.io.*;
import java.sql.SQLException;
import java.util.Base64;

public class BlobSerializer extends LobSerializer {

    /**
     * @throws SQLException
     */
    public BlobSerializer() throws SQLException {
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
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    /**
     * @param toDeserialize
     * @return
     */
    @Override
    public Forest deSerialize(Object toDeserialize) throws IOException, ClassNotFoundException {
        byte[] decoded = Base64.getDecoder().decode(toDeserialize.toString());
        InputStream bis = new ByteArrayInputStream(decoded);
        Forest forest;
        try (ObjectInput in = new ObjectInputStream(bis)) {
            forest = (Forest) in.readObject();
        }
        return forest;
    }
}
