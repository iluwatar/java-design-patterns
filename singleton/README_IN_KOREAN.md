---
title: "자바에서 싱글턴 패턴: 글로벌 액세스 포인트 구현"
shortTitle: 싱글턴
description: "자바 애플리케이션에서 리소스의 효율적 사용과 쉬운 접근을 보장하는 싱글턴 패턴에 대해 알아보세요. 예제와 상세한 설명으로 싱글턴 패턴을 구현하는 방법을 제공합니다."
category: Creational
language: ko
tag:
  - GoF (Gang of Four)
  - 인스턴스화
  - 지연 초기화
  - 리소스 관리
---

## 다른 이름

* 단일 인스턴스

## 싱글턴 디자인 패턴의 의도

자바 클래스가 단일 인스턴스만 가지도록 보장하고, 이 싱글턴 인스턴스에 대한 글로벌 액세스 지점을 제공합니다.

## 싱글턴 패턴과 현실 세계의 예시

### 현실 세계의 예

> 현실 세계에서 싱글턴 패턴을 비유할 수 있는 사례는 정부가 발급하는 여권입니다. 한 나라에서 각 시민은 단 한 개의 유효한 여권만 발급받을 수 있습니다. 여권 발급 기관은 동일한 사람이 중복된 여권을 발급받지 못하도록 보장합니다. 시민이 여행할 때마다 이 단일 여권을 사용해야 하며, 이는 여행 자격을 나타내는 고유하고 글로벌하게 인정받는 식별자로 작동합니다. 이와 마찬가지로, 싱글턴 패턴은 자바 애플리케이션에서 효율적인 객체 관리를 보장합니다.

### 쉽게 설명하자면

> 특정 클래스의 객체가 하나만 생성되도록 보장합니다.

### 위키백과 설명

> 소프트웨어 엔지니어링에서 싱글턴 패턴은 클래스의 인스턴스화를 하나의 객체로 제한하는 소프트웨어 디자인 패턴입니다. 시스템 전체에서 동작을 조율해야 하는 경우 유용합니다.

## 자바에서 싱글턴 패턴의 프로그래밍 예제

**Joshua Bloch, Effective Java 2nd Edition, p.18**

> 단일 요소 열거형(enum) 타입이 싱글턴을 구현하는 가장 좋은 방법입니다.

```java
public enum EnumIvoryTower {
  INSTANCE
}
사용하려면 다음과 같이 작성합니다:
var enumIvoryTower1 = EnumIvoryTower.INSTANCE;
var enumIvoryTower2 = EnumIvoryTower.INSTANCE;
LOGGER.info("enumIvoryTower1={}", enumIvoryTower1);
LOGGER.info("enumIvoryTower2={}", enumIvoryTower2);

콘솔 출력:
enumIvoryTower1=com.iluwatar.singleton.EnumIvoryTower@1221555852
enumIvoryTower2=com.iluwatar.singleton.EnumIvoryTower@1221555852

자바에서 싱글턴 패턴을 사용할 때
다음과 같은 경우 싱글턴 패턴을 사용하세요:

클래스의 인스턴스가 정확히 하나만 있어야 하며, 잘 알려진 액세스 포인트에서 클라이언트가 이 인스턴스에 접근해야 할 때
단일 인스턴스가 서브클래싱으로 확장 가능해야 하며, 클라이언트가 코드를 수정하지 않고 확장된 인스턴스를 사용할 수 있어야 할 때

자바에서 싱글턴 패턴의 현실 세계 응용 사례
로깅 클래스
애플리케이션 구성 클래스
연결 풀(Connection Pool)
파일 관리자
java.lang.Runtime#getRuntime()
java.awt.Desktop#getDesktop()
java.lang.System#getSecurityManager()
싱글턴 패턴의 장점과 단점
장점:
단일 인스턴스에 대한 제어된 접근 제공
네임스페이스 오염 감소
작업과 표현의 세분화 가능
필요한 경우 여러 인스턴스를 허용
클래스 작업보다 유연함
단점:
글로벌 상태로 인해 테스트가 어려움
복잡한 수명 관리가 필요할 수 있음
동기화 없이 사용할 경우 병렬 처리 환경에서 병목 현상을 초래할 수 있음
관련 자바 디자인 패턴
추상 팩토리(Abstract Factory): 클래스가 단일 인스턴스를 가지도록 보장할 때 사용
팩토리 메서드(Factory Method): 싱글턴 패턴은 생성 로직을 캡슐화하는 팩토리 메서드를 사용하여 구현 가능
프로토타입(Prototype): 인스턴스 생성을 피하고 고유 인스턴스를 관리하며 싱글턴과 함께 작동 가능
 참고 문헌 및 크레딧
Design Patterns: Elements of Reusable Object-Oriented Software
Effective Java
Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software
Java Design Patterns: A Hands-On Experience with Real-World Examples
Refactoring to Patterns




