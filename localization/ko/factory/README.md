---
title: Factory
shortTitle: Factory
category: Creational
language: ko
tag:
- Gang of Four
---

## 또한 ~으로 알려진

- Simple Factory
- Static Factory Method

## 의도

구현 논리를 숨기고 클라이언트 코드가 새 객체를 초기화하는 대신 사용에 집중하도록하기 위해 factory라는 클래스에 캡슐화 된 정적 메서드를 제공합니다.

## 설명

예시

> SQLServer에 연결된 웹 응용 프로그램이 있지만 이제 Oracle로 전환하려고 합니다. 기존 소스 코드를 수정하지 않고 이를 수행하려면 주어진 데이터베이스에 대한 연결을 생성하기 위해 정적 메서드를 호출 할 수 있는 Simple Factory 패턴을 구현해야합니다.

Wikipedia 말에 의하면

> factory는 다른 객체를 생성하기위한 객체입니다. 공식적으로 factory는 다양한 프로토 타입 또는 클래스의 객체를 반환하는 함수 또는 메서드입니다.

**프로그램 코드 예제**

우리는 인터페이스 `Car` 와 두 가지 구현 `Ford` 와 `Ferrari`을 가지고 있습니다.

```java
public interface Car {
  String getDescription();
}

public class Ford implements Car {

  static final String DESCRIPTION = "This is Ford.";

  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}

public class Ferrari implements Car {
   
  static final String DESCRIPTION = "This is Ferrari.";

  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
```

열거형은 우리가 지원하는 자동차 유형을 나타냅니다 ( `Ford` 및 `Ferrari` ).

```java
public enum CarType {
  
  FORD(Ford::new),
  FERRARI(Ferrari::new);
  
  private final Supplier<Car> constructor;
  
  CarType(Supplier<Car> constructor) {
    this.constructor = constructor;
  }
  
  public Supplier<Car> getConstructor() {
    return this.constructor;
  }
}
```

그런 다음 factory 클래스 `CarsFactory` 캡슐화 된 자동차 객체를 만드는 정적 메서드 `getCar` 가 있습니다.

```java
public class CarsFactory {
  
  public static Car getCar(CarType type) {
    return type.getConstructor().get();
  }
}
```

이제 클라이언트 코드에서 factory 클래스를 사용하여 다양한 유형의 자동차를 만들 수 있습니다.

```java
var car1 = CarsFactory.getCar(CarType.FORD);
var car2 = CarsFactory.getCar(CarType.FERRARI);
LOGGER.info(car1.getDescription());
LOGGER.info(car2.getDescription());;
```

프로그램 출력 :

```java
This is Ford.
This Ferrari.
```

## 클래스 다이어그램

![alt text](./etc/factory.urm.png)

## 적용 가능성

객체 생성 및 관리 방법이 아닌 객체 생성에만 관심이있을 때 Simple Factory 패턴을 사용합니다.

장점

- 모든 객체 생성을 한곳에 유지하고 코드베이스에 '새'키 값이 확산되는 것을 방지합니다.
- 느슨하게 결합 된 코드를 작성할 수 있습니다. 주요 장점 중 일부는 더 나은 테스트 가능성, 이해하기 쉬운 코드, 교체 가능한 구성 요소, 확장성 및 격리된 기능을 포함합니다.

단점

- 코드는 생각보다 복잡해집니다.

## 관련 패턴

- [Factory Method](https://java-design-patterns.com/patterns/factory-method/)
- [Factory Kit](https://java-design-patterns.com/patterns/factory-kit/)
- [Abstract Factory](https://java-design-patterns.com/patterns/abstract-factory/)
