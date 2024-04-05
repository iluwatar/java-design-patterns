---
title: Retry
category: Behavioral
language: es
tag:
  - Performance
  - Cloud distributed
---

## Propósito

Reintentar de forma transparente determinadas operaciones que implican la comunicación con recursos externos, en particular a través de la red, aislando el código de llamada de los detalles de implementación del reintento.

## Explicación

El patrón de reintento consiste en reintentar operaciones sobre recursos remotos a través de la red un número determinado de veces. Depende estrechamente de los requisitos empresariales y técnicos: ¿Cuánto tiempo permitirá la empresa que espere el usuario final hasta que finalice la operación? ¿Cuáles son las características de rendimiento del recurso remoto durante los picos de carga, así como de nuestra aplicación a medida que más hilos esperan la disponibilidad del recurso remoto? Entre los errores devueltos por el servicio remoto, ¿cuáles pueden ignorarse con seguridad para volver a intentarlo? ¿Es la operación [idempotent](https://en.wikipedia.org/wiki/Idempotence)?

Otra preocupación es el impacto en el código de llamada al implementar el mecanismo de reintento. Idealmente, la mecánica de reintento debería ser completamente transparente para el código de llamada (la interfaz del servicio permanece inalterada). Existen dos enfoques generales para este problema: desde el punto de vista de la arquitectura empresarial (estratégico) y desde el punto de vista de la biblioteca compartida (táctico).

Desde un punto de vista estratégico, esto se resolvería redirigiendo las peticiones a un sistema intermediario separado, tradicionalmente un [ESB](https://en.wikipedia.org/wiki/Enterprise_service_bus), pero más recientemente un [Service Mesh](https://medium.com/microservices-in-practice/service-mesh-for-microservices-2953109a3c9a).

Desde un punto de vista táctico, esto se resolvería reutilizando bibliotecas compartidas como [Hystrix](https://github.com/Netflix/Hystrix) (nótese que Hystrix es una implementación completa del patrón [Circuit Breaker](https://java-design-patterns.com/patterns/circuit-breaker/), del que el patrón Retry puede considerarse un subconjunto). Este es el tipo de solución que se muestra en el sencillo ejemplo que acompaña a este `README.md`.

Ejemplo real

> Nuestra aplicación utiliza un servicio que proporciona información sobre clientes. De vez en cuando el servicio parece fallar y puede devolver errores o a veces simplemente se desconecta. Para evitar estos problemas aplicamos el patrón retry.

En palabras simples

> El patrón de reintento reintenta de forma transparente las operaciones fallidas a través de la red.

[Documentación de Microsoft](https://docs.microsoft.com/en-us/azure/architecture/patterns/retry) dice

> Permite a una aplicación manejar fallos transitorios cuando intenta conectarse a un servicio o recurso de red, reintentando de forma transparente una operación fallida. Esto puede mejorar la estabilidad de la aplicación.

**Ejemplo programático**

En nuestra aplicación hipotética, tenemos una interfaz genérica para todas las operaciones sobre interfaces remotas.

```java
public interface BusinessOperation<T> {
  T perform() throws BusinessException;
}
```

Y tenemos una implementación de esta interfaz que encuentra a nuestros clientes buscando en una base de datos.

```java
public final class FindCustomer implements BusinessOperation<String> {
  @Override
  public String perform() throws BusinessException {
    ...
  }
}
```

Nuestra implementación de `FindCustomer` puede configurarse para lanzar `BusinessException`s antes de devolver el ID del cliente, simulando así un servicio defectuoso que falla intermitentemente. Algunas excepciones, como la `CustomerNotFoundException`, se consideran recuperables tras un hipotético análisis porque la causa raíz del error proviene de "algún problema de bloqueo de la base de datos". Sin embargo, la `DatabaseNotAvailableException` se considera definitivamente un showtopper - la aplicación no debe intentar recuperarse de este error.

Podemos modelar un escenario recuperable instanciando `FindCustomer` así:

```java
final var op = new FindCustomer(
    "12345",
    new CustomerNotFoundException("not found"),
    new CustomerNotFoundException("still not found"),
    new CustomerNotFoundException("don't give up yet!")
);
```

En esta configuración, `FindCustomer` lanzará `CustomerNotFoundException` tres veces, tras lo cual devolverá sistemáticamente el ID del cliente (`12345`).

En nuestro escenario hipotético, nuestros analistas indican que esta operación suele fallar entre 2 y 4 veces para una entrada determinada durante las horas punta, y que cada hilo de trabajo del subsistema de base de datos suele necesitar 50 ms para "recuperarse de un error". Aplicando estas políticas se obtendría algo así:

```java
final var op = new Retry<>(
    new FindCustomer(
        "1235",
        new CustomerNotFoundException("not found"),
        new CustomerNotFoundException("still not found"),
        new CustomerNotFoundException("don't give up yet!")
    ),
    5,
    100,
    e -> CustomerNotFoundException.class.isAssignableFrom(e.getClass())
);
```

Ejecutando `op` una vez se lanzarían automáticamente como máximo 5 intentos de reintento, con un retardo de 100 milisegundos entre intentos, ignorando cualquier `CustomerNotFoundException` lanzada durante el intento. En este escenario en particular, debido a la configuración de `FindCustomer`, habrá 1 intento inicial y 3 reintentos adicionales antes de devolver finalmente el resultado deseado `12345`.

Si nuestra operación `FindCustomer` lanzara una fatal `DatabaseNotFoundException`, la cual se nos instruyó no ignorar, pero más importante aún, no instruimos a nuestro `Retry` ignorar, entonces la operación habría fallado inmediatamente al recibir el error, sin importar cuantos intentos quedaran.

## Diagrama de clases

![alt text](./etc/retry.png "Retry")

## Aplicabilidad

Siempre que una aplicación necesite comunicarse con un recurso externo, especialmente en un entorno de nube, y si los requisitos empresariales lo permiten.

## Consecuencias

**Pros:**

* Resistencia
* Proporciona datos concretos sobre fallos externos

**Desventajas**

* Complejidad
* Mantenimiento de operaciones

## Patrones relacionados


* [Circuit Breaker](https://java-design-patterns.com/patterns/circuit-breaker/)

## Créditos

* [Retry pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/retry)
* [Cloud Design Patterns: Prescriptive Architecture Guidance for Cloud Applications](https://www.amazon.com/gp/product/1621140369/ref=as_li_tl?ie=UTF8&tag=javadesignpat-20&camp=1789&creative=9325&linkCode=as2&creativeASIN=1621140369&linkId=3e3f686af5e60a7a453b48adb286797b)
