/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.hexagonal.mongo;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Mongo connection properties loader.
 */
public class MongoConnectionPropertiesLoader {

  private static final String DEFAULT_HOST = "localhost";
  private static final int DEFAULT_PORT = 27017;

  /**
   * Try to load connection properties from file. Fall back to default connection properties.
   */
  public static void load() {
    var host = DEFAULT_HOST;
    var port = DEFAULT_PORT;
    var path = System.getProperty("hexagonal.properties.path");
    var properties = new Properties();
    if (path != null) {
      try (var fin = new FileInputStream(path)) {
        properties.load(fin);
        host = properties.getProperty("mongo-host");
        port = Integer.parseInt(properties.getProperty("mongo-port"));
      } catch (Exception e) {
        // error occurred, use default properties
        e.printStackTrace();
      }
    }
    System.setProperty("mongo-host", host);
    System.setProperty("mongo-port", String.format("%d", port));
  }
}
