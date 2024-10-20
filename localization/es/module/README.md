---
title: Module
shortTitle: Module
category: Structural
language: es
tag:
 - Decoupling
---

## Propósito
El patrón de módulos se utiliza para implementar el concepto de módulos de software, definido por la programación modular, en un lenguaje de programación con soporte directo incompleto para el concepto.

## Explicación

Ejemplo real

> En una bulliciosa ciudad del software, distintos componentes de software, como la base de datos, la interfaz de usuario y la API, a menudo necesitan colaborar. En lugar de que cada componente hable directamente con los demás, confían en el gestor de módulos. Este gestor de módulos actúa como un mercado central, donde cada componente registra sus servicios y solicitudes para los demás. Esto garantiza que los componentes permanezcan desacoplados y que los cambios en uno de ellos no afecten a todo el sistema.


> Imagina un smartphone moderno. Tiene distintas aplicaciones, como mensajería, cámara y reproductor de música. Aunque cada una funciona de forma independiente, a veces necesitan compartir recursos como el acceso a los contactos o el almacenamiento. En lugar de que cada aplicación tenga su propia forma de acceder a estos recursos, utilizan los módulos integrados del teléfono, como el módulo de contactos o el de almacenamiento. Esto garantiza una experiencia coherente para el usuario y evita posibles conflictos entre aplicaciones.

En pocas palabras

> El patrón Módulo encapsula funciones y datos relacionados en una sola unidad, lo que permite tener componentes de software organizados y manejables.

Wikipedia dice

> En ingeniería de software, el patrón módulo es un patrón de diseño utilizado para implementar el concepto de módulos de software, definido por la programación modular, en un lenguaje de programación con soporte directo incompleto para el concepto.

> Este patrón se puede implementar de varias maneras dependiendo del lenguaje de programación anfitrión, como el patrón de diseño singleton, miembros estáticos orientados a objetos en una clase y funciones globales de procedimiento. En Python, el patrón está integrado en el lenguaje, y cada archivo .py es automáticamente un módulo. Lo mismo ocurre en Ada, donde el paquete puede considerarse un módulo (similar a una clase estática).

**Ejemplo programático**

```java
//Define Logger abstract class
abstract class Logger {
    protected String output;
    protected String error;

    public abstract void prepare();
    public abstract void unprepare();
    public abstract void printString(String message);
    public abstract void printErrorString(String errorMessage);
}

//File log module
class FileLoggerModule extends Logger {
    private static final String OUTPUT_FILE = "output.log";
    private static final String ERROR_FILE = "error.log";

    private static FileLoggerModule instance;

    private FileLoggerModule() {
        this.output = OUTPUT_FILE;
        this.error = ERROR_FILE;
    }

    public static FileLoggerModule getSingleton() {
        if (instance == null) {
            instance = new FileLoggerModule();
        }
        return instance;
    }

    @Override
    public void prepare() {
        // For example, open file operation
        // add the action you want
    }

    @Override
    public void unprepare() {
        // For example, close file operation
        // add the action you want
    }

    @Override
    public void printString(String message) {
        System.out.println("Writing to " + output + ": " + message);
    }

    @Override
    public void printErrorString(String errorMessage) {
        System.out.println("Writing to " + error + ": " + errorMessage);
    }
}

//Console log module
class ConsoleLoggerModule extends Logger {
    private static ConsoleLoggerModule instance;

    private ConsoleLoggerModule() {}

    public static ConsoleLoggerModule getSingleton() {
        if (instance == null) {
            instance = new ConsoleLoggerModule();
        }
        return instance;
    }

    @Override
    public void prepare() {
        //Initialize console operation
    }

    @Override
    public void unprepare() {
        //End console operation
    }

    @Override
    public void printString(String message) {
        System.out.println("Console Output: " + message);
    }

    @Override
    public void printErrorString(String errorMessage) {
        System.err.println("Console Error: " + errorMessage);
    }
}


public class App {
    public void prepare() {
        FileLoggerModule.getSingleton().prepare();
        ConsoleLoggerModule.getSingleton().prepare();
    }

    public void unprepare() {
        FileLoggerModule.getSingleton().unprepare();
        ConsoleLoggerModule.getSingleton().unprepare();
    }

    public void execute(String message) {
        FileLoggerModule.getSingleton().printString(message);
        ConsoleLoggerModule.getSingleton().printString(message);
    }

    public static void main(String[] args) {
        App app = new App();
        app.prepare();
        app.execute("Hello, Module Pattern!");
        app.unprepare();
    }
}
```

Resultados del programa:

```
Writing to output.log: Hello, Module Pattern!
Console Output: Hello, Module Pattern!
```

## Diagrama de clases
![alt text](./etc/module.png "Module")

## Aplicabilidad
El patrón Módulo puede considerarse un patrón de creación y un patrón estructural. Gestiona la creación y organización de otros elementos, y los agrupa como lo hace el patrón estructural.

Un objeto que aplica este patrón puede proporcionar el equivalente de un espacio de nombres, proporcionando el proceso de inicialización y finalización de una clase estática o de una clase con miembros estáticos con una sintaxis y una semántica más limpias y concisas.

## Créditos

* [Module](https://en.wikipedia.org/wiki/Module_pattern)
