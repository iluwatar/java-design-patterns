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

import com.iluwatar.slob.lob.Customer;
import com.iluwatar.slob.lob.Product;
import com.iluwatar.slob.serializers.ClobSerializer;
import com.iluwatar.slob.serializers.LobSerializer;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * Application.
 */
@Slf4j
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * @param args
   * @throws SQLException
   */
  public static void main(String[] args) throws SQLException {

    Product product = new Product("Mountains", List.of(new Product("Iron", null)));
    Customer customer = new Customer("Ram", List.of(product));

    LobSerializer serializer = new ClobSerializer();
    executeSerializer(customer, serializer);

  }

  /**
   * @param customer
   * @param lobSerializer
   */
  private static void executeSerializer(Customer customer, LobSerializer lobSerializer) {
    try (LobSerializer serializer = lobSerializer) {

      Object serialized = serializer.serialize(customer);
      int id = serializer.persistToDb(1, customer.getName(), serialized);

      Object fromDb = serializer.loadFromDb(id, "products");
      Customer customerFromDb = serializer.deSerialize(fromDb);

      LOGGER.info(customerFromDb.toString());
    } catch (SQLException | IOException | TransformerException | ParserConfigurationException
             | SAXException e) {
      throw new RuntimeException(e);
    }
  }
}
