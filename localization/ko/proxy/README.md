---
title: Proxy
category: Structural
language: ko
tag:
- Gang Of Four
- Decoupling
---

## 또한 ~으로 알려진

Surrogate

## 의도 

다른 객체에 대한 접근을 제어할 수 있는 대리인 또는 자리 표시자(placeholder)를 제공합니다.

## 설명

실제 예제

> 지역 마법사들이 주문을 공부하러 가는 탑을 상상해 보세요. 상아탑은 프록시를 통해서만 접근할 수 있으며, 프록시는 처음 세 명의 마법사만 들어갈 수 있도록 합니다. 여기서 프록시는 타워의 기능을 나타내며 액세스 제어를 추가합니다.

쉽게 말하자면

> 프록시 패턴을 사용하여, 클래스는 다른 클래스의 기능을 나타냅니다.

Wikipedia에 의하면

> 프록시는 가장 일반적인 형태로 다른 것에 대한 인터페이스로서 기능하는 클래스이다. 프록시는 클라이언트가 백그라운드에서 실제 서비스 객체에 액세스하기 위해 호출하는 래퍼 또는 에이전트 객체입니다. 프록시의 사용은 단순히 실제 객체로 포워딩하거나 추가적인 논리를 제공할 수 있습니다. 프록시에서 실제 객체에 대한 작업이 리소스 집약적일 때, 캐싱하거나 실제 객체에 대한 작업이 호출되기 전에 사전 조건을 확인하는 등의 추가 기능이 제공될 수 있습니다.

**코드 예제**

위로부터 마법사 타워를 예로 들어보겠습니다. 먼저 `WiszrdTower` 인터페이스와 이를 구현한
`IvoryTower` 클래스가 있습니다.

```java
public interface WizardTower {

  void enter(Wizard wizard);
}

@Slf4j
public class IvoryTower implements WizardTower {

  public void enter(Wizard wizard) {
    LOGGER.info("{} enters the tower.", wizard);
  }

}
```

그리고 간단한 `Wizard` 클래스입니다.


```java
public class Wizard {

  private final String name;

  public Wizard(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
```

그리고 `WizardTower`에 대한 엑세스 통제를 추가하기 위해 `WizardTowerProxy`를 가집니다.

```java
@Slf4j
public class WizardTowerProxy implements WizardTower {

  private static final int NUM_WIZARDS_ALLOWED = 3;

  private int numWizards;

  private final WizardTower tower;

  public WizardTowerProxy(WizardTower tower) {
    this.tower = tower;
  }

  @Override
  public void enter(Wizard wizard) {
    if (numWizards < NUM_WIZARDS_ALLOWED) {
      tower.enter(wizard);
      numWizards++;
    } else {
      LOGGER.info("{} is not allowed to enter!", wizard);
    }
  }
}
```

그리고 다음은 타워에 입장하는 시나리오입니다.

```java
var proxy = new WizardTowerProxy(new IvoryTower());
proxy.enter(new Wizard("Red wizard"));
proxy.enter(new Wizard("White wizard"));
proxy.enter(new Wizard("Black wizard"));
proxy.enter(new Wizard("Green wizard"));
proxy.enter(new Wizard("Brown wizard"));
```

프로그램 실행 결과:

```
Red wizard enters the tower.
White wizard enters the tower.
Black wizard enters the tower.
Green wizard is not allowed to enter!
Brown wizard is not allowed to enter!
```

## 클래스 다이어그램

![alt text](./etc/proxy.urm.png "Proxy pattern class diagram")

## 적용 가능성

프록시는 단순한 포인터보다 더 다재다능하거나 정교한 오브젝트 참조가 필요할 때마다 적용할 수 있습니다. 다음은 프록시 패턴을 적용할 수 있는 몇 가지 일반적인 상황입니다.


* 원격 프록시는 다른 주소 공간에 있는 체의 로컬 대리자를 제공합니다.
* 가상 프록시는 요청 시 값비싼 개체를 생성합니다.
* 보호 프록시는 원래 객체에 대한 액세스를 제어합니다. 보호 프록시는 객체가 서로 다른 액세스 권한을 가져야 할 때 유용합니다.

일반적으로 프록시 패턴은 다음과 같이 사용됩니다.

* 다른 객체에 대한 액세스 제어
* 지연 초기화
* 로깅(logging) 구현
* 네트워크 연결 촉진
* 객체에 대한 참조 카운팅

## 튜토리얼

* [Controlling Access With Proxy Pattern](http://java-design-patterns.com/blog/controlling-access-with-proxy-pattern/)

## 실제 사례

* [java.lang.reflect.Proxy](http://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Proxy.html)
* [Apache Commons Proxy](https://commons.apache.org/proper/commons-proxy/)
* Mocking frameworks [Mockito](https://site.mockito.org/),
  [Powermock](https://powermock.github.io/), [EasyMock](https://easymock.org/)

## 관련 패턴

* [Ambassador](https://java-design-patterns.com/patterns/ambassador/)

## 크레딧

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
