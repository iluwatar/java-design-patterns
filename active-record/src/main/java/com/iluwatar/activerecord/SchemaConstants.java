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
package com.iluwatar.activerecord;

/**
 * Database schema related constants.
 */
public final class SchemaConstants {

  private SchemaConstants() {
  }


  public static final String DB_URL = "jdbc:h2:mem:dao;DB_CLOSE_DELAY=-1";

  public static final String CREATE_SCHEMA_SQL = "CREATE TABLE customer\n"
      + "(\n"
      + "    id              BIGINT      NOT NULL,\n"
      + "    customerNumber VARCHAR(15) NOT NULL,\n"
      + "    firstName      VARCHAR(45) NOT NULL,\n"
      + "    lastName       VARCHAR(45) NOT NULL,\n"
      + "    CONSTRAINT customer_pkey PRIMARY KEY (id)\n"
      + ");\n"
      + "\n"
      + "CREATE TABLE \"order\"\n"
      + "(\n"
      + "    id           BIGINT      NOT NULL,\n"
      + "    orderNumber VARCHAR(15) NOT NULL,\n"
      + "    customerId  BIGINT      NOT NULL,\n"
      + "    CONSTRAINT order_pkey PRIMARY KEY (id),\n"
      + "    CONSTRAINT customer_id_fk FOREIGN KEY (customerId) REFERENCES customer (id)\n"
      + ")";

  public static final String DELETE_SCHEMA_SQL = "DROP TABLE \"order\";"
      + "DROP TABLE customer";

}
