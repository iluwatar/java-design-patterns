---
title: Chain of responsibility
category: Behavioral
language: es
tag:
    - Gang of Four
    - Decoupling
---

## También conocido como

* Chain of Command
* Chain of Objects
* Responsibility Chain

## Propósito

Evita acoplar el emisor de una petición a su receptor dando a más de un objeto la oportunidad de gestionar la petición. Encadena los objetos receptores y pasa la solicitud a lo largo de la cadena hasta que un objeto la gestione.

## Explicación

Ejemplo real

> El Rey Orco da órdenes en voz alta a su ejército. El más cercano a reaccionar es el comandante, luego un oficial y después un soldado. El comandante, el oficial y el soldado forman una cadena de responsabilidad.

En palabras sencillas

> Ayuda a construir una cadena de objetos. Una solicitud entra por un extremo y sigue pasando de un objeto a otro hasta que encuentra un gestor adecuado.

Wikipedia dice

> En diseño orientado a objetos, el patrón de cadena de responsabilidad es un patrón de diseño que consiste en una fuente de objetos de comando y una serie de objetos de procesamiento. Cada objeto de procesamiento contiene lógica que define los tipos de objetos de comando que puede manejar; el resto se pasa al siguiente objeto de procesamiento de la cadena.

**Ejemplo programático**

Traduciendo nuestro ejemplo con los orcos de arriba. Primero, tenemos la clase `Request`:

```java
import lombok.Getter;

@Getter
public class Request {

    private final RequestType requestType;
    private final String requestDescription;
    private boolean handled;

    public Request(final RequestType requestType, final String requestDescription) {
        this.requestType = Objects.requireNonNull(requestType);
        this.requestDescription = Objects.requireNonNull(requestDescription);
    }

    public void markHandled() {
        this.handled = true;
    }

    @Override
    public String toString() {
        return getRequestDescription();
    }
}

public enum RequestType {
    DEFEND_CASTLE, TORTURE_PRISONER, COLLECT_TAX
}
```

A continuación, mostramos la jerarquía del gestor de peticiones.

```java
public interface RequestHandler {

    boolean canHandleRequest(Request req);

    int getPriority();

    void handle(Request req);

    String name();
}

@Slf4j
public class OrcCommander implements RequestHandler {
    @Override
    public boolean canHandleRequest(Request req) {
        return req.getRequestType() == RequestType.DEFEND_CASTLE;
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public void handle(Request req) {
        req.markHandled();
        LOGGER.info("{} handling request \"{}\"", name(), req);
    }

    @Override
    public String name() {
        return "Orc commander";
    }
}

// OrcOfficer and OrcSoldier are defined similarly as OrcCommander

```

El Rey Orco da las órdenes y forma la cadena.

```java
public class OrcKing {

    private List<RequestHandler> handlers;

    public OrcKing() {
        buildChain();
    }

    private void buildChain() {
        handlers = Arrays.asList(new OrcCommander(), new OrcOfficer(), new OrcSoldier());
    }

    public void makeRequest(Request req) {
        handlers
                .stream()
                .sorted(Comparator.comparing(RequestHandler::getPriority))
                .filter(handler -> handler.canHandleRequest(req))
                .findFirst()
                .ifPresent(handler -> handler.handle(req));
    }
}
```

La cadena de responsabilidad en acción.

```java
var king=new OrcKing();
        king.makeRequest(new Request(RequestType.DEFEND_CASTLE,"defend castle"));
        king.makeRequest(new Request(RequestType.TORTURE_PRISONER,"torture prisoner"));
        king.makeRequest(new Request(RequestType.COLLECT_TAX,"collect tax"));
```

La salida de la consola.

```
Orc commander handling request "defend castle"
Orc officer handling request "torture prisoner"
Orc soldier handling request "collect tax"
```

## Diagrama de clases

![alt text](./etc/chain-of-responsibility.urm.png "Diagrama de clases de la cadena de responsabilidad")

## Aplicabilidad

Utilice Cadena de Responsabilidad cuando

* Más de un objeto puede gestionar una petición, y el gestor no se conoce a priori. El gestor debe determinarse automáticamente.
* Se desea enviar una petición a uno de varios objetos sin especificar explícitamente el receptor.
* El conjunto de objetos que pueden gestionar una solicitud debe especificarse dinámicamente.

## Usos conocidos

* Burbujeo de eventos en frameworks GUI donde un evento puede ser manejado en múltiples niveles de la jerarquía de un componente UI.
* Frameworks de middleware en los que una petición pasa a través de una cadena de objetos de procesamiento.
* Marcos de trabajo de registro donde los mensajes pueden pasar a través de una serie de registradores, cada uno posiblemente manejándolos de manera diferente.
* [java.util.logging.Logger#log()](http://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html#log%28java.util.logging.Level,%20java.lang.String%29)
* [Apache Commons Chain](https://commons.apache.org/proper/commons-chain/index.html)
* [javax.servlet.Filter#doFilter()](http://docs.oracle.com/javaee/7/api/javax/servlet/Filter.html#doFilter-javax.servlet.ServletRequest-javax.servlet.ServletResponse-javax.servlet.FilterChain-)

## Consecuencias

Ventajas:

* Acoplamiento reducido. El emisor de una petición no necesita conocer el manejador concreto que procesará la petición.
* Mayor flexibilidad a la hora de asignar responsabilidades a los objetos. Se pueden añadir o cambiar responsabilidades para gestionar una petición cambiando los miembros y el orden de la cadena.
* Permite establecer un gestor por defecto si no hay ningún gestor concreto que pueda gestionar la solicitud.

Desventajas:

* Puede ser difícil depurar y entender el flujo, especialmente si la cadena es larga y compleja.
* La petición puede quedar sin gestionar si la cadena no incluye un gestor "catch-all".
* Pueden surgir problemas de rendimiento debido a la posibilidad de pasar por varios gestores antes de encontrar el correcto, o no encontrarlo en absoluto.

## Patrones Relacionados

* [Comando](https://java-design-patterns.com/patterns/command/): puede ser usado para encapsular una petición como un objeto, que puede ser pasado a lo largo de la cadena.
* [Composite](https://java-design-patterns.com/patterns/composite/): la Cadena de Responsabilidad se aplica a menudo junto con el patrón Composite.
* [Decorator](https://java-design-patterns.com/patterns/decorator/): los decoradores pueden encadenarse de forma similar a las responsabilidades en el patrón Cadena de responsabilidad.

## Créditos

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Pattern-Oriented Software Architecture, Volume 1: A System of Patterns](https://amzn.to/3PAJUg5)
* [Refactoring to Patterns](https://amzn.to/3VOO4F5)
* [Pattern languages of program design 3](https://amzn.to/4a4NxTH)
