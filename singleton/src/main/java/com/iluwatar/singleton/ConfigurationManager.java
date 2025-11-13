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

import java.util.HashMap;
import java.util.Map;

/**
 * 실무 예제: 애플리케이션 설정 관리
 *
 * <p>ConfigurationManager는 애플리케이션 전체에서 사용되는 설정값들을 중앙에서 관리합니다.
 * Singleton 패턴을 사용하여 애플리케이션 전체에서 하나의 설정 관리자만 존재하도록 보장합니다.
 *
 * <h2>실무 활용 사례:</h2>
 * <ul>
 *   <li>데이터베이스 연결 정보 관리</li>
 *   <li>API 엔드포인트 URL 관리</li>
 *   <li>애플리케이션 전역 설정값 관리</li>
 *   <li>환경별(dev, staging, prod) 설정 분리</li>
 * </ul>
 *
 * <h2>사용 예제:</h2>
 * <pre>
 * // 설정값 저장
 * ConfigurationManager.getInstance().setProperty("db.url", "jdbc:mysql://localhost:3306/mydb");
 * ConfigurationManager.getInstance().setProperty("api.timeout", "30000");
 *
 * // 설정값 조회
 * String dbUrl = ConfigurationManager.getInstance().getProperty("db.url");
 * String timeout = ConfigurationManager.getInstance().getProperty("api.timeout");
 * </pre>
 *
 * <h2>장점:</h2>
 * <ul>
 *   <li>전역에서 일관된 설정값 접근</li>
 *   <li>메모리 효율성 (단일 인스턴스)</li>
 *   <li>Thread-safe한 설정 관리</li>
 * </ul>
 */
public final class ConfigurationManager {

  private static volatile ConfigurationManager instance;
  private final Map<String, String> properties;

  /**
   * Private 생성자로 외부에서 인스턴스 생성 방지.
   */
  private ConfigurationManager() {
    properties = new HashMap<>();
    // 기본 설정값 초기화
    initializeDefaultProperties();
  }

  /**
   * Double-Checked Locking을 사용한 Thread-safe한 Singleton 인스턴스 반환.
   *
   * @return ConfigurationManager의 유일한 인스턴스
   */
  public static ConfigurationManager getInstance() {
    if (instance == null) {
      synchronized (ConfigurationManager.class) {
        if (instance == null) {
          instance = new ConfigurationManager();
        }
      }
    }
    return instance;
  }

  /**
   * 기본 설정값 초기화.
   */
  private void initializeDefaultProperties() {
    properties.put("app.name", "Java Design Patterns");
    properties.put("app.version", "1.0.0");
    properties.put("app.environment", "development");
    properties.put("db.max.connections", "100");
    properties.put("api.timeout.ms", "30000");
  }

  /**
   * 설정값 저장.
   *
   * @param key 설정 키
   * @param value 설정 값
   */
  public synchronized void setProperty(String key, String value) {
    if (key == null || key.trim().isEmpty()) {
      throw new IllegalArgumentException("Property key cannot be null or empty");
    }
    properties.put(key, value);
  }

  /**
   * 설정값 조회.
   *
   * @param key 설정 키
   * @return 설정 값, 존재하지 않으면 null
   */
  public String getProperty(String key) {
    return properties.get(key);
  }

  /**
   * 기본값과 함께 설정값 조회.
   *
   * @param key 설정 키
   * @param defaultValue 기본값
   * @return 설정 값, 존재하지 않으면 기본값
   */
  public String getProperty(String key, String defaultValue) {
    return properties.getOrDefault(key, defaultValue);
  }

  /**
   * 설정값 삭제.
   *
   * @param key 설정 키
   * @return 삭제된 값, 존재하지 않으면 null
   */
  public synchronized String removeProperty(String key) {
    return properties.remove(key);
  }

  /**
   * 모든 설정값 조회.
   *
   * @return 모든 설정값의 복사본
   */
  public Map<String, String> getAllProperties() {
    return new HashMap<>(properties);
  }

  /**
   * 모든 설정값 초기화.
   */
  public synchronized void clearAllProperties() {
    properties.clear();
    initializeDefaultProperties();
  }

  /**
   * 설정값 존재 여부 확인.
   *
   * @param key 설정 키
   * @return 존재하면 true, 아니면 false
   */
  public boolean hasProperty(String key) {
    return properties.containsKey(key);
  }
}
