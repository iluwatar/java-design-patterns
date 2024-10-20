---
title: Command
shortTitle: Command
category: Behavioral
language: es
tag:
    - Gang of Four
---

## También conocido como

* Action
* Transaction

## Propósito

El patrón de diseño Command encapsula una petición como un objeto, permitiendo así la parametrización de clientes con colas, peticiones y operaciones. También permite soportar operaciones deshechas.

## Explicación

Ejemplo real

> Hay un mago lanzando hechizos sobre un goblin. Los hechizos se ejecutan sobre el duende uno a uno. El primer hechizo encoge al duende y el segundo lo hace invisible. A continuación, el mago invierte los hechizos uno a uno. Cada hechizo es un objeto de comando que se puede deshacer.

En palabras simples:

> Almacenar peticiones como objetos de comando permite realizar una acción o deshacerla en un momento posterior.

Wikipedia dice:

> En programación orientada a objetos, el patrón de comandos es un patrón de diseño de comportamiento en el que un objeto se utiliza para encapsular toda la información necesaria para realizar una acción o desencadenar un evento en un momento posterior.

**Ejemplo programático**

Aquí está el código de ejemplo con mago `Wizard` y duende `Goblin`. Empecemos por la clase Mago `Wizard`.

```java

@Slf4j
public class Wizard {

    private final Deque<Runnable> undoStack = new LinkedList<>();
    private final Deque<Runnable> redoStack = new LinkedList<>();

    public Wizard() {
    }

    public void castSpell(Runnable runnable) {
        runnable.run();
        undoStack.offerLast(runnable);
    }

    public void undoLastSpell() {
        if (!undoStack.isEmpty()) {
            var previousSpell = undoStack.pollLast();
            redoStack.offerLast(previousSpell);
            previousSpell.run();
        }
    }

    public void redoLastSpell() {
        if (!redoStack.isEmpty()) {
            var previousSpell = redoStack.pollLast();
            undoStack.offerLast(previousSpell);
            previousSpell.run();
        }
    }

    @Override
    public String toString() {
        return "Wizard";
    }
}
```

A continuación, tenemos al duende `Goblin` que es el objetivo `Target` de los hechizos.

```java

@Slf4j
public abstract class Target {

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

    public void changeSize() {
        var oldSize = getSize() == Size.NORMAL ? Size.SMALL : Size.NORMAL;
        setSize(oldSize);
    }

    public void changeVisibility() {
        var visible = getVisibility() == Visibility.INVISIBLE
                ? Visibility.VISIBLE : Visibility.INVISIBLE;
        setVisibility(visible);
    }
}
```

Por último, tenemos al mago en la función principal lanzando hechizos.

```java
public static void main(String[]args){
        var wizard=new Wizard();
        var goblin=new Goblin();

        // casts shrink/unshrink spell
        wizard.castSpell(goblin::changeSize);

        // casts visible/invisible spell
        wizard.castSpell(goblin::changeVisibility);

        // undo and redo casts
        wizard.undoLastSpell();
        wizard.redoLastSpell();
```

Este es el ejemplo en acción.

```java
var wizard=new Wizard();
        var goblin=new Goblin();

        goblin.printStatus();
        wizard.castSpell(goblin::changeSize);
        goblin.printStatus();

        wizard.castSpell(goblin::changeVisibility);
        goblin.printStatus();

        wizard.undoLastSpell();
        goblin.printStatus();

        wizard.undoLastSpell();
        goblin.printStatus();

        wizard.redoLastSpell();
        goblin.printStatus();

        wizard.redoLastSpell();
        goblin.printStatus();
```

Aquí está la salida del programa:

```java
Goblin,[size=normal][visibility=visible]
        Goblin,[size=small][visibility=visible]
        Goblin,[size=small][visibility=invisible]
        Goblin,[size=small][visibility=visible]
        Goblin,[size=normal][visibility=visible]
        Goblin,[size=small][visibility=visible]
        Goblin,[size=small][visibility=invisible]
```

## Diagrama de clases

![alt text](./etc/command.png "Command")

## Aplicabilidad

Utilice el patrón Comando (Command) para:

* Parametrizar objetos mediante una acción a realizar. Puedes expresar dicha parametrización en un lenguaje procedimental con una función callback, es decir, una función que se registra en algún lugar para ser llamada en un momento posterior. Los comandos son un sustituto orientado a objetos de las retrollamadas.
* Especifican, ponen en cola y ejecutan peticiones en diferentes momentos. Un objeto Command puede tener una vida independiente de la petición original. Si el receptor de una petición puede ser representado de una manera independiente del espacio de direcciones, entonces puedes transferir un objeto comando para la petición a un proceso diferente y cumplir la petición allí.
* Soporta deshacer. La operación de ejecución del comando puede almacenar el estado para revertir sus efectos en el propio comando. La interfaz del Comando debe tener una operación añadida de des-ejecutar que revierta los efectos de una llamada previa a ejecutar. Los comandos ejecutados se almacenan en una lista de historial. La funcionalidad de deshacer y rehacer a nivel ilimitado se consigue recorriendo esta lista hacia atrás y hacia delante llamando a un-ejecutar y ejecutar, respectivamente.
* Soporta el registro de cambios para que puedan volver a aplicarse en caso de caída del sistema. Al aumentar la interfaz de comandos con operaciones de carga y almacenamiento, puede mantener un registro persistente de los cambios. La recuperación de un fallo implica volver a cargar los comandos registrados desde el disco y volver a ejecutarlos con la operación de ejecución.
* Estructurar un sistema en torno a operaciones de alto nivel construidas sobre operaciones primitivas. Esta estructura es común en los sistemas de información que admiten transacciones. Una transacción encapsula un conjunto de cambios de datos. El patrón Command ofrece una forma de modelar las transacciones. Los comandos tienen una interfaz común que permite invocar todas las transacciones de la misma manera. El patrón también facilita la ampliación del sistema con nuevas transacciones.
* Mantener un historial de peticiones.
* Implementar la funcionalidad de callback.
* Implementar la funcionalidad de deshacer.

## Usos conocidos

* Botones GUI y elementos de menú en aplicaciones de escritorio.
* Operaciones en sistemas de bases de datos y sistemas transaccionales que soportan rollback.
* Grabación de macros en aplicaciones como editores de texto y hojas de cálculo.
* [java.lang.Runnable](http://docs.oracle.com/javase/8/docs/api/java/lang/Runnable.html)
* [org.junit.runners.model.Statement](https://github.com/junit-team/junit4/blob/master/src/main/java/org/junit/runners/model/Statement.java)
* [Netflix Hystrix](https://github.com/Netflix/Hystrix/wiki)
* [javax.swing.Action](http://docs.oracle.com/javase/8/docs/api/javax/swing/Action.html)

## Consecuencias

Ventajas:

* Desacopla el objeto que invoca la operación del que sabe cómo realizarla.
* Es fácil añadir nuevos Comandos, porque no tienes que cambiar las clases existentes.
* Puedes ensamblar un conjunto de comandos en un comando compuesto.

Desventajas:

* Aumenta el número de clases para cada comando individual.
* Puede complicar el diseño al añadir múltiples capas entre emisores y receptores.

## Patrones Relacionados

* [Composite](https://java-design-patterns.com/patterns/composite/): Los comandos pueden ser compuestos usando el patrón Composite para crear macro comandos.
* [Memento](https://java-design-patterns.com/patterns/memento/): Puede usarse para implementar mecanismos de deshacer.
* [Observador](https://java-design-patterns.com/patterns/observer/): El patrón puede ser observado para cambios que activan comandos.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=f27d2644fbe5026ea448791a8ad09c94)
* [Pattern-Oriented Software Architecture, Volume 1: A System of Patterns](https://amzn.to/3PFUqSY)
