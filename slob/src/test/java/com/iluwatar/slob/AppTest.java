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
package com.iluwatar.slob;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.iluwatar.slob.lob.Animal;
import com.iluwatar.slob.lob.Forest;
import com.iluwatar.slob.lob.Plant;
import com.iluwatar.slob.serializers.BlobSerializer;
import com.iluwatar.slob.serializers.ClobSerializer;
import com.iluwatar.slob.serializers.LobSerializer;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

/**
 * Application test
 */
@Slf4j
class AppTest {

  private static Forest createForest() {
    Plant grass = new Plant("Grass", "Herb");
    Plant oak = new Plant("Oak", "Tree");

    Animal zebra = new Animal("Zebra", Set.of(grass), Collections.emptySet());
    Animal buffalo = new Animal("Buffalo", Set.of(grass), Collections.emptySet());
    Animal lion = new Animal("Lion", Collections.emptySet(), Set.of(zebra, buffalo));

    return new Forest("Amazon", Set.of(lion, buffalo, zebra), Set.of(grass, oak));
  }

  @Test
  void shouldExecuteWithoutExceptionClob() {
    assertDoesNotThrow(() -> App.main(new String[]{"CLOB"}));
  }

  @Test
  void shouldExecuteWithoutExceptionBlob() {
    assertDoesNotThrow(() -> App.main(new String[]{}));
  }

  @Test
  void clobSerializerTest() {
    Forest forest = createForest();
    try (LobSerializer serializer = new ClobSerializer()) {

      Object serialized = serializer.serialize(forest);
      int id = serializer.persistToDb(1, forest.getName(), serialized);

      Object fromDb = serializer.loadFromDb(id, Forest.class.getSimpleName());
      Forest forestFromDb = serializer.deSerialize(fromDb);

      Assertions.assertEquals(forest.hashCode(), forestFromDb.hashCode(),
          "Hashes of objects after Serializing and Deserializing are the same");
    } catch (SQLException | IOException | TransformerException | ParserConfigurationException |
             SAXException |
             ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void blobSerializerTest() {
    Forest forest = createForest();
    try (LobSerializer serializer = new BlobSerializer()) {

      Object serialized = serializer.serialize(forest);
      int id = serializer.persistToDb(1, forest.getName(), serialized);

      Object fromDb = serializer.loadFromDb(id, Forest.class.getSimpleName());
      Forest forestFromDb = serializer.deSerialize(fromDb);

      Assertions.assertEquals(forest.hashCode(), forestFromDb.hashCode(),
          "Hashes of objects after Serializing and Deserializing are the same");
    } catch (SQLException | IOException | TransformerException | ParserConfigurationException |
             SAXException |
             ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
