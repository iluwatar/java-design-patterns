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
 * SLOB Application test
 */
@Slf4j
class AppTest {

  /**
   * Creates a Forest with Animals and Plants along with their respective relationships.
   * <p> The method creates a forest with 2 Plants Grass and Oak of type Herb and tree
   * respectively.</p>
   * <p> It also creates 3 animals Zebra and Buffalo which eat the plant grass. Lion consumes the
   * Zebra and the Buffalo.</p>
   * <p>With the above animals and plants and their relationships a forest
   * object is created which represents the Object Graph.</p>
   *
   * @return Forest Object
   */
  private static Forest createForest() {
    Plant grass = new Plant("Grass", "Herb");
    Plant oak = new Plant("Oak", "Tree");

    Animal zebra = new Animal("Zebra", Set.of(grass), Collections.emptySet());
    Animal buffalo = new Animal("Buffalo", Set.of(grass), Collections.emptySet());
    Animal lion = new Animal("Lion", Collections.emptySet(), Set.of(zebra, buffalo));

    return new Forest("Amazon", Set.of(lion, buffalo, zebra), Set.of(grass, oak));
  }

  /**
   * Tests the {@link App} without passing any argument in the args to test the
   * {@link ClobSerializer}.
   */
  @Test
  void shouldExecuteWithoutExceptionClob() {
    assertDoesNotThrow(() -> App.main(new String[]{"CLOB"}));
  }

  /**
   * Tests the {@link App} without passing any argument in the args to test the
   * {@link BlobSerializer}.
   */
  @Test
  void shouldExecuteWithoutExceptionBlob() {
    assertDoesNotThrow(() -> App.main(new String[]{}));
  }

  /**
   * Tests the serialization of the input object using the {@link ClobSerializer} and persists the
   * serialized object to DB, then load the object back from DB and deserializes it using the
   * provided {@link ClobSerializer}.<p>After loading the object back from DB the test matches the
   * hash of the input object with the hash of the object that was loaded from DB and deserialized.
   */
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

  /**
   * Tests the serialization of the input object using the {@link BlobSerializer} and persists the
   * serialized object to DB, then loads the object back from DB and deserializes it using the
   * {@link BlobSerializer}.<p>After loading the object back from DB the test matches the hash of
   * the input object with the hash of the object that was loaded from DB and deserialized.
   */
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
