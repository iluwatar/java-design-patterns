---
title: Bytecode
shortTitle: Bytecode
category: Behavioral
language: es
tag:
    - Game programming
---

## Propósito

Permite codificar el comportamiento como instrucciones para una máquina virtual.

## Explicación

Ejemplo del mundo real

> Un equipo está trabajando en un nuevo juego en el que los magos luchan entre sí. El comportamiento de los magos necesita ser cuidadosamente ajustado e iterado cientos de veces a través de pruebas de juego. No es óptimo pedir al programador que haga cambios cada vez que el diseñador del juego quiere variar el comportamiento, así que el comportamiento del mago se implementa como una máquina virtual basada en datos.

En palabras sencillas

> El patrón Bytecode permite un comportamiento dirigido por datos en lugar de por código.

[Gameprogrammingpatterns.com](https://gameprogrammingpatterns.com/bytecode.html) indica la documentación:

> Un conjunto de instrucciones define las operaciones de bajo nivel que pueden realizarse. Una serie de instrucciones se codifica como una secuencia de bytes. Una máquina virtual ejecuta estas instrucciones de una en una, utilizando una pila para los valores intermedios. La combinación de instrucciones permite definir comportamientos complejos de alto nivel.

**Ejemplo programático**

Uno de los objetos más importantes del juego es la clase Mago `Wizard`.

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

A continuación, mostramos las instrucciones disponibles para nuestra máquina virtual. Cada una de las instrucciones tiene su propia semántica sobre cómo opera con los datos de la pila. Por ejemplo, la instrucción ADD toma los dos elementos superiores de la pila, los suma y coloca el resultado en la pila.

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

En el corazón de nuestro ejemplo está la clase `VirtualMachine`. Toma instrucciones como entrada y las ejecuta para proporcionar el comportamiento del objeto de juego.

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

Ahora podemos mostrar el ejemplo completo utilizando la máquina virtual.

```java
  public static void main(String[]args){

        var vm=new VirtualMachine(
        new Wizard(45,7,11,0,0),
        new Wizard(36,18,8,0,0));

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

Aquí está la salida de la consola.

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

## Diagrama de clases

![alt text](./etc/bytecode.urm.png "Bytecode class diagram")

## Aplicabilidad

Utiliza el patrón Bytecode cuando tengas que definir muchos comportamientos y el lenguaje de implementación de tu juego no sea el adecuado porque:

* Es demasiado de bajo nivel, por lo que es tedioso o propenso a errores para programar.
* Iterar en él lleva demasiado tiempo debido a tiempos de compilación lentos u otros problemas de herramientas.
* Tiene demasiada confianza. Si quieres asegurarte de que el comportamiento definido no puede romper el juego, necesitas separarlo del resto del código base.

## Usos Conocidos

* Java Virtual Machine (JVM) utiliza bytecode para permitir que los programas Java se ejecuten en cualquier dispositivo que tenga JVM instalado.
* Python compila sus scripts a bytecode que luego es interpretado por la Máquina Virtual Python.
* NET Framework utiliza una forma de bytecode llamada Microsoft Intermediate Language (MSIL).

## Consecuencias

Ventajas:

* Portabilidad: Los programas pueden ejecutarse en cualquier plataforma que disponga de una máquina virtual compatible.
* Seguridad: La máquina virtual puede aplicar controles de seguridad al código de bytes.
* Rendimiento: Los compiladores JIT pueden optimizar el código de bytes en tiempo de ejecución, mejorando potencialmente el rendimiento respecto al código interpretado.

Desventajas:

* Sobrecarga: Ejecutar bytecode normalmente implica más sobrecarga que ejecutar código nativo, lo que puede afectar al rendimiento.
* Complejidad: Implementar y mantener una máquina virtual añade complejidad al sistema.

## Patrones relacionados

* [Intérprete](https://java-design-patterns.com/patterns/interpreter/) se utiliza a menudo dentro de la implementación de VMs para interpretar instrucciones bytecode.
* [Comando](https://java-design-patterns.com/patterns/command/): Las instrucciones bytecode pueden ser vistas como comandos ejecutados por la VM.
* [Método de fábrica](https://java-design-patterns.com/patterns/factory-method/): Las VMs pueden utilizar métodos de fábrica para instanciar operaciones o instrucciones definidas en el bytecode.

## Créditos

* [Game programming patterns](http://gameprogrammingpatterns.com/bytecode.html)
* [Programming Language Pragmatics](https://amzn.to/49Tusnn)
