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

package com.iluwatar.activerecord.base;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class QueryTest {

  @Test
  void shouldConstructFullSelectQuery() {
    final String expectedQuery = "SELECT * FROM Courier";

    String actualQuery = Query
        .selectFrom("Courier")
        .toString();

    assertEquals(expectedQuery, actualQuery);
  }

  @Test
  void shouldConstructFullSelectByTheFieldQuery() {
    final String expectedQuery = "SELECT * FROM Courier WHERE id = ?";

    String actualQuery = Query
        .selectFrom("Courier")
        .withKey("id")
        .toString();

    assertEquals(expectedQuery, actualQuery);
  }

  @Test
  void shouldConstructFullInsertQuery() {
    final String expected = "INSERT INTO Courier (id,firstName,lastName) VALUES (?,?,?)";

    String actualQuery = Query.insertInto("Courier")
        .column("id")
        .value("?") // FIXME: This API doesn't look smooth
        .column("firstName")
        .value("?")
        .column("lastName")
        .value("?")
        .toString();

    assertEquals(expected, actualQuery);
  }

  @Test
  void constructDeleteQuery() {
    final String expected = "DELETE FROM Courier WHERE id = ?";

    String actualQuery = Query.deleteFrom("Courier")
        .withKey("id")
        .toString();

    assertEquals(expected, actualQuery);
  }
}
