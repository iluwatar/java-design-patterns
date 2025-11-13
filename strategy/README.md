---
layout: pattern
title: Strategy
folder: strategy
permalink: /patterns/strategy/
categories: Behavioral
tags:
 - Gang of Four
---

## Also known as
Policy

## Intent
Define a family of algorithms, encapsulate each one, and make them
interchangeable. Strategy lets the algorithm vary independently from clients
that use it.

## Class diagram
![alt text](./etc/strategy_1.png "Strategy")

## Applicability
Use the Strategy pattern when

* Many related classes differ only in their behavior. Strategies provide a way to configure a class either one of many behaviors
* You need different variants of an algorithm. for example, you might define algorithms reflecting different space/time trade-offs. Strategies can be used when these variants are implemented as a class hierarchy of algorithms
* An algorithm uses data that clients shouldn't know about. Use the Strategy pattern to avoid exposing complex, algorithm-specific data structures
* A class defines many behaviors, and these appear as multiple conditional statements in its operations. Instead of many conditionals, move related conditional branches into their own Strategy class

## Tutorial

* [Strategy Pattern Tutorial](https://www.journaldev.com/1754/strategy-design-pattern-in-java-example-tutorial)

## 실무 예제 (Practical Example)

### 결제 처리 시스템

전자상거래에서 다양한 결제 수단을 유연하게 처리하는 시스템입니다.

**주요 컴포넌트:**

1. **PaymentStrategy (인터페이스)**: 결제 전략 정의
2. **구체적 전략들**:
   - CreditCardPayment: 신용카드 결제
   - BankTransferPayment: 계좌이체 결제
   - PayPalPayment: PayPal 결제
3. **PaymentProcessor (Context)**: 결제 처리 담당

**사용 예제:**

```java
// 신용카드로 결제
PaymentStrategy creditCard = new CreditCardPayment(
    "1234-5678-9012-3456", "John Doe", "123", "12/25"
);
PaymentProcessor processor = new PaymentProcessor(creditCard);
PaymentResult result = processor.processPayment(1000.0);

// 런타임에 결제 방법 변경
PaymentStrategy paypal = new PayPalPayment("user@example.com", "password");
processor.setPaymentStrategy(paypal);
result = processor.processPayment(500.0);

// 계좌이체로 변경
PaymentStrategy bankTransfer = new BankTransferPayment(
    "KB Bank", "1234567890", "John Doe"
);
processor.setPaymentStrategy(bankTransfer);
result = processor.processPayment(10000.0);
```

**실무 활용:**
- 전자상거래 결제 시스템
- 구독 서비스 결제
- 모바일 앱 인앱 결제
- 다국가 결제 처리

**장점:**
1. **런타임 유연성**: 실행 중에 결제 방법 변경 가능
2. **확장성**: 새로운 결제 수단 추가 용이 (OCP 준수)
3. **분리**: 결제 로직과 비즈니스 로직 분리
4. **테스트 용이성**: 각 결제 수단별 독립적인 테스트 가능

**패턴 구조:**
```
PaymentProcessor (Context)
    └── uses PaymentStrategy (Interface)
            ├── CreditCardPayment
            ├── BankTransferPayment
            └── PayPalPayment
```

## 테스트 실행

```bash
# 모든 테스트 실행
mvn test

# 결제 시스템 테스트만 실행
mvn test -Dtest=PaymentProcessorTest
```

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](http://www.amazon.com/Design-Patterns-Elements-Reusable-Object-Oriented/dp/0201633612)
* [Functional Programming in Java: Harnessing the Power of Java 8 Lambda Expressions](http://www.amazon.com/Functional-Programming-Java-Harnessing-Expressions/dp/1937785467/ref=sr_1_1)
