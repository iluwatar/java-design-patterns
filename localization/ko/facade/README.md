---
title: Facade
shortTitle: Facade
category: Structural
language: ko
tag:
 - Gang Of Four
 - Decoupling
---

## 의도

하위 시스템의 인터페이스 집합에 통합 인터페이스를 제공합니다. 퍼싸드(Facade)는 상위 레벨을 정의합니다.
하위시스템을 보다 쉽게 사용할 수 있는 인터페이스.

## 설명

실제 예제

> 금광은 어떻게 운영되나요? "광부들이 저 아래로 내려가서 금을 캐죠" 라고 당신은 말합니다.
> 당신은 금광이 외부에 제공하는 간단한 인터페이스를 사용하고 있기 때문에 그렇게 믿습니다.
> 그러나 내부적으로는 많은 작업을 수행해야 합니다. 복잡한 하위 시스템에 대한 이 간단한 인터페이스는
> 퍼싸드(Facade)입니다.

쉽게 말하자면

> 퍼싸드(Facade) 패턴은 복잡한 하위 시스템에 대한 간소화된 인터페이스를 제공합니다.

Wikipedia에 의하면

> 퍼싸드(Facade)는 클래스 라이브러리와 같은 더 큰 코드 본운에 간단한 인터페이스를 제공하는 객체입니다.

**코드 예제**

위의 금광을 예로 들어 보겠습니다. 여기에는 드워프 광산 작업자 계층 구조가 있습니다.
먼저, 베이스 클래스 `DwarvenMineWorker`가 있습니다.:

```java

@Slf4j
public abstract class DwarvenMineWorker {

    public void goToSleep() {
        LOGGER.info("{} goes to sleep.", name());
    }

    public void wakeUp() {
        LOGGER.info("{} wakes up.", name());
    }

    public void goHome() {
        LOGGER.info("{} goes home.", name());
    }

    public void goToMine() {
        LOGGER.info("{} goes to the mine.", name());
    }

    private void action(Action action) {
        switch (action) {
            case GO_TO_SLEEP -> goToSleep();
            case WAKE_UP -> wakeUp();
            case GO_HOME -> goHome();
            case GO_TO_MINE -> goToMine();
            case WORK -> work();
            default -> LOGGER.info("Undefined action");
        }
    }

    public void action(Action... actions) {
        Arrays.stream(actions).forEach(this::action);
    }

    public abstract void work();

    public abstract String name();

    enum Action {
        GO_TO_SLEEP, WAKE_UP, GO_HOME, GO_TO_MINE, WORK
    }
}
```

그 다음 구체적인 드워프 클래스인  `DwarvenTunnelDigger`, `DwarvenGoldDigger` 와
`DwarvenCartOperator` 입니다:

```java
@Slf4j
public class DwarvenTunnelDigger extends DwarvenMineWorker {

  @Override
  public void work() {
    LOGGER.info("{} creates another promising tunnel.", name());
  }

  @Override
  public String name() {
    return "Dwarven tunnel digger";
  }
}

@Slf4j
public class DwarvenGoldDigger extends DwarvenMineWorker {

  @Override
  public void work() {
    LOGGER.info("{} digs for gold.", name());
  }

  @Override
  public String name() {
    return "Dwarf gold digger";
  }
}

@Slf4j
public class DwarvenCartOperator extends DwarvenMineWorker {

  @Override
  public void work() {
    LOGGER.info("{} moves gold chunks out of the mine.", name());
  }

  @Override
  public String name() {
    return "Dwarf cart operator";
  }
}

```

이 모든 금광의 일꾼들을 운영하기 위해 `DwarvenGoldmineFacade` 가 있습니다.:

```java
public class DwarvenGoldmineFacade {

  private final List<DwarvenMineWorker> workers;

  public DwarvenGoldmineFacade() {
      workers = List.of(
            new DwarvenGoldDigger(),
            new DwarvenCartOperator(),
            new DwarvenTunnelDigger());
  }

  public void startNewDay() {
    makeActions(workers, DwarvenMineWorker.Action.WAKE_UP, DwarvenMineWorker.Action.GO_TO_MINE);
  }

  public void digOutGold() {
    makeActions(workers, DwarvenMineWorker.Action.WORK);
  }

  public void endDay() {
    makeActions(workers, DwarvenMineWorker.Action.GO_HOME, DwarvenMineWorker.Action.GO_TO_SLEEP);
  }

  private static void makeActions(Collection<DwarvenMineWorker> workers,
      DwarvenMineWorker.Action... actions) {
    workers.forEach(worker -> worker.action(actions));
  }
}
```

이제 퍼싸드(facade) 패턴을 사용해 봅시다:

```java
var facade = new DwarvenGoldmineFacade();
facade.startNewDay();
facade.digOutGold();
facade.endDay();
```

프로그램 실행 결과:

```java
// Dwarf gold digger wakes up.
// Dwarf gold digger goes to the mine.
// Dwarf cart operator wakes up.
// Dwarf cart operator goes to the mine.
// Dwarven tunnel digger wakes up.
// Dwarven tunnel digger goes to the mine.
// Dwarf gold digger digs for gold.
// Dwarf cart operator moves gold chunks out of the mine.
// Dwarven tunnel digger creates another promising tunnel.
// Dwarf gold digger goes home.
// Dwarf gold digger goes to sleep.
// Dwarf cart operator goes home.
// Dwarf cart operator goes to sleep.
// Dwarven tunnel digger goes home.
// Dwarven tunnel digger goes to sleep.
```

## 클래스 다이어그램

![alt text](./etc/facade.urm.png "Facade pattern class diagram")

## 적용 가능성

다음과 같은 경우 파사드 패턴을 사용합니다.

* 복잡한 하위시스템에 간단한 인터페이스를 제공하려는 경우, 하위시스템은 점점 더 복잡해지는 경우가 많습니다. 대부분의 패턴들은 적용되었을 때,
  클래스가 점점 더 작아집니다. 이것은 하위 시스템의 재사용성을 늘리고 커스터마이징을 쉽게 해주지만, 커스터마이징이 필요없는 클라이언트는 사용하기
  어려워집니다. 퍼싸드(Facade)는 하위시스템의 간단한 보기를 제공할 수 있습니다. 오직 커스터마이징이 필요한 클라이언트들만 퍼싸드(Facade) 너머를
  살펴볼 필요가 있습니다.
* 클라이언트와 추상화의 구현 클래스 사이에는 많은 종속성이 있습니다. 퍼싸드(Facade)를 도입해 하위 시스템을 클라이언트 및 다른 하위 시스템으로부터
  분리하면 다음과 같은 이점이 있습니다.
  하위 시스템의 독립성과 이식성을 촉진합니다.
* 하위 시스템을 계층화하려고 합니다. 퍼싸드(Facade)를 이용해 각 하위 시스템 계층에 대한 진입점을 정희합니다.
  하위 시스템이 종속적인 경우 하위 시스템 간의 종속성을 단순화할 수 있습니다.
  퍼싸드(Facade)를 통해서만 서로 통신할 수 있습니다.

## 튜토리얼

*[DigitalOcean](https://www.digitalocean.com/community/tutorials/facade-design-pattern-in-java)
* [Refactoring Guru](https://refactoring.guru/design-patterns/facade)
* [GeekforGeeks](https://www.geeksforgeeks.org/facade-design-pattern-introduction/)
* [Tutorialspoint](https://www.tutorialspoint.com/design_pattern/facade_pattern.htm)



## 크레딧

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
