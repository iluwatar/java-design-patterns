---
layout: pattern
title: Lockable Object
folder: lockable-object
permalink: /patterns/lockable-object/zh
categories: Concurrency
language: zh
tags:
- Performance
---


## 目的
可锁定对象设计模式确保只有一个用户使用目标对象。与使用`synchronized`关键字等内置同步机制相比，这种模式可以在不确定的时间内锁定对象，并且与请求的持续时间无关。

## 解释


真实世界的例子

>阿拉贡之剑是一件传奇物品，当时只有一种生物可以拥有。
>中土的每一个生灵都想拥有，所以只要不锁，每一个生灵都会为之而战。

引擎盖下

> 在这个特定的模块中，SwordOfAragorn.java 是一个实现 Lockable 接口的类。
它通过使用实现 unlock() 和 unlock() 方法达到了 Lockable-Object 模式的目标
线程安全逻辑。线程安全逻辑是通过 Java 内置的监控机制实现的。
SwordOfAaragorn.java 有一个名为“同步器”的对象属性。在每个关键的并发代码块中，
它通过使用同步器来同步块。



**程序示例**

```java
/** This interface describes the methods to be supported by a lockable object. */
public interface Lockable {

  /**
   * Checks if the object is locked.
   *
   * @return true if it is locked.
   */
  boolean isLocked();

  /**
   * locks the object with the creature as the locker.
   *
   * @param creature as the locker.
   * @return true if the object was locked successfully.
   */
  boolean lock(Creature creature);

  /**
   * Unlocks the object.
   *
   * @param creature as the locker.
   */
  void unlock(Creature creature);

  /**
   * Gets the locker.
   *
   * @return the Creature that holds the object. Returns null if no one is locking.
   */
  Creature getLocker();

  /**
   * Returns the name of the object.
   *
   * @return the name of the object.
   */
  String getName();
}

```

我们已经定义，根据我们的上下文，对象必须实现 Lockable 接口。

例如，SwordOfAragorn 类：

```java
public class SwordOfAragorn implements Lockable {

  private Creature locker;
  private final Object synchronizer;
  private static final String NAME = "The Sword of Aragorn";

  public SwordOfAragorn() {
    this.locker = null;
    this.synchronizer = new Object();
  }

  @Override
  public boolean isLocked() {
    return this.locker != null;
  }

  @Override
  public boolean lock(@NonNull Creature creature) {
    synchronized (synchronizer) {
      LOGGER.info("{} is now trying to acquire {}!", creature.getName(), this.getName());
      if (!isLocked()) {
        locker = creature;
        return true;
      } else {
        if (!locker.getName().equals(creature.getName())) {
          return false;
        }
      }
    }
    return false;
  }

  @Override
  public void unlock(@NonNull Creature creature) {
    synchronized (synchronizer) {
      if (locker != null && locker.getName().equals(creature.getName())) {
        locker = null;
        LOGGER.info("{} is now free!", this.getName());
      }
      if (locker != null) {
        throw new LockingException("You cannot unlock an object you are not the owner of.");
      }
    }
  }

  @Override
  public Creature getLocker() {
    return this.locker;
  }

  @Override
  public String getName() {
    return NAME;
  }
}
```

根据我们的上下文，有寻找剑的生物，所以必须定义父类：
```java
public abstract class Creature {

  private String name;
  private CreatureType type;
  private int health;
  private int damage;
  Set<Lockable> instruments;

  protected Creature(@NonNull String name) {
    this.name = name;
    this.instruments = new HashSet<>();
  }

  /**
   * Reaches for the Lockable and tried to hold it.
   *
   * @param lockable as the Lockable to lock.
   * @return true of Lockable was locked by this creature.
   */
  public boolean acquire(@NonNull Lockable lockable) {
    if (lockable.lock(this)) {
      instruments.add(lockable);
      return true;
    }
    return false;
  }

  /** Terminates the Creature and unlocks all of the Lockable that it posses. */
  public synchronized void kill() {
    LOGGER.info("{} {} has been slayed!", type, name);
    for (Lockable lockable : instruments) {
      lockable.unlock(this);
    }
    this.instruments.clear();
  }

  /**
   * Attacks a foe.
   *
   * @param creature as the foe to be attacked.
   */
  public synchronized void attack(@NonNull Creature creature) {
    creature.hit(getDamage());
  }

  /**
   * When a creature gets hit it removed the amount of damage from the creature's life.
   *
   * @param damage as the damage that was taken.
   */
  public synchronized void hit(int damage) {
    if (damage < 0) {
      throw new IllegalArgumentException("Damage cannot be a negative number");
    }
    if (isAlive()) {
      setHealth(getHealth() - damage);
      if (!isAlive()) {
        kill();
      }
    }
  }

  /**
   * Checks if the creature is still alive.
   *
   * @return true of creature is alive.
   */
  public synchronized boolean isAlive() {
    return getHealth() > 0;
  }

}
```

如前所述，我们有扩展 Creature 类的类，例如 Elf、Orc 和 Human。

最后，下面的程序将模拟一场争夺剑的战斗：
```java
public class App implements Runnable {

  private static final int WAIT_TIME = 3;
  private static final int WORKERS = 2;
  private static final int MULTIPLICATION_FACTOR = 3;

  /**
   * main method.
   *
   * @param args as arguments for the main method.
   */
  public static void main(String[] args) {
    var app = new App();
    app.run();
  }

  @Override
  public void run() {
    // The target object for this example.
    var sword = new SwordOfAragorn();
    // Creation of creatures.
    List<Creature> creatures = new ArrayList<>();
    for (var i = 0; i < WORKERS; i++) {
      creatures.add(new Elf(String.format("Elf %s", i)));
      creatures.add(new Orc(String.format("Orc %s", i)));
      creatures.add(new Human(String.format("Human %s", i)));
    }
    int totalFiends = WORKERS * MULTIPLICATION_FACTOR;
    ExecutorService service = Executors.newFixedThreadPool(totalFiends);
    // Attach every creature and the sword is a Fiend to fight for the sword.
    for (var i = 0; i < totalFiends; i = i + MULTIPLICATION_FACTOR) {
      service.submit(new Feind(creatures.get(i), sword));
      service.submit(new Feind(creatures.get(i + 1), sword));
      service.submit(new Feind(creatures.get(i + 2), sword));
    }
    // Wait for program to terminate.
    try {
      if (!service.awaitTermination(WAIT_TIME, TimeUnit.SECONDS)) {
        LOGGER.info("The master of the sword is now {}.", sword.getLocker().getName());
      }
    } catch (InterruptedException e) {
      LOGGER.error(e.getMessage());
      Thread.currentThread().interrupt();
    } finally {
      service.shutdown();
    }
  }
}
```

## 适用性

可锁定对象模式非常适合需要线程安全的非分布式应用程序
并将它们的域模型保存在内存中（与数据库等持久模型相反）。

## 类图

![alt text](./etc/lockable-object.urm.png "Lockable Object class diagram")


## 鸣谢

* [Lockable Object - Chapter 10.3, J2EE Design Patterns, O'Reilly](http://ommolketab.ir/aaf-lib/axkwht7wxrhvgs2aqkxse8hihyu9zv.pdf)
