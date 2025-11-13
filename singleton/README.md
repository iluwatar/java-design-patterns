---
layout: pattern
title: Singleton
folder: singleton
permalink: /patterns/singleton/
categories: Creational
tags:
 - Gang of Four
---

## Intent
Ensure a class only has one instance, and provide a global point of
access to it.


## Explanation
Real world example

> There can only be one ivory tower where the wizards study their magic. The same enchanted ivory tower is always used by the wizards. Ivory tower here is singleton.

In plain words

> Ensures that only one object of a particular class is ever created.

Wikipedia says

> In software engineering, the singleton pattern is a software design pattern that restricts the instantiation of a class to one object. This is useful when exactly one object is needed to coordinate actions across the system.

**Programmatic Example**

Joshua Bloch, Effective Java 2nd Edition p.18

> A single-element enum type is the best way to implement a singleton

```java
public enum EnumIvoryTower {
  INSTANCE;
}
```

Then in order to use

```java
var enumIvoryTower1 = EnumIvoryTower.INSTANCE;
var enumIvoryTower2 = EnumIvoryTower.INSTANCE;
assertEquals(enumIvoryTower1, enumIvoryTower2); // true
```

## Class diagram
![alt text](./etc/singleton.urm.png "Singleton pattern class diagram")

## Applicability
Use the Singleton pattern when

* There must be exactly one instance of a class, and it must be accessible to clients from a well-known access point
* When the sole instance should be extensible by subclassing, and clients should be able to use an extended instance without modifying their code

## Typical Use Case

* The logging class
* Managing a connection to a database
* File manager

## Real world examples

* [java.lang.Runtime#getRuntime()](http://docs.oracle.com/javase/8/docs/api/java/lang/Runtime.html#getRuntime%28%29)
* [java.awt.Desktop#getDesktop()](http://docs.oracle.com/javase/8/docs/api/java/awt/Desktop.html#getDesktop--)
* [java.lang.System#getSecurityManager()](http://docs.oracle.com/javase/8/docs/api/java/lang/System.html#getSecurityManager--)


## Consequences

* Violates Single Responsibility Principle (SRP) by controlling their own creation and lifecycle.
* Encourages using a global shared instance which prevents an object and resources used by this object from being deallocated.     
* Creates tightly coupled code. The clients of the Singleton become difficult to test.
* Makes it almost impossible to subclass a Singleton.

## 실무 예제 (Real-world Examples)

### 1. ConfigurationManager - 애플리케이션 설정 관리

애플리케이션 전체에서 사용되는 설정값을 중앙에서 관리합니다.

```java
// 설정값 저장
ConfigurationManager.getInstance().setProperty("db.url", "jdbc:mysql://localhost:3306/mydb");
ConfigurationManager.getInstance().setProperty("api.timeout", "30000");

// 설정값 조회
String dbUrl = ConfigurationManager.getInstance().getProperty("db.url");
String timeout = ConfigurationManager.getInstance().getProperty("api.timeout", "5000");

// 모든 설정 조회
Map<String, String> allConfig = ConfigurationManager.getInstance().getAllProperties();
```

**실무 활용:**
- 데이터베이스 연결 정보 관리
- API 엔드포인트 URL 관리
- 환경별(dev, staging, prod) 설정 분리
- 애플리케이션 전역 설정값 관리

### 2. LoggerService - 로깅 서비스

애플리케이션 전체에서 통일된 로깅 기능을 제공합니다.

```java
LoggerService logger = LoggerService.getInstance();

// 다양한 레벨의 로그 출력
logger.info("Application started");
logger.debug("User input: " + userInput);
logger.warning("Memory usage above 80%");
logger.error("Database connection failed");
logger.error("Critical error", exception);

// 로그 레벨 설정
logger.setLogLevel(LogLevel.WARNING);

// 로그 히스토리 조회
List<String> allLogs = logger.getLogHistory();
List<String> errorLogs = logger.getLogsByLevel(LogLevel.ERROR);
```

**실무 활용:**
- 애플리케이션 이벤트 로깅
- 에러 및 예외 추적
- 디버깅 정보 기록
- 사용자 활동 모니터링

### 3. DatabaseConnectionPool - 데이터베이스 연결 풀

데이터베이스 연결을 효율적으로 관리하는 연결 풀을 제공합니다.

```java
DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();

// 연결 획득 및 사용
Connection conn = pool.acquireConnection();
try {
    // 데이터베이스 작업 수행
    String result = conn.executeQuery("SELECT * FROM users");
    System.out.println(result);
} finally {
    // 연결 반환 (필수!)
    pool.releaseConnection(conn);
}

// 풀 상태 확인
PoolStats stats = pool.getPoolStats();
System.out.println("Active connections: " + stats.getActiveConnections());
System.out.println("Available connections: " + stats.getAvailableConnections());
```

**실무 활용:**
- 데이터베이스 연결 재사용으로 성능 향상
- 동시 연결 수 제한으로 리소스 관리
- 연결 생성/해제 오버헤드 감소
- Thread-safe한 연결 관리

## 테스트 실행

각 실무 예제에는 comprehensive한 테스트 코드가 포함되어 있습니다:

```bash
# 모든 테스트 실행
mvn test

# 특정 테스트만 실행
mvn test -Dtest=ConfigurationManagerTest
mvn test -Dtest=LoggerServiceTest
mvn test -Dtest=DatabaseConnectionPoolTest
```

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](http://www.amazon.com/Design-Patterns-Elements-Reusable-Object-Oriented/dp/0201633612)
* [Effective Java (2nd Edition)](http://www.amazon.com/Effective-Java-Edition-Joshua-Bloch/dp/0321356683)
