---
layout: pattern
title: Multiton
folder: multiton
permalink: /patterns/multiton/
categories: Creational
language: en
tags:
 - Instantiation
---

## Also known as

Registry

## Intent

Ensure a class only has limited number of instances and provide a global point of access to them.

## Explanation

Real world example

> The Nazgûl, also called ringwraiths or the Nine Riders, are Sauron's most terrible servants. By 
> definition there's always nine of them.           

In plain words

> Multiton pattern ensures there's predefined amount of instances available globally.   

Wikipedia says

> In software engineering, the multiton pattern is a design pattern which generalizes the singleton 
> pattern. Whereas the singleton allows only one instance of a class to be created, the multiton 
> pattern allows for the controlled creation of multiple instances, which it manages through the use 
> of a map.

**Programmatic Example**

`Nazgul` is the multiton class.

```java
public enum NazgulName {

  KHAMUL, MURAZOR, DWAR, JI_INDUR, AKHORAHIL, HOARMURATH, ADUNAPHEL, REN, UVATHA
}

public final class Nazgul {

  private static final Map<NazgulName, Nazgul> nazguls;

  private final NazgulName name;

  static {
    nazguls = new ConcurrentHashMap<>();
    nazguls.put(NazgulName.KHAMUL, new Nazgul(NazgulName.KHAMUL));
    nazguls.put(NazgulName.MURAZOR, new Nazgul(NazgulName.MURAZOR));
    nazguls.put(NazgulName.DWAR, new Nazgul(NazgulName.DWAR));
    nazguls.put(NazgulName.JI_INDUR, new Nazgul(NazgulName.JI_INDUR));
    nazguls.put(NazgulName.AKHORAHIL, new Nazgul(NazgulName.AKHORAHIL));
    nazguls.put(NazgulName.HOARMURATH, new Nazgul(NazgulName.HOARMURATH));
    nazguls.put(NazgulName.ADUNAPHEL, new Nazgul(NazgulName.ADUNAPHEL));
    nazguls.put(NazgulName.REN, new Nazgul(NazgulName.REN));
    nazguls.put(NazgulName.UVATHA, new Nazgul(NazgulName.UVATHA));
  }

  private Nazgul(NazgulName name) {
    this.name = name;
  }

  public static Nazgul getInstance(NazgulName name) {
    return nazguls.get(name);
  }

  public NazgulName getName() {
    return name;
  }
}
```

And here's how we access the `Nazgul` instances.

```java
    LOGGER.info("KHAMUL={}", Nazgul.getInstance(NazgulName.KHAMUL));
    LOGGER.info("MURAZOR={}", Nazgul.getInstance(NazgulName.MURAZOR));
    LOGGER.info("DWAR={}", Nazgul.getInstance(NazgulName.DWAR));
    LOGGER.info("JI_INDUR={}", Nazgul.getInstance(NazgulName.JI_INDUR));
    LOGGER.info("AKHORAHIL={}", Nazgul.getInstance(NazgulName.AKHORAHIL));
    LOGGER.info("HOARMURATH={}", Nazgul.getInstance(NazgulName.HOARMURATH));
    LOGGER.info("ADUNAPHEL={}", Nazgul.getInstance(NazgulName.ADUNAPHEL));
    LOGGER.info("REN={}", Nazgul.getInstance(NazgulName.REN));
    LOGGER.info("UVATHA={}", Nazgul.getInstance(NazgulName.UVATHA));
```

Program output:

```
KHAMUL=com.iluwatar.multiton.Nazgul@2b214b94
MURAZOR=com.iluwatar.multiton.Nazgul@17814b1c
DWAR=com.iluwatar.multiton.Nazgul@7ac9af2a
JI_INDUR=com.iluwatar.multiton.Nazgul@7bb004b8
AKHORAHIL=com.iluwatar.multiton.Nazgul@78e89bfe
HOARMURATH=com.iluwatar.multiton.Nazgul@652ce654
ADUNAPHEL=com.iluwatar.multiton.Nazgul@522ba524
REN=com.iluwatar.multiton.Nazgul@29c5ee1d
UVATHA=com.iluwatar.multiton.Nazgul@15cea7b0
```

## Class diagram

![alt text](./etc/multiton.png "Multiton")

## Applicability

Use the Multiton pattern when

* There must be specific number of instances of a class, and they must be accessible to clients from 
a well-known access point.
