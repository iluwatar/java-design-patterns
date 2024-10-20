---
title: Builder
shortTitle: Builder
category: Creational
language: ko
tag:
- Gang of Four
---

## 의도 

동일한 구성 프로세스가 다른 표현을 생성할 수 있도록 복잡한 객체의 구성을 해당 표현과 분리합니다.

## 설명

실제 예제

> 롤플레잉 게임의 캐릭터 생성기를 상상해 보세요. 가장 쉬운 옵션은 컴퓨터가 사용자를 위해 문자를 만들도록 하는 것입니다. 직업, 성별, 머리색 등 캐릭터 세부 정보를 수동으로 선택하려면 캐릭터 생성은 모든 선택이 준비되었을 때 완료되는 단계별 프로세스가 됩니다.

쉽게 말하자면

> 생성자의 오염을 방지하면서 객체의 다른 맛들을 만들 수 있습니다. 여러 가지 맛의 객체가 있을 때 유용합니다. 또는 개체를 만드는 데 많은 단계가 수반될 때 유용합니다.

Wikipedia에 의하면

> 빌더 패턴은 점층적 생성자 안티-패턴(telescoping constructor anti-pattern)에 대한 해결책을 찾기 위한 목적을 가진 객체 생성 소프트웨어 디자인 패턴이다.

그렇긴 하지만, 점층적 생성자 안티-패턴(telescoping constructor anti-pattern)이 무엇인지 조금 더 설명하겠습니다. 어느 시점에서, 우리는 모두 다음과 같은 생성자를 보았습니다.

```java
public Hero(Profession profession, String name, HairType hairType, HairColor hairColor, Armor armor, Weapon weapon) {
}
```

보시다시피 생성자 매개 변수의 수는 빠르게 감당할 수 없게 되고 매개 변수의 배치를 이해하는 것이 어려워질 수 있습니다. 또한 이 매개 변수 목록은 나중에 더 많은 옵션을 추가하려는 경우 계속 증가할 수 있습니다. 이를 점층적 생성자 안티-패턴(telescoping constructor anti-pattern)이라고 합니다.

**코드 예제**

올바른 대안은 빌더 패턴을 사용하는 것입니다. 우선, 우리는 우리가 창조하고 싶은 영웅을 가지고 있습니다. :


```java
public final class Hero {
  private final Profession profession;
  private final String name;
  private final HairType hairType;
  private final HairColor hairColor;
  private final Armor armor;
  private final Weapon weapon;

  private Hero(Builder builder) {
    this.profession = builder.profession;
    this.name = builder.name;
    this.hairColor = builder.hairColor;
    this.hairType = builder.hairType;
    this.weapon = builder.weapon;
    this.armor = builder.armor;
  }
}
```

그리고 우리는 빌더를 가지고 있습니다. :

```java
  public static class Builder {
    private final Profession profession;
    private final String name;
    private HairType hairType;
    private HairColor hairColor;
    private Armor armor;
    private Weapon weapon;

    public Builder(Profession profession, String name) {
      if (profession == null || name == null) {
        throw new IllegalArgumentException("profession and name can not be null");
      }
      this.profession = profession;
      this.name = name;
    }

    public Builder withHairType(HairType hairType) {
      this.hairType = hairType;
      return this;
    }

    public Builder withHairColor(HairColor hairColor) {
      this.hairColor = hairColor;
      return this;
    }

    public Builder withArmor(Armor armor) {
      this.armor = armor;
      return this;
    }

    public Builder withWeapon(Weapon weapon) {
      this.weapon = weapon;
      return this;
    }

    public Hero build() {
      return new Hero(this);
    }
  }
```

그런 다음, 다음과 같이 사용할 수 있습니다.


```java
var mage = new Hero.Builder(Profession.MAGE, "Riobard").withHairColor(HairColor.BLACK).withWeapon(Weapon.DAGGER).build();
```

## 클래스 다이어그램

![alt text](./etc/builder.urm.png "Builder class diagram")

## 적용 가능성

다음과 같은 경우 빌더 패턴을 사용합니다.

* 복잡한 객체를 만드는 알고리즘은 객체를 구성하는 부품과 이들이 조립되는 방법에 독립적이어야 한다.
* 구축 프로세스는 구성된 객체에 대해 서로 다른 표현을 허용해야 합니다.

## 튜토리얼

* [Refactoring Guru](https://refactoring.guru/design-patterns/builder)
* [Oracle Blog](https://blogs.oracle.com/javamagazine/post/exploring-joshua-blochs-builder-design-pattern-in-java)
* [Journal Dev](https://www.journaldev.com/1425/builder-design-pattern-in-java)

## 실제 사례

* [java.lang.StringBuilder](http://docs.oracle.com/javase/8/docs/api/java/lang/StringBuilder.html)
* [java.nio.ByteBuffer](http://docs.oracle.com/javase/8/docs/api/java/nio/ByteBuffer.html#put-byte-) as well as similar buffers such as FloatBuffer, IntBuffer and so on.
* [java.lang.StringBuffer](http://docs.oracle.com/javase/8/docs/api/java/lang/StringBuffer.html#append-boolean-)
* All implementations of [java.lang.Appendable](http://docs.oracle.com/javase/8/docs/api/java/lang/Appendable.html)
* [Apache Camel builders](https://github.com/apache/camel/tree/0e195428ee04531be27a0b659005e3aa8d159d23/camel-core/src/main/java/org/apache/camel/builder)
* [Apache Commons Option.Builder](https://commons.apache.org/proper/commons-cli/apidocs/org/apache/commons/cli/Option.Builder.html)

## 크레딧

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Effective Java](https://www.amazon.com/gp/product/0134685997/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0134685997&linkCode=as2&tag=javadesignpat-20&linkId=4e349f4b3ff8c50123f8147c828e53eb)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
