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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 실무 예제: 데이터베이스 연결 풀
 *
 * <p>DatabaseConnectionPool은 데이터베이스 연결을 효율적으로 관리하는 연결 풀을 구현합니다.
 * Singleton 패턴을 사용하여 애플리케이션 전체에서 하나의 연결 풀만 존재하도록 보장합니다.
 *
 * <h2>실무 활용 사례:</h2>
 * <ul>
 *   <li>데이터베이스 연결 재사용으로 성능 향상</li>
 *   <li>동시 연결 수 제한으로 리소스 관리</li>
 *   <li>연결 생성/해제 오버헤드 감소</li>
 *   <li>애플리케이션 전체에서 일관된 연결 관리</li>
 * </ul>
 *
 * <h2>사용 예제:</h2>
 * <pre>
 * DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
 *
 * // 연결 획득
 * Connection conn = pool.acquireConnection();
 * try {
 *     // 데이터베이스 작업 수행
 *     conn.executeQuery("SELECT * FROM users");
 * } finally {
 *     // 연결 반환 (필수!)
 *     pool.releaseConnection(conn);
 * }
 * </pre>
 *
 * <h2>주요 기능:</h2>
 * <ul>
 *   <li>Thread-safe한 연결 풀 관리</li>
 *   <li>최대 연결 수 제한</li>
 *   <li>연결 획득 타임아웃 설정</li>
 *   <li>풀 상태 모니터링</li>
 * </ul>
 */
public final class DatabaseConnectionPool {

  private static volatile DatabaseConnectionPool instance;
  private final BlockingQueue<Connection> availableConnections;
  private final List<Connection> allConnections;
  private final int maxPoolSize;
  private static final int DEFAULT_POOL_SIZE = 10;
  private static final int CONNECTION_TIMEOUT_SECONDS = 30;

  /**
   * 데이터베이스 연결을 나타내는 내부 클래스.
   * 실제 구현에서는 JDBC Connection을 사용하지만, 여기서는 시뮬레이션을 위한 간단한 구현.
   */
  public static class Connection {
    private final int id;
    private boolean inUse;
    private final long createdAt;

    public Connection(int id) {
      this.id = id;
      this.inUse = false;
      this.createdAt = System.currentTimeMillis();
    }

    public int getId() {
      return id;
    }

    public boolean isInUse() {
      return inUse;
    }

    public void setInUse(boolean inUse) {
      this.inUse = inUse;
    }

    public long getCreatedAt() {
      return createdAt;
    }

    /**
     * 쿼리 실행 시뮬레이션.
     *
     * @param query SQL 쿼리
     * @return 쿼리 실행 결과 (시뮬레이션)
     */
    public String executeQuery(String query) {
      if (!inUse) {
        throw new IllegalStateException("Connection is not in use");
      }
      return "Query executed on connection " + id + ": " + query;
    }

    @Override
    public String toString() {
      return "Connection{id=" + id + ", inUse=" + inUse + "}";
    }
  }

  /**
   * Private 생성자로 외부에서 인스턴스 생성 방지.
   *
   * @param poolSize 연결 풀 크기
   */
  private DatabaseConnectionPool(int poolSize) {
    this.maxPoolSize = poolSize;
    this.availableConnections = new LinkedBlockingQueue<>(poolSize);
    this.allConnections = new ArrayList<>();
    initializePool();
  }

  /**
   * DatabaseConnectionPool의 유일한 인스턴스 반환 (기본 풀 크기).
   *
   * @return DatabaseConnectionPool의 유일한 인스턴스
   */
  public static DatabaseConnectionPool getInstance() {
    return getInstance(DEFAULT_POOL_SIZE);
  }

  /**
   * DatabaseConnectionPool의 유일한 인스턴스 반환 (커스텀 풀 크기).
   * Double-Checked Locking을 사용한 Thread-safe한 초기화.
   *
   * @param poolSize 연결 풀 크기
   * @return DatabaseConnectionPool의 유일한 인스턴스
   */
  public static DatabaseConnectionPool getInstance(int poolSize) {
    if (instance == null) {
      synchronized (DatabaseConnectionPool.class) {
        if (instance == null) {
          instance = new DatabaseConnectionPool(poolSize);
        }
      }
    }
    return instance;
  }

  /**
   * 연결 풀 초기화.
   */
  private void initializePool() {
    for (int i = 0; i < maxPoolSize; i++) {
      Connection connection = new Connection(i + 1);
      allConnections.add(connection);
      availableConnections.offer(connection);
    }
    System.out.println("Connection pool initialized with " + maxPoolSize + " connections");
  }

  /**
   * 연결 획득.
   * 사용 가능한 연결이 없으면 타임아웃까지 대기.
   *
   * @return 데이터베이스 연결
   * @throws InterruptedException 대기 중 인터럽트 발생
   * @throws IllegalStateException 타임아웃 발생
   */
  public Connection acquireConnection() throws InterruptedException {
    Connection connection = availableConnections.poll(CONNECTION_TIMEOUT_SECONDS,
        TimeUnit.SECONDS);

    if (connection == null) {
      throw new IllegalStateException("Connection pool timeout - no available connections");
    }

    synchronized (connection) {
      connection.setInUse(true);
    }

    System.out.println("Connection " + connection.getId() + " acquired. "
        + "Available: " + getAvailableConnectionCount());
    return connection;
  }

  /**
   * 연결 반환.
   *
   * @param connection 반환할 연결
   */
  public void releaseConnection(Connection connection) {
    if (connection == null) {
      throw new IllegalArgumentException("Connection cannot be null");
    }

    if (!allConnections.contains(connection)) {
      throw new IllegalArgumentException("Connection does not belong to this pool");
    }

    synchronized (connection) {
      if (!connection.isInUse()) {
        throw new IllegalStateException("Connection is not in use");
      }
      connection.setInUse(false);
    }

    availableConnections.offer(connection);
    System.out.println("Connection " + connection.getId() + " released. "
        + "Available: " + getAvailableConnectionCount());
  }

  /**
   * 사용 가능한 연결 수 조회.
   *
   * @return 사용 가능한 연결 수
   */
  public int getAvailableConnectionCount() {
    return availableConnections.size();
  }

  /**
   * 사용 중인 연결 수 조회.
   *
   * @return 사용 중인 연결 수
   */
  public int getActiveConnectionCount() {
    return maxPoolSize - availableConnections.size();
  }

  /**
   * 전체 연결 풀 크기 조회.
   *
   * @return 전체 연결 풀 크기
   */
  public int getMaxPoolSize() {
    return maxPoolSize;
  }

  /**
   * 연결 풀 상태 정보 조회.
   *
   * @return 연결 풀 상태 정보
   */
  public PoolStats getPoolStats() {
    return new PoolStats(
        maxPoolSize,
        getActiveConnectionCount(),
        getAvailableConnectionCount()
    );
  }

  /**
   * 연결 풀 상태 정보를 담는 클래스.
   */
  public static class PoolStats {
    private final int totalConnections;
    private final int activeConnections;
    private final int availableConnections;

    public PoolStats(int totalConnections, int activeConnections, int availableConnections) {
      this.totalConnections = totalConnections;
      this.activeConnections = activeConnections;
      this.availableConnections = availableConnections;
    }

    public int getTotalConnections() {
      return totalConnections;
    }

    public int getActiveConnections() {
      return activeConnections;
    }

    public int getAvailableConnections() {
      return availableConnections;
    }

    @Override
    public String toString() {
      return String.format("PoolStats{total=%d, active=%d, available=%d}",
          totalConnections, activeConnections, availableConnections);
    }
  }

  /**
   * 테스트를 위한 풀 리셋 (프로덕션에서는 사용하지 않음).
   */
  public static void resetInstance() {
    instance = null;
  }
}
