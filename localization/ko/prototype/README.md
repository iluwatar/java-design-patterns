---
title: Prototype
shortTitle: Prototype
category: Creational
language: ko
tag:
- Gang Of Four
- Instantiation
---

## 의도

prototype 인스턴스를 사용하여 만들 개체의 종류를 지정하고  prototype을 복사하여 새 개체를 만듭니다.

## 설명

먼저 성능 이점을 얻기 위해 prototype 패턴이 사용되지 않는다는 점에 유의해야합니다. 오직 prototype 인스턴스에서 새 개체를 만드는 데만 사용됩니다.

예시

> 복제된 양인 돌리에 대해 기억나십니까? 자세한 내용은 다루지 않겠습니다만 여기서 핵심은 복제에 관한 것입니다.

평범한 말하자면

> 복제를 통해 기존 개체를 기반으로 개체를 만듭니다.

Wikipedia 말에 의하면

> prototype 패턴은 소프트웨어 개발에서 생성 디자인 패턴입니다. 생성 할 개체의 유형이 새 개체를 생성하기 위해 복제되는 prototype 인스턴스에 의해 결정될 때 사용됩니다.

간단히 말해, 객체를 처음부터 만들고 설정하는 문제를 겪는 대신 기존 객체의 복사본을 만들어 필요에 맞게 수정할 수 있습니다.

**프로그램 코드 예제**

Java에서는 `Cloneable` 을 구현하고 `Object` 에서 `clone` 을 오버라이딩하여 쉽게 수행 할 수 있습니다.

```java
class Sheep implements Cloneable {
  private String name;
  public Sheep(String name) { this.name = name; }
  public void setName(String name) { this.name = name; }
  public String getName() { return name; }
  @Override
  public Sheep clone() {
    try {
      return (Sheep)super.clone();
    } catch(CloneNotSuportedException) {
      throw new InternalError();
    }
  }
}
```

그런 다음 아래와 같이 복제할 수 있습니다.

```java
var original = new Sheep("Jolly");
System.out.println(original.getName()); // Jolly

// Clone and modify what is required
var cloned = original.clone();
cloned.setName("Dolly");
System.out.println(cloned.getName()); // Dolly
```

## 클래스 다이어그램

![alt text](./etc/prototype.urm.png)

## 적용 가능성

시스템이 제품의 생성, 구성, 표현 및 표현 방식에 독립적이어야 할 때 prototype 패턴을 사용

- 인스턴스화할 클래스가 런타임에 지정되는 경우 (예 : 동적 로딩)
- 제품의 클래스 계층 구조와 유사한 팩토리의 클래스 계층 구조 구축을 방지해야할 경우
- 클래스의 인스턴스가 몇 가지 다른 상태 조합 중 하나만 가질 수 있는 경우. 적절한 상태로 매번 클래스를 수동으로 인스턴스화하는 것보다 해당하는 수의 prototype을 설치하고 복제하는 것이 더 편리 할 수 있습니다.
- 복제에 비해 개체 생성 비용이 많이 드는 경우.

## 실제 사례

- [java.lang.Object#clone()](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#clone%28%29)

## 크레딧

- [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
- [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
