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

import com.iluwatar.slob.lob.Animal;
import com.iluwatar.slob.lob.Forest;
import com.iluwatar.slob.lob.Plant;
import com.iluwatar.slob.serializers.ClobSerializer;
import com.iluwatar.slob.serializers.LobSerializer;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Set;

/**
 * SLOB Application using Serializer.
 */
@Slf4j
public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    /**
     * @param args NA
     */
    public static void main(String[] args) throws SQLException {

        Plant grass = new Plant("Grass", "Herb");
        Plant oak = new Plant("Oak", "Tree");

        Animal zebra = new Animal("Zebra", Set.of(grass), Collections.emptySet());
        Animal buffalo = new Animal("Buffalo", Set.of(grass), Collections.emptySet());
        Animal lion = new Animal("Lion", Collections.emptySet(), Set.of(zebra, buffalo));

        Forest forest = new Forest("Amazon", Set.of(lion, buffalo, zebra), Set.of(grass, oak));

        LobSerializer serializer = new ClobSerializer();
        executeSerializer(forest, serializer);
    }

    /**
     * Serialize the input object using the input serializer and persist to DB, then load the object
     * back from DB and deserializing using the provided input Serializer.
     * After loading from DB the method matches the hash of the input object with the hash of the object
     * that was loaded from DB and deserialized.
     * @param forest Object to Serialize and Persist
     * @param lobSerializer Serializer to Serialize and Deserialize Object
     */
    private static void executeSerializer(Forest forest, LobSerializer lobSerializer) {
        try (LobSerializer serializer = lobSerializer) {

            Object serialized = serializer.serialize(forest);
            int id = serializer.persistToDb(1, forest.getName(), serialized);

            Object fromDb = serializer.loadFromDb(id, Forest.class.getSimpleName());
            Forest forestFromDb = serializer.deSerialize(fromDb);

            LOGGER.info(forestFromDb.toString());
            if (forest.hashCode() == forestFromDb.hashCode()) {
                LOGGER.info("Objects Before And After Serialization are Same");
            }
        } catch (SQLException | IOException | TransformerException | ParserConfigurationException | SAXException |
                 ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
