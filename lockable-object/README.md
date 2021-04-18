---
layout: pattern
title: Lockable Object
folder: lockable-object
permalink: /patterns/lockable-object/
categories: Concurrency
tags:
- Performance
---


## Intent
The lockable object design pattern ensures that there is only one user using the target object. Compared to the built-in synchronization mechanisms such as using the `synchronized` keyword, this pattern can lock objects for an undetermined time and is not tied to the duration of the request.

## Explanation

The class will contain methods for locking and unlocking the object for single use only, while using thread-safety logic.

Real-world example

>The Sword Of Aragorn is a legendary object that only one creature can posses at the time. Every creature in the middle earth wants to posses is, so  as long as it's not locked, every creature while fight for it.



**Programmatic Example**

```java
/** This interface describes the methods to be supported by a lockable-object. */
public interface Lockable {

  /**
   * checks if the object is locked.
   *
   * @return true if it is locked.
   */
  boolean isLocked();

  /**
   * locks the object with creature as the locker.
   *
   * @param creature as the locker.
   * @return true if object was locked successfully.
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

We have defined that according to our context, the object must implement the Lockable interface.

For example, the SwordOfAragorn class:

```java
public class SwordOfAragorn implements Lockable {

  private static final Logger LOGGER = LoggerFactory.getLogger(SwordOfAragorn.class.getName());
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
  public boolean lock(Creature creature) {
    if (creature == null) {
      throw new NullPointerException("id must not be null.");
    }
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
  public void unlock(Creature creature) {
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

According to our context, there are creatures that are looking for the sword, so must define the parent class:

```java
public abstract class Creature {

  private static final Logger LOGGER = LoggerFactory.getLogger(Creature.class.getName());
  private String name;
  private CreatureType type;
  private int health;
  private int damage;
  Set<Lockable> instruments;

  protected Creature(String name) {
    this.name = name;
    this.instruments = new HashSet<>();
  }

  /**
   * Reaches for the Lockable and tried to hold it.
   *
   * @param lockable as the Lockable to lock.
   * @return true of Lockable was locked by this creature.
   */
  public boolean acquire(Lockable lockable) {
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
  }

  /**
   * Attacks a foe.
   *
   * @param creature as the foe to be attacked.
   */
  public synchronized void attack(Creature creature) throws InterruptedException {
    creature.hit(getDamage());
  }

  /**
   * When a creature gets hit it removed the amount of damage from the creature's life.
   *
   * @param damage as the damage that was taken.
   */
  public synchronized void hit(int damage) {
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

As mentioned before, we have classes that extend the Creature class, such as Elf, Orc and Human.

Finally, the following program will simulate a battle for the sword:

```java
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class.getName());
  private static final int WAIT_TIME = 2000;
  private static final int WORKERS = 2;
  private static final int MULTIPLICATION_FACTOR = 3;

  /**
   * main method.
   *
   * @param args as arguments for the main method.
   * @throws InterruptedException in case of interruption for one of the threads.
   */
  public static void main(String[] args) throws InterruptedException {
    Lockable sword = new SwordOfAragorn();
    List<Creature> creatures = new ArrayList<>();
    for (int i = 0; i < WORKERS; i++) {
      creatures.add(new Elf(String.format("Elf %s", i)));
      creatures.add(new Orc(String.format("Orc %s", i)));
      creatures.add(new Human(String.format("Human %s", i)));
    }
    int totalFiends = WORKERS * MULTIPLICATION_FACTOR;
    ExecutorService service = Executors.newFixedThreadPool(totalFiends);
    for (int i = 0; i < totalFiends; i = i + MULTIPLICATION_FACTOR) {
      service.submit(new Feind(creatures.get(i), sword));
      service.submit(new Feind(creatures.get(i + 1), sword));
      service.submit(new Feind(creatures.get(i + 2), sword));
    }
    Thread.sleep(WAIT_TIME);
    LOGGER.info("The master of the sword is now {}.", sword.getLocker().getName());
    service.shutdown();
  }
}
```

## Class diagram

![alt text](./etc/lockable-object.urm.png "Lockable Object class diagram")
