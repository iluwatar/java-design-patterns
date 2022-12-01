---
title: Bytecode
category: Behavioral
language: zh
tag:
 - Game programming
---

## 意图

允许编码行为作为虚拟机的指令。

## 解释

真实世界例子

> 一个团队正在开发一款新的巫师对战游戏。巫师的行为需要经过精心的调整和上百次的游玩测试。每次当游戏设计师想改变巫师行为时都让程序员去修改代码这是不妥的，所以巫师行为以数据驱动的虚拟机方式实现。

通俗地说

> 字节码模式支持由数据而不是代码驱动的行为。

[Gameprogrammingpatterns.com](https://gameprogrammingpatterns.com/bytecode.html) 中做了如下阐述：

> 指令集定义了可以执行的低级操作。一系列指令被编码为字节序列。虚拟机一次一条地执行这些指令，中间的值用栈处理。通过组合指令，可以定义复杂的高级行为。

**程序示例**

其中最重要的游戏对象是`巫师`类。

```java
@AllArgsConstructor
@Setter
@Getter
@Slf4j
public class Wizard {

  private int health;
  private int agility;
  private int wisdom;
  private int numberOfPlayedSounds;
  private int numberOfSpawnedParticles;

  public void playSound() {
    LOGGER.info("Playing sound");
    numberOfPlayedSounds++;
  }

  public void spawnParticles() {
    LOGGER.info("Spawning particles");
    numberOfSpawnedParticles++;
  }
}
```

下面我们展示虚拟机可用的指令。每个指令对于如何操作栈中的数据都有自己的语义。例如，增加指令，其取得栈顶的两个元素并把结果压入栈中。

```java
@AllArgsConstructor
@Getter
public enum Instruction {

  LITERAL(1),         // e.g. "LITERAL 0", push 0 to stack
  SET_HEALTH(2),      // e.g. "SET_HEALTH", pop health and wizard number, call set health
  SET_WISDOM(3),      // e.g. "SET_WISDOM", pop wisdom and wizard number, call set wisdom
  SET_AGILITY(4),     // e.g. "SET_AGILITY", pop agility and wizard number, call set agility
  PLAY_SOUND(5),      // e.g. "PLAY_SOUND", pop value as wizard number, call play sound
  SPAWN_PARTICLES(6), // e.g. "SPAWN_PARTICLES", pop value as wizard number, call spawn particles
  GET_HEALTH(7),      // e.g. "GET_HEALTH", pop value as wizard number, push wizard's health
  GET_AGILITY(8),     // e.g. "GET_AGILITY", pop value as wizard number, push wizard's agility
  GET_WISDOM(9),      // e.g. "GET_WISDOM", pop value as wizard number, push wizard's wisdom
  ADD(10),            // e.g. "ADD", pop 2 values, push their sum
  DIVIDE(11);         // e.g. "DIVIDE", pop 2 values, push their division
  // ...
}
```

我们示例的核心是`虚拟机`类。 它将指令作为输入并执行它们以提供游戏对象行为。

```java
@Getter
@Slf4j
public class VirtualMachine {

  private final Stack<Integer> stack = new Stack<>();

  private final Wizard[] wizards = new Wizard[2];

  public VirtualMachine() {
    wizards[0] = new Wizard(randomInt(3, 32), randomInt(3, 32), randomInt(3, 32),
        0, 0);
    wizards[1] = new Wizard(randomInt(3, 32), randomInt(3, 32), randomInt(3, 32),
        0, 0);
  }

  public VirtualMachine(Wizard wizard1, Wizard wizard2) {
    wizards[0] = wizard1;
    wizards[1] = wizard2;
  }

  public void execute(int[] bytecode) {
    for (var i = 0; i < bytecode.length; i++) {
      Instruction instruction = Instruction.getInstruction(bytecode[i]);
      switch (instruction) {
        case LITERAL:
          // Read the next byte from the bytecode.
          int value = bytecode[++i];
          // Push the next value to stack
          stack.push(value);
          break;
        case SET_AGILITY:
          var amount = stack.pop();
          var wizard = stack.pop();
          setAgility(wizard, amount);
          break;
        case SET_WISDOM:
          amount = stack.pop();
          wizard = stack.pop();
          setWisdom(wizard, amount);
          break;
        case SET_HEALTH:
          amount = stack.pop();
          wizard = stack.pop();
          setHealth(wizard, amount);
          break;
        case GET_HEALTH:
          wizard = stack.pop();
          stack.push(getHealth(wizard));
          break;
        case GET_AGILITY:
          wizard = stack.pop();
          stack.push(getAgility(wizard));
          break;
        case GET_WISDOM:
          wizard = stack.pop();
          stack.push(getWisdom(wizard));
          break;
        case ADD:
          var a = stack.pop();
          var b = stack.pop();
          stack.push(a + b);
          break;
        case DIVIDE:
          a = stack.pop();
          b = stack.pop();
          stack.push(b / a);
          break;
        case PLAY_SOUND:
          wizard = stack.pop();
          getWizards()[wizard].playSound();
          break;
        case SPAWN_PARTICLES:
          wizard = stack.pop();
          getWizards()[wizard].spawnParticles();
          break;
        default:
          throw new IllegalArgumentException("Invalid instruction value");
      }
      LOGGER.info("Executed " + instruction.name() + ", Stack contains " + getStack());
    }
  }

  public void setHealth(int wizard, int amount) {
    wizards[wizard].setHealth(amount);
  }
  // other setters ->
  // ...
}
```

现在我们可以展示使用虚拟机的完整示例。

```java
  public static void main(String[] args) {

    var vm = new VirtualMachine(
        new Wizard(45, 7, 11, 0, 0),
        new Wizard(36, 18, 8, 0, 0));

    vm.execute(InstructionConverterUtil.convertToByteCode("LITERAL 0"));
    vm.execute(InstructionConverterUtil.convertToByteCode("LITERAL 0"));
    vm.execute(InstructionConverterUtil.convertToByteCode("GET_HEALTH"));
    vm.execute(InstructionConverterUtil.convertToByteCode("LITERAL 0"));
    vm.execute(InstructionConverterUtil.convertToByteCode("GET_AGILITY"));
    vm.execute(InstructionConverterUtil.convertToByteCode("LITERAL 0"));
    vm.execute(InstructionConverterUtil.convertToByteCode("GET_WISDOM"));
    vm.execute(InstructionConverterUtil.convertToByteCode("ADD"));
    vm.execute(InstructionConverterUtil.convertToByteCode("LITERAL 2"));
    vm.execute(InstructionConverterUtil.convertToByteCode("DIVIDE"));
    vm.execute(InstructionConverterUtil.convertToByteCode("ADD"));
    vm.execute(InstructionConverterUtil.convertToByteCode("SET_HEALTH"));
  }
```

下面是控制台输出。

```
16:20:10.193 [main] INFO com.iluwatar.bytecode.VirtualMachine - Executed LITERAL, Stack contains [0]
16:20:10.196 [main] INFO com.iluwatar.bytecode.VirtualMachine - Executed LITERAL, Stack contains [0, 0]
16:20:10.197 [main] INFO com.iluwatar.bytecode.VirtualMachine - Executed GET_HEALTH, Stack contains [0, 45]
16:20:10.197 [main] INFO com.iluwatar.bytecode.VirtualMachine - Executed LITERAL, Stack contains [0, 45, 0]
16:20:10.197 [main] INFO com.iluwatar.bytecode.VirtualMachine - Executed GET_AGILITY, Stack contains [0, 45, 7]
16:20:10.197 [main] INFO com.iluwatar.bytecode.VirtualMachine - Executed LITERAL, Stack contains [0, 45, 7, 0]
16:20:10.197 [main] INFO com.iluwatar.bytecode.VirtualMachine - Executed GET_WISDOM, Stack contains [0, 45, 7, 11]
16:20:10.197 [main] INFO com.iluwatar.bytecode.VirtualMachine - Executed ADD, Stack contains [0, 45, 18]
16:20:10.197 [main] INFO com.iluwatar.bytecode.VirtualMachine - Executed LITERAL, Stack contains [0, 45, 18, 2]
16:20:10.198 [main] INFO com.iluwatar.bytecode.VirtualMachine - Executed DIVIDE, Stack contains [0, 45, 9]
16:20:10.198 [main] INFO com.iluwatar.bytecode.VirtualMachine - Executed ADD, Stack contains [0, 54]
16:20:10.198 [main] INFO com.iluwatar.bytecode.VirtualMachine - Executed SET_HEALTH, Stack contains []
```

## 类图

![alt text](./etc/bytecode.urm.png "Bytecode class diagram")

## 适用性



当您需要定义很多行为并且游戏的实现语言不合适时，请使用字节码模式，因为：

* 它的等级太低，使得编程变得乏味或容易出错。
* 由于编译时间慢或其他工具问题，迭代它需要很长时间。
* 它有太多的信任。 如果您想确保定义的行为不会破坏游戏，您需要将其与代码库的其余部分进行沙箱化。

## 相关模式

* [Interpreter](https://java-design-patterns.com/patterns/interpreter/)

## 鸣谢

* [Game programming patterns](http://gameprogrammingpatterns.com/bytecode.html)
