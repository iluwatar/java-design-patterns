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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 실무 예제: 로깅 서비스
 *
 * <p>LoggerService는 애플리케이션 전체에서 통일된 로깅 기능을 제공합니다.
 * Singleton 패턴을 사용하여 로그 메시지를 중앙에서 관리하고 일관된 형식으로 출력합니다.
 *
 * <h2>실무 활용 사례:</h2>
 * <ul>
 *   <li>애플리케이션 이벤트 로깅</li>
 *   <li>에러 및 예외 추적</li>
 *   <li>디버깅 정보 기록</li>
 *   <li>사용자 활동 모니터링</li>
 * </ul>
 *
 * <h2>사용 예제:</h2>
 * <pre>
 * LoggerService logger = LoggerService.getInstance();
 * logger.info("Application started");
 * logger.error("Database connection failed");
 * logger.debug("User input: " + userInput);
 * logger.warning("Memory usage above 80%");
 * </pre>
 *
 * <h2>로그 레벨:</h2>
 * <ul>
 *   <li>DEBUG: 상세한 디버깅 정보</li>
 *   <li>INFO: 일반적인 정보 메시지</li>
 *   <li>WARNING: 경고 메시지</li>
 *   <li>ERROR: 에러 메시지</li>
 * </ul>
 */
public final class LoggerService {

  private static final LoggerService instance = new LoggerService();
  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private final List<String> logHistory = Collections.synchronizedList(new ArrayList<>());
  private LogLevel currentLogLevel = LogLevel.INFO;

  /**
   * 로그 레벨 열거형.
   */
  public enum LogLevel {
    DEBUG(0),
    INFO(1),
    WARNING(2),
    ERROR(3);

    private final int priority;

    LogLevel(int priority) {
      this.priority = priority;
    }

    public int getPriority() {
      return priority;
    }
  }

  /**
   * Private 생성자로 외부에서 인스턴스 생성 방지.
   */
  private LoggerService() {
    // Initialization on demand holder idiom을 사용하여 Thread-safe 보장
  }

  /**
   * LoggerService의 유일한 인스턴스 반환.
   * Initialization-on-demand holder idiom을 사용하여 Thread-safe하고 효율적인 초기화 보장.
   *
   * @return LoggerService의 유일한 인스턴스
   */
  public static LoggerService getInstance() {
    return instance;
  }

  /**
   * 현재 로그 레벨 설정.
   *
   * @param level 설정할 로그 레벨
   */
  public void setLogLevel(LogLevel level) {
    this.currentLogLevel = level;
    log(LogLevel.INFO, "Log level changed to: " + level);
  }

  /**
   * 현재 로그 레벨 조회.
   *
   * @return 현재 로그 레벨
   */
  public LogLevel getLogLevel() {
    return currentLogLevel;
  }

  /**
   * DEBUG 레벨 로그 출력.
   *
   * @param message 로그 메시지
   */
  public void debug(String message) {
    log(LogLevel.DEBUG, message);
  }

  /**
   * INFO 레벨 로그 출력.
   *
   * @param message 로그 메시지
   */
  public void info(String message) {
    log(LogLevel.INFO, message);
  }

  /**
   * WARNING 레벨 로그 출력.
   *
   * @param message 로그 메시지
   */
  public void warning(String message) {
    log(LogLevel.WARNING, message);
  }

  /**
   * ERROR 레벨 로그 출력.
   *
   * @param message 로그 메시지
   */
  public void error(String message) {
    log(LogLevel.ERROR, message);
  }

  /**
   * 예외와 함께 ERROR 레벨 로그 출력.
   *
   * @param message 로그 메시지
   * @param throwable 예외 객체
   */
  public void error(String message, Throwable throwable) {
    String fullMessage = message + " | Exception: " + throwable.getClass().getName()
        + " - " + throwable.getMessage();
    log(LogLevel.ERROR, fullMessage);
  }

  /**
   * 로그 메시지 기록 및 출력.
   *
   * @param level 로그 레벨
   * @param message 로그 메시지
   */
  private void log(LogLevel level, String message) {
    if (level.getPriority() >= currentLogLevel.getPriority()) {
      String timestamp = LocalDateTime.now().format(formatter);
      String logEntry = String.format("[%s] [%s] %s", timestamp, level, message);
      logHistory.add(logEntry);
      System.out.println(logEntry);
    }
  }

  /**
   * 로그 히스토리 조회.
   *
   * @return 모든 로그 메시지의 복사본
   */
  public List<String> getLogHistory() {
    return new ArrayList<>(logHistory);
  }

  /**
   * 특정 레벨의 로그만 필터링하여 조회.
   *
   * @param level 필터링할 로그 레벨
   * @return 필터링된 로그 메시지 리스트
   */
  public List<String> getLogsByLevel(LogLevel level) {
    List<String> filtered = new ArrayList<>();
    String levelStr = "[" + level + "]";
    for (String log : logHistory) {
      if (log.contains(levelStr)) {
        filtered.add(log);
      }
    }
    return filtered;
  }

  /**
   * 로그 히스토리 초기화.
   */
  public void clearLogs() {
    logHistory.clear();
    info("Log history cleared");
  }

  /**
   * 로그 히스토리 개수 조회.
   *
   * @return 로그 메시지 개수
   */
  public int getLogCount() {
    return logHistory.size();
  }
}
