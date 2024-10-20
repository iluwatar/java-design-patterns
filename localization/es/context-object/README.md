---
title: Context object
shortTitle: Context object
category: Creational
language: es
tags:
  - Data access
---

## Nombre / Clasificación

Context Object

## También conocido como

Context, Encapsulate Context

## Propósito

Desacoplar los datos de las clases específicas del protocolo y almacenar los datos del ámbito en un objeto independiente
de la tecnología del protocolo subyacente.

## Explicación

Ejemplo del mundo real

> Esta aplicación tiene diferentes capas etiquetadas como A, B y C, cada una extrayendo información específica de un
> contexto similar para su uso posterior en el software. Pasar cada pieza de información individualmente sería
> ineficiente, se necesita un método para almacenar y pasar información de manera eficiente.
> En palabras sencillas

> Crea un objeto y almacena los datos allí y pasa este objeto a donde se necesita.

[Core J2EE Patterns](http://corej2eepatterns.com/ContextObject.htm) dice

> Utilice un objeto de contexto (Context Object) para encapsular el estado de una manera independiente del protocolo para ser compartido
> en toda tu aplicación.

**Ejemplo programático**

Definimos qué datos contiene un objeto de contexto (Context Object) de servicio `ServiceContext`.

```Java
public class ServiceContext {

    String ACCOUNT_SERVICE, SESSION_SERVICE, SEARCH_SERVICE;

    public void setACCOUNT_SERVICE(String ACCOUNT_SERVICE) {
        this.ACCOUNT_SERVICE = ACCOUNT_SERVICE;
    }

    public void setSESSION_SERVICE(String SESSION_SERVICE) {
        this.SESSION_SERVICE = SESSION_SERVICE;
    }

    public void setSEARCH_SERVICE(String SEARCH_SERVICE) {
        this.SEARCH_SERVICE = SEARCH_SERVICE;
    }

    public String getACCOUNT_SERVICE() {
        return ACCOUNT_SERVICE;
    }

    public String getSESSION_SERVICE() {
        return SESSION_SERVICE;
    }

    public String getSEARCH_SERVICE() {
        return SEARCH_SERVICE;
    }
    
    public String toString() { return ACCOUNT_SERVICE + " " + SESSION_SERVICE + " " + SEARCH_SERVICE;}
}
```

Se crea una interfaz `ServiceContextFactory` utilizada en partes de la aplicación para crear objetos de contexto.

```Java
public class ServiceContextFactory {

    public static ServiceContext createContext() {
        return new ServiceContext();
    }
}
```

Instanciar el objeto de contexto en la primera capa `LayerA` y la capa contigua `LayerB` hasta llamar al contexto en la capa actual `LayerC`,
que estructura el objeto.

```Java
public class LayerA {

    private static ServiceContext context;

    public LayerA() {
        context = ServiceContextFactory.createContext();
    }

    public static ServiceContext getContext() {
        return context;
    }

    public void addAccountInfo(String accountService) {
        context.setACCOUNT_SERVICE(accountService);
    }
}

public class LayerB {

    private static ServiceContext context;

    public LayerB(LayerA layerA) {
        this.context = layerA.getContext();
    }

    public static ServiceContext getContext() {
        return context;
    }

    public void addSessionInfo(String sessionService) {
        context.setSESSION_SERVICE(sessionService);
    }
}

public class LayerC {

    public static ServiceContext context;

    public LayerC(LayerB layerB) {
        this.context = layerB.getContext();
    }

    public static ServiceContext getContext() {
        return context;
    }

    public void addSearchInfo(String searchService) {
        context.setSEARCH_SERVICE(searchService);
    }
}
```

He aquí el objeto de contexto y las capas en acción.

```Java
var layerA = new LayerA();
layerA.addAccountInfo(SERVICE);
LOGGER.info("Context = {}",layerA.getContext());
var layerB = new LayerB(layerA);
layerB.addSessionInfo(SERVICE);
LOGGER.info("Context = {}",layerB.getContext());
var layerC = new LayerC(layerB);
layerC.addSearchInfo(SERVICE);
LOGGER.info("Context = {}",layerC.getContext());
```

Salida del programa:

```Java
Context = SERVICE null null
Context = SERVICE SERVICE null
Context = SERVICE SERVICE SERVICE
```

## Diagrama de clases

![alt text](./etc/context-object.png "Context object")

## Aplicabilidad

Utilizar el patrón Objeto Contexto (Context Object) para:

* Compartir información entre diferentes capas del sistema.
* Desacoplar datos de software de contextos específicos de protocolo.
* Exponer solo las API relevantes dentro del contexto.

## Usos conocidos

* [Spring: ApplicationContext](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ApplicationContext.html)
* [Oracle: SecurityContext](https://docs.oracle.com/javaee/7/api/javax/ws/rs/core/SecurityContext.html)
* [Oracle: ServletContext](https://docs.oracle.com/javaee/6/api/javax/servlet/ServletContext.html)

## Créditos

* [Core J2EE Design Patterns](https://amzn.to/3IhcY9w)
* [Core J2EE Design Patterns website - Context Object](http://corej2eepatterns.com/ContextObject.htm)
* [Allan Kelly - The Encapsulate Context Pattern](https://accu.org/journals/overload/12/63/kelly_246/)
* [Arvid S. Krishna et al. - Context Object](https://www.dre.vanderbilt.edu/~schmidt/PDF/Context-Object-Pattern.pdf)
