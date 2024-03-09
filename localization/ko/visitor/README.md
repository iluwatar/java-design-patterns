---
title: Visitor
category: Behavioral
language: ko
tag:
 - Gang of Four
---

## 의도


객체 구조의 요소들에 대해 수행할 작업을 나타냅니다. 방문자(Visitor)는 
연산하는 요소의 클래스를 변경하지 않고 새 연산을 정의할 수 있게 해줍니다.
## 설명

살제 예제

> 육군 부대의 편성을 생각해보세요. 지휘관은 아래에 2명의 중사를 휘하에 두고 있고 각 중사들은
> 3명의 병사를 휘하에 두고 있습니다. 위계에 따라 방문자(Visitor) 패턴이 구현되는 것을 함께 생각하면,
> 우리는 지휘관, 중사 병사 또는 그들 모두와 상호작용하는 새로운 게체를 쉽게 만들 수 있습니다.

쉽게 말하자면

> 방문자(Visitor) 패턴은 데이터 구조의 노드에서 수행할 수 있는 작업들을 정의합니다.

Wikipedia에 의하면

> 객체 지향 프로그래밍 및 소프트웨어 엔지니어링에에서, 방문자(Visitor) 패턴은
> 알고리즘이 동작하는 객체 구조로부터 알고리즘을 분리하는 것,
> 이 분리를 통해, 구조의 변경 없이 기본 객체 구조에 새로운 연산을 추가할 수 있도록 하는 방법입니다.

**코드 예제**

위의 육군 부대를 예로 들겠습니다. 먼저 `Unit` 추상화 클래스와 `UnitVisitor` 인터페이스가 있습니다.

```java
public abstract class Unit {

  private final Unit[] children;

  public Unit(Unit... children) {
    this.children = children;
  }

  public void accept(UnitVisitor visitor) {
    Arrays.stream(children).forEach(child -> child.accept(visitor));
  }
}

public interface UnitVisitor {

  void visit(Soldier soldier);

  void visit(Sergeant sergeant);

  void visit(Commander commander);
}
```

그리고 이를 구현한 `Commander`,`Sergant`,`Soilder` 구현 클래스입니다.

```java
public class Commander extends Unit {

  public Commander(Unit... children) {
    super(children);
  }

  @Override
  public void accept(UnitVisitor visitor) {
    visitor.visit(this);
    super.accept(visitor);
  }

  @Override
  public String toString() {
    return "commander";
  }
}

public class Sergeant extends Unit {

  public Sergeant(Unit... children) {
    super(children);
  }

  @Override
  public void accept(UnitVisitor visitor) {
    visitor.visit(this);
    super.accept(visitor);
  }

  @Override
  public String toString() {
    return "sergeant";
  }
}

public class Soldier extends Unit {

  public Soldier(Unit... children) {
    super(children);
  }

  @Override
  public void accept(UnitVisitor visitor) {
    visitor.visit(this);
    super.accept(visitor);
  }

  @Override
  public String toString() {
    return "soldier";
  }
}
```

그리고 `UnitVisiotr`를 구현한 `CommanderVisitor`,`SergantVisitor`,`Soilder` 구현 클래스입니다.

```java
@Slf4j
public class CommanderVisitor implements UnitVisitor {

  @Override
  public void visit(Soldier soldier) {
    // Do nothing
  }

  @Override
  public void visit(Sergeant sergeant) {
    // Do nothing
  }

  @Override
  public void visit(Commander commander) {
    LOGGER.info("Good to see you {}", commander);
  }
}

@Slf4j
public class SergeantVisitor implements UnitVisitor {

  @Override
  public void visit(Soldier soldier) {
    // Do nothing
  }

  @Override
  public void visit(Sergeant sergeant) {
    LOGGER.info("Hello {}", sergeant);
  }

  @Override
  public void visit(Commander commander) {
    // Do nothing
  }
}

@Slf4j
public class SoldierVisitor implements UnitVisitor {

  @Override
  public void visit(Soldier soldier) {
    LOGGER.info("Greetings {}", soldier);
  }

  @Override
  public void visit(Sergeant sergeant) {
    // Do nothing
  }

  @Override
  public void visit(Commander commander) {
    // Do nothing
  }
}
```

마지막으로, 우린 방문자(Visitor)의 위력을 볼 수 있습니다.

```java
commander.accept(new SoldierVisitor());
commander.accept(new SergeantVisitor());
commander.accept(new CommanderVisitor());
```

프로그램 실행 결과:

```
Greetings soldier
Greetings soldier
Greetings soldier
Greetings soldier
Greetings soldier
Greetings soldier
Hello sergeant
Hello sergeant
Good to see you commander
```

## 클래스 다이어그램

![alt text](./etc/visitor_1.png "Visitor")

## 적용 가능성

일반적으로 방문자(Visitor) 패턴은 다음과 같은 상황에 사용할 수 있습니다.

* 객체 구조에 다양한 인터페이스를 가진 객체의 클래스가 많이 포함되어 있고, 객체들의 구현 클래스에 따라 연산되게 하고 싶을 때 사용할 수 있습니다.
* 객체 구조에 있는 객체들에 대해 개별적이고 서로 연관이 없는 연산들을 수행하고 싶을 때, 이러한 연산들로 인해 클래스가 "오염(Polluting)"되는 것을 방지하고자 합니다. 이럴 때, 방문자(Visitor)를 사용해 관련 연산을 하나의 클래스에 정의하여 유지할 수 있습니다. 객체 구조가 여러 응용 프로그램에서 공유되는 경우에는 방문자(Visitor)를 사용해 필요한 응용 프로그램에만 연산을 추가할 수 있습니다.
* 객체 구조를 정의하는 클래스들은 거의 변경되지 않지만, 종종 새로운 연산을 정의해야 할 때가 있습니다. 객체 구조 클래스들을 변경하려면 모둔 방문자(Visitor)에 데한 인터페이스를 재정의해야 하므로 비용이 많이 들수 있습니다. 객체 구조 클래스가 자주 변경되는 경우 해당 클래스의 연산을 정의하는 것이 더 나을 수 있습니다.

## 튜토리얼

* [Refactoring Guru](https://refactoring.guru/design-patterns/visitor)
* [Dzone](https://dzone.com/articles/design-patterns-visitor)
* [Sourcemaking](https://sourcemaking.com/design_patterns/visitor)

## 실제 사례

* [Apache Wicket](https://github.com/apache/wicket) 구성 요소, [MarkupContainer](https://github.com/apache/wicket/blob/b60ec64d0b50a611a9549809c9ab216f0ffa3ae3/wicket-core/src/main/java/org/apache/wicket/MarkupContainer.java)를 참조하세요.
* [javax.lang.model.element.AnnotationValue](http://docs.oracle.com/javase/8/docs/api/javax/lang/model/element/AnnotationValue.html) 그리고 [AnnotationValueVisitor](http://docs.oracle.com/javase/8/docs/api/javax/lang/model/element/AnnotationValueVisitor.html)
* [javax.lang.model.element.Element](http://docs.oracle.com/javase/8/docs/api/javax/lang/model/element/Element.html) 그리고 [Element Visitor](http://docs.oracle.com/javase/8/docs/api/javax/lang/model/element/ElementVisitor.html)
* [java.nio.file.FileVisitor](http://docs.oracle.com/javase/8/docs/api/java/nio/file/FileVisitor.html)

## 크레딧

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
