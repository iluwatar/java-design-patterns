---
title: Context object
category: Creational
language: en
tags:
- Data access
---

## Name / classification

Context Object

## Also known as

Context, Encapsulate Context

## Intent

Decouple data from protocol-specific classes and store the scoped data in an object independent 
of the underlying protocol technology. 

## Explanation

Real-world example

> This application has different layers labelled A, B and C with each extracting specific information
> from a similar context for further use in the software. Passing down each pieces of information 
> individually would be inefficient, a method to efficiently store and pass information is needed.  

In plain words

> Create an object and store the data there and pass this object to where it is needed.

[Core J2EE Patterns](http://corej2eepatterns.com/ContextObject.htm) says

> Use a Context Object to encapsulate state in a protocol-independent way to be shared throughout your application. 

**Programmatic Example**

We define what data a service context object contains.

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

Create an interface used in parts of the application for context objects to be created.

```Java
public class ServiceContextFactory {

    public static ServiceContext createContext() {
        return new ServiceContext();
    }
}
```

Instantiate the context object in the first layer and the adjoining layer upcalls the context in the current layer, which
then further structures the object.

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
Here is the context object and layers in action.

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

Program output:

```Java
Context = SERVICE null null
Context = SERVICE SERVICE null
Context = SERVICE SERVICE SERVICE
```

## Class diagram

![alt text](./etc/context-object.png "Context object")

## Application

Use the Context Object pattern for:

* Sharing information across different system layers.
* Decoupling software data from protocol-specific contexts.
* Exposing only the relevant API's within the context.

## Known uses
* [Spring: ApplicationContext](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ApplicationContext.html)
* [Oracle: SecurityContext](https://docs.oracle.com/javaee/7/api/javax/ws/rs/core/SecurityContext.html)
* [Oracle: ServletContext](https://docs.oracle.com/javaee/6/api/javax/servlet/ServletContext.html)

## Credits

* [Core J2EE Design Patterns](https://amzn.to/3IhcY9w)
* [Core J2EE Design Patterns website - Context Object](http://corej2eepatterns.com/ContextObject.htm)
* [Allan Kelly - The Encapsulate Context Pattern](https://accu.org/journals/overload/12/63/kelly_246/)
* [Arvid S. Krishna et al. - Context Object](https://www.dre.vanderbilt.edu/~schmidt/PDF/Context-Object-Pattern.pdf)
