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
package com.iluwatar.rowdata;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

class UserGatewayTest {

  @Test
  void insertTest() {
    var r1 = new RowData(1, "John", 25);
    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db")) {
      UserGateway userGateway = new UserGateway(r1, conn);
      assertDoesNotThrow(userGateway::insert);
    } catch (SQLException e) {
      fail("Database connection failed");
    }
  }

  @Test
  void updateTest() {
    var r1 = new RowData(1, "John", 25);
    var r2 = new RowData(1, "Johnny", 30);
    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db")) {
      UserGateway userGateway = new UserGateway(r1, conn);
      userGateway.insert();
      userGateway.setRowData(r2);
      assertDoesNotThrow(userGateway::update);
    } catch (SQLException e) {
      fail("Database connection failed");
    }
  }

  @Test
  void deleteTest() {
    var r1 = new RowData(1, "John", 25);
    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db")) {
      UserGateway userGateway = new UserGateway(r1, conn);
      userGateway.insert();
      assertDoesNotThrow(userGateway::delete);
    } catch (SQLException e) {
      fail("Database connection failed");
    }
  }


  @Test
  void readTest() {
    var r1 = new RowData(1, "John", 25);
    var r2 = new RowData(1, "Johnny", 30);
    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db")) {
      UserGateway userGateway = new UserGateway(r1, conn);
      userGateway.setRowData(r2);
      assertEquals(r2, userGateway.getRowData());
    } catch (SQLException e) {
      fail("Database connection failed");
    }
  }
}
