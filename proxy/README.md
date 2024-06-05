---
title: Proxy
category: Structural
language: en
tag:
    - Decoupling
    - Encapsulation
    - Gang Of Four
    - Lazy initialization
    - Proxy
    - Security
    - Wrapping
---

## Also known as

* Surrogate

## Intent

Provide a surrogate or placeholder for another object to control access to it.

## Explanation

Real-world example

> In a real-world scenario, consider a security guard at a gated community. The security guard acts as a proxy for the residents. When a visitor arrives, the guard checks the visitor's credentials and permissions before allowing them access to the community. If the visitor is authorized, the guard grants entry; if not, entry is denied. This ensures that only authorized individuals can access the community, much like a Proxy design pattern controls access to a specific object.

In plain words

> Using the proxy pattern, a class represents the functionality of another class.

Wikipedia says

> A proxy, in its most general form, is a class functioning as an interface to something else. A proxy is a wrapper or agent object that is being called by the client to access the real serving object behind the scenes. Use of the proxy can simply be forwarding to the real object, or can provide additional logic. In the proxy extra functionality can be provided, for example caching when operations on the real object are resource intensive, or checking preconditions before operations on the real object are invoked.

**Programmatic Example**

Imagine a tower where the local wizards go to study their spells. The ivory tower can only be accessed through a proxy which ensures that only the first three wizards can enter. Here the proxy represents the functionality of the tower and adds access control to it.

First, we have the `WizardTower` interface and the `IvoryTower` class.

```java
public interface WizardTower {
  void enter(Wizard wizard);
}
```

```java
@Slf4j
public class IvoryTower implements WizardTower {
  public void enter(Wizard wizard) {
    LOGGER.info("{} enters the tower.", wizard);
  }
}
```

Then a simple `Wizard` class.

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

Then we have the `WizardTowerProxy` to add access control to `WizardTower`.

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

And here is the tower entering scenario.

```java
  public static void main(String[] args) {

    var proxy = new WizardTowerProxy(new IvoryTower());
    proxy.enter(new Wizard("Red wizard"));
    proxy.enter(new Wizard("White wizard"));
    proxy.enter(new Wizard("Black wizard"));
    proxy.enter(new Wizard("Green wizard"));
    proxy.enter(new Wizard("Brown wizard"));
}
```

Program output:

```
08:42:06.183 [main] INFO com.iluwatar.proxy.IvoryTower -- Red wizard enters the tower.
08:42:06.186 [main] INFO com.iluwatar.proxy.IvoryTower -- White wizard enters the tower.
08:42:06.186 [main] INFO com.iluwatar.proxy.IvoryTower -- Black wizard enters the tower.
08:42:06.186 [main] INFO com.iluwatar.proxy.WizardTowerProxy -- Green wizard is not allowed to enter!
08:42:06.186 [main] INFO com.iluwatar.proxy.WizardTowerProxy -- Brown wizard is not allowed to enter!
```

## Applicability

Proxy is applicable whenever there is a need for a more versatile or sophisticated reference to an object than a simple pointer. Here are several common situations in which the Proxy pattern is applicable. Typically, the proxy pattern is used to

* Control access to another object
* Lazy initialization
* Implement logging
* Facilitate network connection
* Count references to an object
* Provide a local representation for an object that is in a different address space.

## Known Uses

* Virtual Proxies: In applications that need heavy resources like large images or complex calculations, virtual proxies can be used to instantiate objects only when needed.
* Remote Proxies: Used in remote method invocation (RMI) to manage interactions with remote objects.
* Protection Proxies: Control access to the original object to ensure proper authorization.
* [java.lang.reflect.Proxy](http://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Proxy.html)
* [Apache Commons Proxy](https://commons.apache.org/proper/commons-proxy/)
* Mocking frameworks [Mockito](https://site.mockito.org/),[Powermock](https://powermock.github.io/), [EasyMock](https://easymock.org/)
* [UIAppearance](https://developer.apple.com/documentation/uikit/uiappearance)

## Consequences

Benefits:

* Controlled Access: Proxies control access to the real object, allowing for checks, logging, or other operations.
* Lazy Initialization: Proxies can delay the creation and initialization of resource-intensive objects until they are needed.
* Remote Proxy Handling: Simplifies interaction with remote objects by handling the network communication.

Trade-offs:

* Overhead: Adding a proxy introduces additional layers that might add overhead.
* Complexity: Increases the complexity of the system by adding more classes.

## Related Patterns

* [Adapter](https://java-design-patterns.com/patterns/adapter/): The Adapter pattern changes the interface of an existing object, whereas Proxy provides the same interface as the original object.
* [Ambassador](https://java-design-patterns.com/patterns/ambassador/): Ambassador is similar to Proxy as it acts as an intermediary, especially in remote communications, enhancing access control and monitoring.
* [Decorator](https://java-design-patterns.com/patterns/decorator/): Both Decorator and Proxy patterns provide a level of indirection, but the Decorator pattern adds responsibilities to objects dynamically, while Proxy controls access.
* [Facade](https://java-design-patterns.com/patterns/facade/): Facade provides a simplified interface to a complex subsystem, while Proxy controls access to a particular object.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [Java Design Patterns: A Hands-On Experience with Real-World Examples](https://amzn.to/3yhh525)
