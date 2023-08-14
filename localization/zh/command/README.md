---
title: Command
category: Behavioral
language: zh
tag:
 - Gang of Four
---

## 或称
行动, 事务模式

## 目的
将请求封装为对象，从而使你可以将具有不同请求的客户端参数化，队列或记录请求，并且支持可撤销操作。

## 解释
真实世界例子

> 有一个巫师在地精上施放咒语。咒语在地精上一一执行。第一个咒语使地精缩小，第二个使他不可见。然后巫师将咒语一个个的反转。这里的每一个咒语都是一个可撤销的命令对象。

用通俗的话说

> 用命令对象的方式存储请求以在将来时可以执行它或撤销它。

维基百科说

> 在面向对象编程中，命令模式是一种行为型设计模式，它把在稍后执行的一个动作或触发的一个事件所需要的所有信息封装到一个对象中。

**编程示例**

这是巫师和地精的示例代码。让我们从巫师类开始。

```java
public class Wizard {

  private static final Logger LOGGER = LoggerFactory.getLogger(Wizard.class);

  private final Deque<Runnable> undoStack = new LinkedList<>();
  private final Deque<Runnable> redoStack = new LinkedList<>();

  public Wizard() {}

  public void castSpell(Command command, Target target) {
    LOGGER.info("{} casts {} at {}", this, command, target);
    command.execute(target);
    undoStack.offerLast(command);
  }

  public void undoLastSpell() {
    if (!undoStack.isEmpty()) {
      var previousSpell = undoStack.pollLast();
      redoStack.offerLast(previousSpell);
      LOGGER.info("{} undoes {}", this, previousSpell);
      previousSpell.undo();
    }
  }

  public void redoLastSpell() {
    if (!redoStack.isEmpty()) {
      var previousSpell = redoStack.pollLast();
      undoStack.offerLast(previousSpell);
      LOGGER.info("{} redoes {}", this, previousSpell);
      previousSpell.redo();
    }
  }

  @Override
  public String toString() {
    return "Wizard";
  }
}
```

接下来我们介绍咒语层级

```java
public interface Command {

  void execute(Target target);

  void undo();

  void redo();

  String toString();
}

public class InvisibilitySpell implements Command {

  private Target target;

  @Override
  public void execute(Target target) {
    target.setVisibility(Visibility.INVISIBLE);
    this.target = target;
  }

  @Override
  public void undo() {
    if (target != null) {
      target.setVisibility(Visibility.VISIBLE);
    }
  }

  @Override
  public void redo() {
    if (target != null) {
      target.setVisibility(Visibility.INVISIBLE);
    }
  }

  @Override
  public String toString() {
    return "Invisibility spell";
  }
}

public class ShrinkSpell implements Command {

  private Size oldSize;
  private Target target;

  @Override
  public void execute(Target target) {
    oldSize = target.getSize();
    target.setSize(Size.SMALL);
    this.target = target;
  }

  @Override
  public void undo() {
    if (oldSize != null && target != null) {
      var temp = target.getSize();
      target.setSize(oldSize);
      oldSize = temp;
    }
  }

  @Override
  public void redo() {
    undo();
  }

  @Override
  public String toString() {
    return "Shrink spell";
  }
}
```

最后我们有咒语的目标地精。

```java
public abstract class Target {

  private static final Logger LOGGER = LoggerFactory.getLogger(Target.class);

  private Size size;

  private Visibility visibility;

  public Size getSize() {
    return size;
  }

  public void setSize(Size size) {
    this.size = size;
  }

  public Visibility getVisibility() {
    return visibility;
  }

  public void setVisibility(Visibility visibility) {
    this.visibility = visibility;
  }

  @Override
  public abstract String toString();

  public void printStatus() {
    LOGGER.info("{}, [size={}] [visibility={}]", this, getSize(), getVisibility());
  }
}

public class Goblin extends Target {

  public Goblin() {
    setSize(Size.NORMAL);
    setVisibility(Visibility.VISIBLE);
  }

  @Override
  public String toString() {
    return "Goblin";
  }

}
```

最后是整个示例的实践。

```java
var wizard = new Wizard();
var goblin = new Goblin();
goblin.printStatus();
// Goblin, [size=normal] [visibility=visible]
wizard.castSpell(new ShrinkSpell(), goblin);
// Wizard casts Shrink spell at Goblin
goblin.printStatus();
// Goblin, [size=small] [visibility=visible]
wizard.castSpell(new InvisibilitySpell(), goblin);
// Wizard casts Invisibility spell at Goblin
goblin.printStatus();
// Goblin, [size=small] [visibility=invisible]
wizard.undoLastSpell();
// Wizard undoes Invisibility spell
goblin.printStatus();
// Goblin, [size=small] [visibility=visible]
```

## 类图
![alt text](./etc/command.png "Command")

## 适用性
使用命令模式当你想

* 通过操作将对象参数化。您可以使用回调函数（即，已在某处注册以便稍后调用的函数）以过程语言表示这种参数化。命令是回调的一种面向对象替代方案。
* 在不同的时间指定，排队和执行请求。一个命令对象的生存期可以独立于原始请求。如果请求的接收方可以以地址空间无关的方式来表示，那么你可以将请求的命令对象传输到其他进程并在那里执行请求。
* 支持撤销。命令的执行操作可以在命令本身中存储状态以反转其效果。命令接口必须有添加的反执行操作，该操作可以逆转上一次执行调用的效果。执行的命令存储在历史列表中。无限撤消和重做通过分别向后和向前遍历此列表来实现，分别调用unexecute和execute。
* 支持日志记录更改，以便在系统崩溃时可以重新应用它们。通过使用加载和存储操作扩展命令接口，你可以保留更改的永久日志。从崩溃中恢复涉及从磁盘重新加载记录的命令，并通过执行操作重新执行它们。
* 通过原始的操作来构建一个以高级操作围绕的系统。这种结构在支持事务的信息系统中很常见。事务封装了一组数据更改。命令模式提供了一种对事务进行建模的方法。命令具有公共接口，让你以相同的方式调用所有事务。该模式还可以通过新的事务来轻松扩展系统。

## 典型用例

* 保留请求历史
* 实现回调功能
* 实现撤销功能

## Java世界例子

* [java.lang.Runnable](http://docs.oracle.com/javase/8/docs/api/java/lang/Runnable.html)
* [org.junit.runners.model.Statement](https://github.com/junit-team/junit4/blob/master/src/main/java/org/junit/runners/model/Statement.java)
* [Netflix Hystrix](https://github.com/Netflix/Hystrix/wiki)
* [javax.swing.Action](http://docs.oracle.com/javase/8/docs/api/javax/swing/Action.html)

## 鸣谢

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=f27d2644fbe5026ea448791a8ad09c94)
