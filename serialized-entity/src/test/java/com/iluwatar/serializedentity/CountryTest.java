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
package com.iluwatar.serializedentity;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class CountryTest {

  @Test
  void testGetMethod() {
      Country China = new Country(
              86,
              "China",
              "Asia",
              "Chinese"
      );

      assertEquals(86, China.getCode());
      assertEquals("China", China.getName());
      assertEquals("Asia", China.getContinents());
      assertEquals("Chinese", China.getLanguage());
  }

  @Test
  void testSetMethod() {
      Country country = new Country(
              86,
              "China",
              "Asia",
              "Chinese"
      );

      country.setCode(971);
      country.setName("UAE");
      country.setContinents("West-Asia");
      country.setLanguage("Arabic");

      assertEquals(971, country.getCode());
      assertEquals("UAE", country.getName());
      assertEquals("West-Asia", country.getContinents());
      assertEquals("Arabic", country.getLanguage());
  }

  @Test
  void testSerializable(){
      // Serializing Country
      try {
          Country country = new Country(
                  86,
                  "China",
                  "Asia",
                  "Chinese");
          ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("output.txt"));
          objectOutputStream.writeObject(country);
          objectOutputStream.close();
      } catch (IOException e) {
          LOGGER.error("Error occurred: ", e);
      }

      // De-serialize Country
      try {
          ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("output.txt"));
          Country country = (Country) objectInputStream.readObject();
          objectInputStream.close();
          System.out.println(country);

          Country China = new Country(
                  86,
                  "China",
                  "Asia",
                  "Chinese");

          assertEquals(China, country);
      } catch (Exception e) {
        LOGGER.error("Error occurred: ", e);
      }
    try {
      Files.deleteIfExists(Paths.get("output.txt"));
    } catch (IOException e) {
      LOGGER.error("Error occurred: ", e);
    }
  }
}
