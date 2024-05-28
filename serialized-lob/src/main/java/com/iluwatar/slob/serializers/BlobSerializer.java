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

import com.iluwatar.slob.lob.Forest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;

/**
 * Creates a Serializer that uses Binary serialization and deserialization of objects graph to and
 * from their Binary Representation.
 */
public class BlobSerializer extends LobSerializer {

  public static final String TYPE_OF_DATA_FOR_DB = "BINARY";

  public BlobSerializer() throws SQLException {
    super(TYPE_OF_DATA_FOR_DB);
  }

  /**
   * Serializes the input object graph to its Binary Representation using Object Stream.
   *
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
   * Deserializes the input Byte Array Stream using Object Stream and return its Object Graph
   * Representation.
   *
   * @param toDeserialize Input Object to De-serialize
   * @return Deserialized Object
   * @throws ClassNotFoundException {@inheritDoc}
   * @throws IOException            {@inheritDoc}
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
