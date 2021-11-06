---
layout: pattern
title: Singleton
folder: singleton
permalink: /patterns/singleton/ko
categories: Creational
language: ko
tags:
- Gang of Four
---

## 의도

클래스에 인스턴스가 하나만 있는지 확인하고 이에 대한 전역 access point을 제공합니다.

## 설명

예시

> 마법사들이 마법을 연구하는 상아탑은 단 하나뿐입니다. 마법사는 항상 동일한 마법의 상아탑을 사용합니다. 여기서 상아탑은 singleton입니다.

평범하게 말하자면

> 특정 클래스의 개체가 하나만 생성되도록합니다.

Wikipedia 말에 의하면

> 소프트웨어 엔지니어링에서 singleton 패턴은 클래스의 인스턴스화를 하나의 객체로 제한하는 소프트웨어 디자인 패턴입니다. 이는 시스템 전체에서 작업을 조정하는 데 정확히 하나의 개체가 필요할 때 유용합니다.

**프로그램 코드 예제**

Joshua Bloch, Effective Java 2nd Edition p.18

> 단일 요소 열거형은 singleton을 구현하는 가장 좋은 방법입니다.

```java
public enum EnumIvoryTower {
  INSTANCE
}
```

그런 다음 사용하려면 :

```java
var enumIvoryTower1 = EnumIvoryTower.INSTANCE;
var enumIvoryTower2 = EnumIvoryTower.INSTANCE;
assertEquals(enumIvoryTower1, enumIvoryTower2); // true
```

## 클래스 다이어그램

![alt text](https://github.com/iluwatar/java-design-patterns/blob/master/singleton/etc/singleton.urm.png)

## 적용 가능성

다음과 같은 경우 Singleton 패턴을 사용합니다.

- 정확히 하나의 클래스 인스턴스가 있어야하며 잘 알려진 access point에서 클라이언트에 접근할 수 있어야합니다.
- 단일 인스턴스가 서브 클래싱으로 확장 가능해야하고 클라이언트가 코드를 수정하지 않고 확장 인스턴스를 사용할 수 있어야 하는 경우

## 일반적인 사용 사례

- 로깅 클래스
- 데이터베이스에 대한 연결 관리
- 파일 관리자

## 실제 사례

- [java.lang.Runtime#getRuntime()](http://docs.oracle.com/javase/8/docs/api/java/lang/Runtime.html#getRuntime%28%29)
- [java.awt.Desktop#getDesktop()](http://docs.oracle.com/javase/8/docs/api/java/awt/Desktop.html#getDesktop--)
- [java.lang.System#getSecurityManager()](http://docs.oracle.com/javase/8/docs/api/java/lang/System.html#getSecurityManager--)

## 결과

- 자체 생성 및 수명주기를 제어하여 SRP (Single Responsibility Principle)를 위반합니다.
- 이 개체가 사용하는 개체와 리소스가 할당 해제되는 것을 방지하는 전역 공유 인스턴스를 사용하도록 권장합니다.
- 밀접하게 연결된 코드를 만듭니다. Singleton의 클라이언트는 테스트하기가 어려워집니다.
- Singleton의 하위 클래스를 만드는 것이 거의 불가능합니다.

## 크레딧

- [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
- [Effective Java](https://www.amazon.com/gp/product/0134685997/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0134685997&linkCode=as2&tag=javadesignpat-20&linkId=4e349f4b3ff8c50123f8147c828e53eb)
- [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
- [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
