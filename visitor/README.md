---
title: Visitor
category: Behavioral
language: en
tag:
    - Decoupling
    - Extensibility
    - Gang of Four
    - Object composition
    - Polymorphism
---

## Intent

To represent an operation to be performed on the elements of an object structure. Visitor lets you define a new operation without changing the classes of the elements on which it operates.

## Explanation

Real-world example

> An analogous real-world example of the Visitor design pattern can be seen in a museum tour guide system. Imagine a museum where visitors can take guided tours to learn about different types of exhibits, such as paintings, sculptures, and historical artifacts. Each exhibit type requires a different explanation, which is provided by specialized tour guides.
> 
> In this scenario, the exhibits are like the elements in the Visitor pattern, and the tour guides are like the visitors. The museum structure remains unchanged, but new guides with new types of tours (operations) can be added without modifying the exhibits themselves. Each guide (visitor) implements a specific way to interact with the exhibits, providing detailed explanations according to their specialization, thereby separating the operations from the objects they operate on. 

In plain words

> Visitor pattern defines operations that can be performed on the nodes of the data structure. 

Wikipedia says

> In object-oriented programming and software engineering, the visitor design pattern is a way of separating an algorithm from an object structure on which it operates. A practical result of this separation is the ability to add new operations to existing object structures without modifying the structures.

**Programmatic Example**

Consider a tree structure with army units. Commander has two sergeants under it and each sergeant has three soldiers under them. Given that the hierarchy implements the visitor pattern, we can easily create new objects that interact with the commander, sergeants, soldiers, or all of them.

Given the army unit example from above, we first have the `Unit` and `UnitVisitor` base types.

```java
public abstract class Unit {

  private final Unit[] children;

  public Unit(Unit... children) {
    this.children = children;
  }

  public void accept(UnitVisitor visitor) {
    Arrays.stream(children).forEach(child -> child.accept(visitor));
  }
}

```

```java
public interface UnitVisitor {

  void visit(Soldier soldier);

  void visit(Sergeant sergeant);

  void visit(Commander commander);
}
```

Then we have the concrete units `Commander`, `Sergeant`, and `Soldier`.

```java
public class Commander extends Unit {

  public Commander(Unit... children) {
    super(children);
  }

  @Override
  public void accept(UnitVisitor visitor) {
    visitor.visit(this);
    super.accept(visitor);
  }

  @Override
  public String toString() {
    return "commander";
  }
}
```

```java
public class Sergeant extends Unit {

  public Sergeant(Unit... children) {
    super(children);
  }

  @Override
  public void accept(UnitVisitor visitor) {
    visitor.visit(this);
    super.accept(visitor);
  }

  @Override
  public String toString() {
    return "sergeant";
  }
}
```

```java
public class Soldier extends Unit {

  public Soldier(Unit... children) {
    super(children);
  }

  @Override
  public void accept(UnitVisitor visitor) {
    visitor.visit(this);
    super.accept(visitor);
  }

  @Override
  public String toString() {
    return "soldier";
  }
}
```

Here are the concrete visitors `CommanderVisitor`, `SergeantVisitor`, and `SoldierVisitor`.

```java
@Slf4j
public class CommanderVisitor implements UnitVisitor {

  @Override
  public void visit(Soldier soldier) {
    // Do nothing
  }

  @Override
  public void visit(Sergeant sergeant) {
    // Do nothing
  }

  @Override
  public void visit(Commander commander) {
    LOGGER.info("Good to see you {}", commander);
  }
}
```

```java
@Slf4j
public class SergeantVisitor implements UnitVisitor {

  @Override
  public void visit(Soldier soldier) {
    // Do nothing
  }

  @Override
  public void visit(Sergeant sergeant) {
    LOGGER.info("Hello {}", sergeant);
  }

  @Override
  public void visit(Commander commander) {
    // Do nothing
  }
}
```

```java
@Slf4j
public class SoldierVisitor implements UnitVisitor {

  @Override
  public void visit(Soldier soldier) {
    LOGGER.info("Greetings {}", soldier);
  }

  @Override
  public void visit(Sergeant sergeant) {
    // Do nothing
  }

  @Override
  public void visit(Commander commander) {
    // Do nothing
  }
}
```

Finally, we can show the power of visitors in action.

```java
public static void main(String[] args) {

    var commander = new Commander(
            new Sergeant(new Soldier(), new Soldier(), new Soldier()),
            new Sergeant(new Soldier(), new Soldier(), new Soldier())
    );
    commander.accept(new SoldierVisitor());
    commander.accept(new SergeantVisitor());
    commander.accept(new CommanderVisitor());
}
```

Program output:

```
14:58:06.115 [main] INFO com.iluwatar.visitor.SoldierVisitor -- Greetings soldier
14:58:06.118 [main] INFO com.iluwatar.visitor.SoldierVisitor -- Greetings soldier
14:58:06.118 [main] INFO com.iluwatar.visitor.SoldierVisitor -- Greetings soldier
14:58:06.118 [main] INFO com.iluwatar.visitor.SoldierVisitor -- Greetings soldier
14:58:06.118 [main] INFO com.iluwatar.visitor.SoldierVisitor -- Greetings soldier
14:58:06.118 [main] INFO com.iluwatar.visitor.SoldierVisitor -- Greetings soldier
14:58:06.118 [main] INFO com.iluwatar.visitor.SergeantVisitor -- Hello sergeant
14:58:06.118 [main] INFO com.iluwatar.visitor.SergeantVisitor -- Hello sergeant
14:58:06.118 [main] INFO com.iluwatar.visitor.CommanderVisitor -- Good to see you commander
```

## Class diagram

![Visitor](./etc/visitor_1.png "Visitor")

## Applicability

Use the Visitor pattern when

* Use the Visitor pattern when you need to perform an operation on a group of similar kinds of objects, and you want to avoid polluting their classes with this operation.
* Use it when a class structure is stable, but you need to perform new operations on the structure without changing it.
* It's beneficial when the set of classes are fixed and only the operations need to be extended.

## Tutorials

* [Visitor (Refactoring Guru)](https://refactoring.guru/design-patterns/visitor)
* [Visitor Pattern Tutorial with Java Examples (DZone)](https://dzone.com/articles/design-patterns-visitor)
* [Visitor Design Pattern (Sourcemaking)](https://sourcemaking.com/design_patterns/visitor)

## Known uses

* Compiler design, where the Visitor pattern can be used for operations like pretty printing, semantic checks, etc.
* Abstract Syntax Tree (AST) processing.
* Document structure processing (e.g., HTML, XML).
* [Apache Wicket](https://github.com/apache/wicket) component tree, see [MarkupContainer](https://github.com/apache/wicket/blob/b60ec64d0b50a611a9549809c9ab216f0ffa3ae3/wicket-core/src/main/java/org/apache/wicket/MarkupContainer.java)
* [javax.lang.model.element.AnnotationValue](http://docs.oracle.com/javase/8/docs/api/javax/lang/model/element/AnnotationValue.html) and [AnnotationValueVisitor](http://docs.oracle.com/javase/8/docs/api/javax/lang/model/element/AnnotationValueVisitor.html)
* [javax.lang.model.element.Element](http://docs.oracle.com/javase/8/docs/api/javax/lang/model/element/Element.html) and [Element Visitor](http://docs.oracle.com/javase/8/docs/api/javax/lang/model/element/ElementVisitor.html)
* [java.nio.file.FileVisitor](http://docs.oracle.com/javase/8/docs/api/java/nio/file/FileVisitor.html)

## Consequences

Benefits:

* Simplifies adding new operations: Adding a new operation is straightforward because you can add a new visitor without changing existing code.
* Single Responsibility Principle: The Visitor pattern allows you to move related behavior into one class.
* Open/Closed Principle: Elements can stay closed to modification while visitors are open to extension.

Trade-offs:

* Adding new element classes: If you need to add new types of elements, you'll need to change both the visitor interface and all of its concrete visitors.
* Circular dependencies: In complex systems, this pattern can introduce circular dependencies between visitor and element classes.
* Breaking encapsulation: Visitor pattern requires that the element classes expose enough details to allow the visitor to do its job, potentially breaking encapsulation.

## Related Patterns

* [Composite](https://java-design-patterns.com/patterns/composite/): The Visitor pattern is often used in conjunction with the Composite pattern, where the visitor can perform operations over a composite structure.
* [Interpreter](https://java-design-patterns.com/patterns/interpreter/): Visitors can be used to implement the non-terminal expressions in the Interpreter pattern.
* [Strategy](https://java-design-patterns.com/patterns/strategy/): Visitor can be considered a way of making strategies work on objects that they were not designed to operate on.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [Java Design Patterns: A Hands-On Experience with Real-World Examples](https://amzn.to/3yhh525)
* [Refactoring to Patterns](https://amzn.to/3VOO4F5)
