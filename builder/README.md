---
layout: pattern
title: Builder
folder: builder
permalink: /patterns/builder/
categories: Creational
tags:
 - Gang of Four
---

## Intent
Separate the construction of a complex object from its
representation so that the same construction process can create different
representations.

## Explanation

Real world example

> Imagine a character generator for a role playing game. The easiest option is to let computer create the character for you. But if you want to select the character details like profession, gender, hair color etc. the character generation becomes a step-by-step process that completes when all the selections are ready.

In plain words

> Allows you to create different flavors of an object while avoiding constructor pollution. Useful when there could be several flavors of an object. Or when there are a lot of steps involved in creation of an object.

Wikipedia says

> The builder pattern is an object creation software design pattern with the intentions of finding a solution to the telescoping constructor anti-pattern.

Having said that let me add a bit about what telescoping constructor anti-pattern is. At one point or the other we have all seen a constructor like below:

```java
public Hero(Profession profession, String name, HairType hairType, HairColor hairColor, Armor armor, Weapon weapon) {
}
```

As you can see the number of constructor parameters can quickly get out of hand and it might become difficult to understand the arrangement of parameters. Plus this parameter list could keep on growing if you would want to add more options in future. This is called telescoping constructor anti-pattern.

**Programmatic Example**

The sane alternative is to use the Builder pattern. First of all we have our hero that we want to create

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

And then we have the builder

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

And then it can be used as:

```java
var mage = new Hero.Builder(Profession.MAGE, "Riobard").withHairColor(HairColor.BLACK).withWeapon(Weapon.DAGGER).build();
```

## Class diagram
![alt text](./etc/builder.urm.png "Builder class diagram")

## Applicability
Use the Builder pattern when

* the algorithm for creating a complex object should be independent of the parts that make up the object and how they're assembled
* the construction process must allow different representations for the object that's constructed

## Real world examples

* [java.lang.StringBuilder](http://docs.oracle.com/javase/8/docs/api/java/lang/StringBuilder.html)
* [java.nio.ByteBuffer](http://docs.oracle.com/javase/8/docs/api/java/nio/ByteBuffer.html#put-byte-) as well as similar buffers such as FloatBuffer, IntBuffer and so on.
* [java.lang.StringBuffer](http://docs.oracle.com/javase/8/docs/api/java/lang/StringBuffer.html#append-boolean-)
* All implementations of [java.lang.Appendable](http://docs.oracle.com/javase/8/docs/api/java/lang/Appendable.html)
* [Apache Camel builders](https://github.com/apache/camel/tree/0e195428ee04531be27a0b659005e3aa8d159d23/camel-core/src/main/java/org/apache/camel/builder)
* [Apache Commons Option.Builder](https://commons.apache.org/proper/commons-cli/apidocs/org/apache/commons/cli/Option.Builder.html)

## 실무 예제 (Practical Examples)

### 1. UserDTO - 사용자 정보 DTO

API 요청/응답에서 사용되는 복잡한 사용자 정보 객체를 Builder 패턴으로 구성합니다.

```java
UserDTO user = UserDTO.builder()
    .id(1L)
    .username("john.doe")
    .email("john@example.com")
    .firstName("John")
    .lastName("Doe")
    .age(30)
    .phoneNumber("010-1234-5678")
    .address("123 Main St, Seoul")
    .addRole("USER")
    .addRole("ADMIN")
    .active(true)
    .build();

System.out.println(user.getFullName()); // "John Doe"
System.out.println(user.hasRole("ADMIN")); // true
```

**실무 활용:**
- API 요청/응답 DTO 생성
- 데이터베이스 엔티티에서 DTO로 변환
- 테스트 데이터 생성
- 복잡한 사용자 프로필 구성

### 2. HttpRequest - HTTP 요청 빌더

REST API 호출을 위한 HTTP 요청을 체계적으로 구성합니다.

```java
HttpRequest request = HttpRequest.builder()
    .post()
    .url("https://api.example.com/users")
    .addHeader("Content-Type", "application/json")
    .addHeader("Authorization", "Bearer token123")
    .addQueryParam("page", "1")
    .addQueryParam("size", "10")
    .jsonBody("{\"name\": \"John Doe\"}")
    .timeout(5000)
    .build();

String response = request.execute();
```

**실무 활용:**
- REST API 클라이언트 구현
- HTTP 라이브러리 래퍼
- 마이크로서비스 간 통신
- 테스트용 HTTP 요청 생성

### 3. EmailMessage - 이메일 메시지 빌더

복잡한 이메일 메시지를 단계적으로 구성합니다.

```java
EmailMessage email = EmailMessage.builder()
    .from("sender@example.com")
    .to("recipient@example.com")
    .addCc("manager@example.com")
    .addBcc("admin@example.com")
    .subject("Welcome to our service!")
    .body("<h1>Welcome!</h1><p>Thank you for joining us.</p>")
    .html(true)
    .priority(Priority.HIGH)
    .addAttachment("/path/to/document.pdf")
    .build();

boolean sent = email.send();
System.out.println("Total recipients: " + email.getTotalRecipientCount());
```

**실무 활용:**
- 이메일 발송 시스템
- 알림 서비스
- 마케팅 이메일 생성
- 시스템 알림 메일

## Builder 패턴의 실무 장점

1. **가독성**: 복잡한 객체 생성 과정을 명확하게 표현
2. **유연성**: 선택적 파라미터를 쉽게 처리
3. **불변성**: Immutable 객체 생성으로 Thread-safe 보장
4. **유효성 검증**: build() 메서드에서 일괄 유효성 검증
5. **유지보수성**: 새로운 속성 추가가 용이

## 테스트 실행

각 실무 예제에는 comprehensive한 테스트 코드가 포함되어 있습니다:

```bash
# 모든 테스트 실행
mvn test

# 특정 테스트만 실행
mvn test -Dtest=UserDTOTest
mvn test -Dtest=HttpRequestTest
mvn test -Dtest=EmailMessageTest
```

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](http://www.amazon.com/Design-Patterns-Elements-Reusable-Object-Oriented/dp/0201633612)
* [Effective Java (2nd Edition)](http://www.amazon.com/Effective-Java-Edition-Joshua-Bloch/dp/0321356683)
