---
title: Facade
category: Structural
language: zh
tag:
 - Gang Of Four
 - Decoupling
---

## 目的
为一个子系统中的一系列接口提供一个统一的接口。外观定义了一个更高级别的接口以便子系统更容易使用。

## 解释

真实世界的例子

> 一个金矿是怎么工作的？“嗯，矿工下去然后挖金子！”你说。这是你所相信的因为你在使用一个金矿对外提供的一个简单接口，在内部它要却要做很多事情。这个简单的接口对复杂的子系统来说就是一个外观。

用通俗的话说

> 外观模式为一个复杂的子系统提供一个简单的接口。

维基百科说

> 外观是为很大体量的代码（比如类库）提供简单接口的一种对象。

**程序示例**

使用上面金矿的例子。这里我们有矮人的矿工等级制度。

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

为了操纵所有这些矿工我们有了这个外观

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

现在来使用外观

```java
DwarvenGoldmineFacade facade = new DwarvenGoldmineFacade();
facade.startNewDay();
// Dwarf gold digger wakes up.
// Dwarf gold digger goes to the mine.
// Dwarf cart operator wakes up.
// Dwarf cart operator goes to the mine.
// Dwarven tunnel digger wakes up.
// Dwarven tunnel digger goes to the mine.
facade.digOutGold();
// Dwarf gold digger digs for gold.
// Dwarf cart operator moves gold chunks out of the mine.
// Dwarven tunnel digger creates another promising tunnel.
facade.endDay();
// Dwarf gold digger goes home.
// Dwarf gold digger goes to sleep.
// Dwarf cart operator goes home.
// Dwarf cart operator goes to sleep.
// Dwarven tunnel digger goes home.
// Dwarven tunnel digger goes to sleep.
```

## 类图
![alt text](./etc/facade.urm.png "Facade pattern class diagram")

## 适用性
使用外观模式当

* 你想为一个复杂的子系统提供一个简单的接口。随着子系统的发展，它们通常会变得更加复杂。多数模式在应用时会导致更多和更少的类。这使子系统更可重用，更易于自定义，但是对于不需要自定义它的客户来说，使用它也变得更加困难。 外观可以提供子系统的简单默认视图，足以满足大多数客户端的需求。只有需要更多可定制性的客户才需要查看外观外的东西（原子系统提供的接口）。
* 客户端与抽象的实现类之间存在许多依赖关系。 引入外观以使子系统与客户端和其他子系统分离，从而提高子系统的独立性和可移植性。
* 您想对子系统进行分层。 使用外观来定义每个子系统级别的入口点。 如果子系统是相关的，则可以通过使子系统仅通过其外观相互通信来简化它们之间的依赖性。

## 鸣谢

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
