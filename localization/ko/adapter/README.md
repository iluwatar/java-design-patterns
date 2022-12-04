---
title: Adapter
category: Structural
language: ko
tag:
- Gang of Four
---

## 또한 ~으로 알려진

Wrapper

## 의도

클래스의 인터페이스를 클라이언트가 기대하는 다른 인터페이스로 변환합니다. adapter를 사용하면 호환되지 않는 인터페이스로 인해 같이 쓸 수 없는 클래스를 함께 작동 할 수 있습니다.

## 설명

예시

> 메모리 카드에 몇 장의 사진이 있고 컴퓨터로 전송해야한다고 생각하십시오. 이들을 전송하려면 컴퓨터에 메모리 카드를 연결할 수 있도록 컴퓨터 포트와 호환되는 어댑터가 필요합니다. 이 경우 카드 리더는 어댑터입니다. 또 다른 예는 유명한 전원 어댑터입니다. 세 갈래 플러그는 두 갈래 콘센트에 연결할 수 없습니다. 두 갈래 콘센트와 호환되는 전원 어댑터를 사용해야합니다. 또 다른 예는 한 사람이 말한 단어를 다른 사람에게 번역하는 번역가입니다.

평범하게 말하자면

> adapter 패턴을 사용하면 호환되지 않는 개체를 adapter에 연결하여 다른 클래스와 호환되도록 할 수 있습니다.

Wikipedia 말에 의하면

> 소프트웨어 엔지니어링에서 adapter 패턴은 기존 클래스의 인터페이스를 다른 인터페이스로 사용할 수 있도록 하는 소프트웨어 디자인 패턴입니다. 소스 코드를 수정하지 않고 기존 클래스가 다른 클래스와 함께 작동하도록 만드는 데 자주 사용됩니다.

**프로그램 코드 예제**

조정 보트만 사용할 수 있고 전혀 항해할 수 없는 선장을 생각해보십시오.

먼저 `RowingBoat` 및 `FishingBoat` 인터페이스가 있습니다.

```java
public interface RowingBoat {
  void row();
}

public class FishingBoat {
  private static final Logger LOGGER = LoggerFactory.getLogger(FishingBoat.class);
  public void sail() {
    LOGGER.info("The fishing boat is sailing");
  }
}
```

그리고 선장은 `RowingBoat` 인터페이스를 이동할 수 있게 구현했습니다.

```java
public class Captain {

  private final RowingBoat rowingBoat;
  // default constructor and setter for rowingBoat
  public Captain(RowingBoat rowingBoat) {
    this.rowingBoat = rowingBoat;
  }

  public void row() {
    rowingBoat.row();
  }
}
```

이제 해적이오고 있고 우리 선장이 탈출해야하는데 어선만 있습니다. 선장이 조정 보트 기술로 어선을 조작 할 수있는 adapter를 만들어야합니다.

```java
public class FishingBoatAdapter implements RowingBoat {

  private static final Logger LOGGER = LoggerFactory.getLogger(FishingBoatAdapter.class);

  private final FishingBoat boat;

  public FishingBoatAdapter() {
    boat = new FishingBoat();
  }

  @Override
  public void row() {
    boat.sail();
  }
}
```

이제 `Captain` 은 `FishingBoat` 를 사용하여 해적을 탈출 할 수 있습니다.

```java
var captain = new Captain(new FishingBoatAdapter());
captain.row();
```

## 클레스 다이어그램

![alt text](./etc/adapter.urm.png)

## 적용 가능성

다음과 같은 경우 adapter 패턴을 사용합니다.

- 기존 클래스를 사용 하려는데 해당 인터페이스가 필요한 클래스와 일치하지 않습니다.
- 관련이 없거나 예상치 못한 클래스, 즉 호환되는 인터페이스가 반드시 필요하지 않은 클래스와 협력하는 재사용 가능한 클래스를 만들고 싶습니다.
- 기존의 여러 하위 클래스를 사용해야하지만 모든 하위 클래스를 하위 클래스로 지정하여 인터페이스를 조정하는 것은 비현실적입니다. 개체 adapter는 부모 클래스의 인터페이스를 조정할 수 있습니다.
- 타사 라이브러리를 사용하는 대부분의 응용 프로그램은 adapter를 응용 프로그램과 타사 라이브러리 사이의 중간 계층으로 사용하여 라이브러리에서 응용 프로그램을 분리합니다. 다른 라이브러리를 사용해야하는 경우 애플리케이션 코드를 변경할 필요없이 새 라이브러리 용 adapter만 필요합니다.

## 결과 :

클래스 및 개체 adapter에는 서로 다른 장단점이 있습니다. <br>클래스 adapter

- 구체적인 Adaptee 클래스를 커밋하여 Adaptee를 Target에 적용합니다. 결과적으로 클래스와 모든 하위 클래스를 조정하려는 경우 클래스 adapter가 작동하지 않습니다.
- adapter는 Adaptee의 하위 클래스이기 때문에 Adaptee의 일부 동작을 오버라이드합니다.
- 하나의 객체만 생성하고 adaptee를 얻기위해 위해 추가 포인터 간접 지정이 필요하지 않습니다.

개체 adapter

- 하나의 adapter가 많은 Adaptees, 즉 Adaptee 자체와 모든 하위 클래스 (있는 경우)와 함께 작동하도록합시다. adapter는 한 번에 모든 어댑터에 기능을 추가 할 수도 있습니다.
- Adaptee 동작을 오버라이드하기가 더 어렵습니다. Adaptee를 서브 클래싱하고 어댑터가 Adaptee 자체가 아닌 서브 클래스를 참조하도록 해야합니다.

## 실제 사례

- [java.util.Arrays#asList()](http://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#asList%28T...%29)
- [java.util.Collections#list()](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#list-java.util.Enumeration-)
- [java.util.Collections#enumeration()](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#enumeration-java.util.Collection-)
- [javax.xml.bind.annotation.adapters.XMLAdapter](http://docs.oracle.com/javase/8/docs/api/javax/xml/bind/annotation/adapters/XmlAdapter.html#marshal-BoundType-)

## 크레딧

- [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
- [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
- [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
- [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
