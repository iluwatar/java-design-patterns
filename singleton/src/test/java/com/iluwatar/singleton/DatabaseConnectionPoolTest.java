/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.singleton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.singleton.DatabaseConnectionPool.Connection;
import com.iluwatar.singleton.DatabaseConnectionPool.PoolStats;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * DatabaseConnectionPool 테스트.
 */
class DatabaseConnectionPoolTest {

  @BeforeEach
  void setUp() {
    // 각 테스트 전에 인스턴스 리셋
    DatabaseConnectionPool.resetInstance();
  }

  @AfterEach
  void tearDown() {
    // 테스트 후 정리
    DatabaseConnectionPool.resetInstance();
  }

  @Test
  void testSingletonInstance() {
    // Given & When
    DatabaseConnectionPool pool1 = DatabaseConnectionPool.getInstance();
    DatabaseConnectionPool pool2 = DatabaseConnectionPool.getInstance();

    // Then
    assertNotNull(pool1);
    assertNotNull(pool2);
    assertSame(pool1, pool2, "두 인스턴스는 동일해야 함");
  }

  @Test
  void testDefaultPoolSize() {
    // Given & When
    DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();

    // Then
    assertEquals(10, pool.getMaxPoolSize());
    assertEquals(10, pool.getAvailableConnectionCount());
    assertEquals(0, pool.getActiveConnectionCount());
  }

  @Test
  void testCustomPoolSize() {
    // Given
    int customSize = 5;

    // When
    DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance(customSize);

    // Then
    assertEquals(customSize, pool.getMaxPoolSize());
    assertEquals(customSize, pool.getAvailableConnectionCount());
  }

  @Test
  void testAcquireConnection() throws InterruptedException {
    // Given
    DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();

    // When
    Connection connection = pool.acquireConnection();

    // Then
    assertNotNull(connection);
    assertTrue(connection.isInUse());
    assertEquals(9, pool.getAvailableConnectionCount());
    assertEquals(1, pool.getActiveConnectionCount());
  }

  @Test
  void testReleaseConnection() throws InterruptedException {
    // Given
    DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
    Connection connection = pool.acquireConnection();

    // When
    pool.releaseConnection(connection);

    // Then
    assertFalse(connection.isInUse());
    assertEquals(10, pool.getAvailableConnectionCount());
    assertEquals(0, pool.getActiveConnectionCount());
  }

  @Test
  void testMultipleAcquireAndRelease() throws InterruptedException {
    // Given
    DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();

    // When
    Connection conn1 = pool.acquireConnection();
    Connection conn2 = pool.acquireConnection();
    Connection conn3 = pool.acquireConnection();

    // Then
    assertEquals(7, pool.getAvailableConnectionCount());
    assertEquals(3, pool.getActiveConnectionCount());

    // When
    pool.releaseConnection(conn1);
    pool.releaseConnection(conn2);

    // Then
    assertEquals(9, pool.getAvailableConnectionCount());
    assertEquals(1, pool.getActiveConnectionCount());

    // When
    pool.releaseConnection(conn3);

    // Then
    assertEquals(10, pool.getAvailableConnectionCount());
    assertEquals(0, pool.getActiveConnectionCount());
  }

  @Test
  void testConnectionExecuteQuery() throws InterruptedException {
    // Given
    DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
    Connection connection = pool.acquireConnection();
    String query = "SELECT * FROM users";

    // When
    String result = connection.executeQuery(query);

    // Then
    assertNotNull(result);
    assertTrue(result.contains(query));
    assertTrue(result.contains("Connection " + connection.getId()));

    // Clean up
    pool.releaseConnection(connection);
  }

  @Test
  void testExecuteQueryOnNonUsedConnectionThrowsException() throws InterruptedException {
    // Given
    DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
    Connection connection = pool.acquireConnection();
    pool.releaseConnection(connection);

    // When & Then
    assertThrows(IllegalStateException.class, () -> {
      connection.executeQuery("SELECT * FROM users");
    });
  }

  @Test
  void testReleaseNullConnectionThrowsException() {
    // Given
    DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();

    // When & Then
    assertThrows(IllegalArgumentException.class, () -> {
      pool.releaseConnection(null);
    });
  }

  @Test
  void testReleaseConnectionNotInPoolThrowsException() throws InterruptedException {
    // Given
    DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
    Connection externalConnection = new Connection(999);

    // When & Then
    assertThrows(IllegalArgumentException.class, () -> {
      pool.releaseConnection(externalConnection);
    });
  }

  @Test
  void testReleaseConnectionNotInUseThrowsException() throws InterruptedException {
    // Given
    DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
    Connection connection = pool.acquireConnection();
    pool.releaseConnection(connection);

    // When & Then
    assertThrows(IllegalStateException.class, () -> {
      pool.releaseConnection(connection);
    });
  }

  @Test
  void testPoolStats() throws InterruptedException {
    // Given
    DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance(5);

    // When
    Connection conn1 = pool.acquireConnection();
    Connection conn2 = pool.acquireConnection();
    PoolStats stats = pool.getPoolStats();

    // Then
    assertEquals(5, stats.getTotalConnections());
    assertEquals(2, stats.getActiveConnections());
    assertEquals(3, stats.getAvailableConnections());

    // Clean up
    pool.releaseConnection(conn1);
    pool.releaseConnection(conn2);
  }

  @Test
  void testPoolStatsToString() throws InterruptedException {
    // Given
    DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance(5);
    Connection conn = pool.acquireConnection();

    // When
    String statsString = pool.getPoolStats().toString();

    // Then
    assertTrue(statsString.contains("total=5"));
    assertTrue(statsString.contains("active=1"));
    assertTrue(statsString.contains("available=4"));

    // Clean up
    pool.releaseConnection(conn);
  }

  @Test
  void testConnectionToString() throws InterruptedException {
    // Given
    DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
    Connection connection = pool.acquireConnection();

    // When
    String connectionString = connection.toString();

    // Then
    assertTrue(connectionString.contains("Connection"));
    assertTrue(connectionString.contains("id=" + connection.getId()));
    assertTrue(connectionString.contains("inUse=true"));

    // Clean up
    pool.releaseConnection(connection);
  }

  @Test
  void testThreadSafetyWithMultipleThreads() throws InterruptedException {
    // Given
    DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance(5);
    int threadCount = 10;
    int iterationsPerThread = 10;
    Thread[] threads = new Thread[threadCount];

    // When
    for (int i = 0; i < threadCount; i++) {
      threads[i] = new Thread(() -> {
        for (int j = 0; j < iterationsPerThread; j++) {
          try {
            Connection conn = pool.acquireConnection();
            // 연결 사용 시뮬레이션
            Thread.sleep(10);
            pool.releaseConnection(conn);
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
        }
      });
      threads[i].start();
    }

    // Wait for all threads to complete
    for (Thread thread : threads) {
      thread.join();
    }

    // Then
    assertEquals(5, pool.getAvailableConnectionCount());
    assertEquals(0, pool.getActiveConnectionCount());
  }

  @Test
  void testAcquireAllConnections() throws InterruptedException {
    // Given
    DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance(3);

    // When
    Connection conn1 = pool.acquireConnection();
    Connection conn2 = pool.acquireConnection();
    Connection conn3 = pool.acquireConnection();

    // Then
    assertEquals(0, pool.getAvailableConnectionCount());
    assertEquals(3, pool.getActiveConnectionCount());

    // Clean up
    pool.releaseConnection(conn1);
    pool.releaseConnection(conn2);
    pool.releaseConnection(conn3);
  }

  @Test
  void testConnectionCreatedTime() throws InterruptedException {
    // Given
    long beforeCreation = System.currentTimeMillis();
    DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();

    // When
    Connection connection = pool.acquireConnection();
    long afterCreation = System.currentTimeMillis();

    // Then
    assertTrue(connection.getCreatedAt() >= beforeCreation);
    assertTrue(connection.getCreatedAt() <= afterCreation);

    // Clean up
    pool.releaseConnection(connection);
  }

  @Test
  void testConnectionIdUniqueness() throws InterruptedException {
    // Given
    DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance(5);

    // When
    Connection conn1 = pool.acquireConnection();
    Connection conn2 = pool.acquireConnection();
    Connection conn3 = pool.acquireConnection();

    // Then
    assertTrue(conn1.getId() != conn2.getId());
    assertTrue(conn1.getId() != conn3.getId());
    assertTrue(conn2.getId() != conn3.getId());

    // Clean up
    pool.releaseConnection(conn1);
    pool.releaseConnection(conn2);
    pool.releaseConnection(conn3);
  }
}
