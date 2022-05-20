---
layout: pattern
title: Coarse-grained Lock
folder: coarse-grained-lock
permalink: /patterns/coarse-grained-lock/
categories: Concurrency
language: en
tags:
 - Performance
---

## Intent

Use a single lock to control concurrency on multiple objects.

## Explanation

The coarse-grained lock pattern uses a single lock to cover multiple objects. It not only simplifies the locking action itself but also frees you from having to load all the members of a group in order to lock them.

**Programmatic Example**

In the following example simulates a scenario where a famous basketball
player Lebron James joins different teams and wins champions. The problem here is that
when Lebron James is playing hard during the season to win the championship, he cannot
become a free agent to join other teams. Therefore, coarse-grained lock could be used
to prevent address from being updated when Lebron James is playing during the season.

First we could build an `Address` class that represents the address where the player lives.
```java
public class Address {
  private final Player player;
  private String city;
  private String state;

  public Address(Player player, String city, String state) {
    this.player = player;
    this.city = city;
    this.state = state;
  }

  public String getCity() {
    return city;
  }
  
  public String getState() {
    return state;
  }

  public void updateAddress(String city, String state) {
    synchronized (player.getMutex()) {
      LOGGER.info("Become Free Agent");
      this.city = city;
      this.state = state;
      LOGGER.info(player.getFirstName() + " " + player.getLastName() + " "
          + "brings talent to " + this.city + ", which is in " + this.state);
    }
  }

}
```

`Player` class represents a player.

```java
public class Player {
  private final String lastName;
  private final String firstName;
  private int champions;
  private final Address address;
  private final Object mutex;

  public Player(String lastName, String firstName, String city, String state) {
    this.lastName = lastName;
    this.firstName = firstName;
    this.champions = 0;
    this.address = new Address(this, city, state);
    this.mutex = new Object();
  }

  public Object getMutex() {
    return mutex;
  }

  public String getFirstName() {
    return firstName;
  }

  public int getChampions() {
    return champions;
  }

  public String getCity() {
    return address.getCity();
  }

  public String getState() {
    return address.getState();
  }

  public String getLastName() {
    return lastName;
  }

  public void winChampion() {
    synchronized (mutex) {
      LOGGER.info("Playing hard to win champion");
      this.champions += 1;
      LOGGER.info("Win one more champion, now the total champion is " + this.champions);
    }
  }

  public void updateAddress(String city, String state) {
    this.address.updateAddress(city, state);
  }
}
```

`WinChampion` class creates a thread representing a player's decision on playing hard to win championship.

```java
public class WinChampion extends Thread {
  private final Player player;

  public WinChampion(Player player) {
    this.player = player;
  }
  
  public void run() {
    for (int i = 0; i < 3; i++) {
      player.winChampion();
    }
  }
}
```

`Miami` class creates a thread representing a player's decision on moving to Miami.
```java
public class Miami extends Thread {
  private final Player player;

  public Miami(Player player) {
    this.player = player;
  }

  public void run() {
    player.updateAddress("Miami", "Florida");
  }
}
```


`Cleveland` class creates a thread representing a player's decision on moving to Cleveland.
```java
public class Cleveland extends Thread {
  private final Player player;

  public Cleveland(Player player) {
    this.player = player;
  }

  public void run() {
    player.updateAddress("Cleveland", "Ohio");
  }
}
```

`LosAngeles` class creates a thread representing a player's decision on moving to LosAngeles.
```java
public class LosAngeles extends Thread {
  private final Player player;

  public LosAngeles(Player player) {
    this.player = player;
  }

  public void run() {
    player.updateAddress("LosAngeles", "California");
  }
}
```

Now the `App` simulates the scenerio that when Lebron James is playing hard during the season to win champion, he cannot become free agent to join other teams.

```java
public class App {

  public static void main(String[] args) {
    var lebronJames = new Player("James", "Lebron", "Cleveland", "Ohio");
    var miami = new Miami(lebronJames);
    var winChampion = new WinChampion(lebronJames);
    var cleveland = new Cleveland(lebronJames);
    winChampion.start();
    miami.start();
    cleveland.start();
    var losAngeles = new LosAngeles(lebronJames);
    losAngeles.start();
  }
}
```

## Class diagram

![image](https://user-images.githubusercontent.com/73434395/168976105-6fa10028-5088-4c2a-ac45-0e2036143cf0.png)


## Applicability

Use the Data Transfer Hash pattern when:

* Using the application it makes sense to lock all of a series of items if you want to lock any one of them.

## Credits

* [Coarse-grained Lock Pattern](https://www.youtube.com/watch?v=-35OPiQwd8k)
