---
title: Commander
category: Behavioral
language: es
tag:
    - Cloud distributed
    - Microservices
    - Transactions
---

## También conocido como

* Distributed Transaction Commander
* Transaction Coordinator

## Propósito

La intención del patrón Commander en el contexto de las transacciones distribuidas es gestionar y coordinar transacciones complejas a través de múltiples componentes o servicios distribuidos, asegurando la consistencia e integridad de la transacción global. Encapsula comandos de transacciones y lógica de coordinación, facilitando la implementación de protocolos de transacciones distribuidas como commit de dos fases o Saga.

## Explicación

Ejemplo real

> Imagine que organiza un gran festival internacional de música en el que están programadas actuaciones de varios grupos de todo el mundo. La llegada, la prueba de sonido y la actuación de cada grupo son como transacciones individuales en un sistema distribuido. El organizador del festival actúa como el "Comandante", coordinando estas transacciones para garantizar que si el vuelo de una banda se retrasa (similar a un fallo de transacción), hay un plan de respaldo, como reprogramar o intercambiar franjas horarias con otra banda (acciones compensatorias), para mantener intacto el programa general. Esta configuración refleja el patrón del Comandante en las transacciones distribuidas, en las que varios componentes deben coordinarse para lograr un resultado satisfactorio a pesar de los fallos individuales.

En palabras sencillas

> El patrón Commander convierte una petición en un objeto independiente, permitiendo la parametrización de comandos, la puesta en cola de acciones y la implementación de operaciones de deshacer.

**Ejemplo programático**

La gestión de transacciones a través de diferentes servicios en un sistema distribuido, como una plataforma de comercio electrónico con microservicios separados de Pago y Envío, requiere una cuidadosa coordinación para evitar problemas. Cuando un usuario realiza un pedido pero un servicio (por ejemplo, Pago) no está disponible mientras que el otro (por ejemplo, Envío) está listo, necesitamos una solución robusta para manejar esta discrepancia.

Una estrategia para resolver este problema consiste en utilizar un componente Commander que orqueste el proceso. Inicialmente, el pedido es procesado por el servicio disponible (Envío en este caso). A continuación, el comandante intenta sincronizar el pedido con el servicio no disponible en ese momento (Pago) almacenando los detalles del pedido en una base de datos o poniéndolo en cola para su procesamiento futuro. Este sistema de colas también debe tener en cuenta posibles fallos al añadir solicitudes a la cola.

El comandante intenta repetidamente procesar los pedidos en cola para garantizar que ambos servicios reflejen finalmente los mismos datos de transacción. Este proceso implica garantizar la idempotencia, lo que significa que incluso si la misma solicitud de sincronización de pedidos se realiza varias veces, sólo se ejecutará una vez, evitando transacciones duplicadas. El objetivo es lograr una coherencia final entre los servicios, en la que todos los sistemas se sincronicen a lo largo del tiempo a pesar de los fallos o retrasos iniciales.

En el código proporcionado, el patrón Commander se utiliza para manejar transacciones distribuidas a través de múltiples servicios (PaymentService, ShippingService, MessagingService, EmployeeHandle). Cada servicio tiene su propia base de datos y puede lanzar excepciones para simular fallos.

La clase Commander es la parte central de este patrón. Toma instancias de todos los servicios y sus bases de datos, junto con algunos parámetros de configuración. El método placeOrder de la clase Commander se utiliza para realizar un pedido, lo que implica interactuar con todos los servicios.

```java
public class Commander {
    // ... constructor and other methods ...

    public void placeOrder(Order order) {
        // ... implementation ...
    }
}
```

Las clases Usuario y Pedido representan un usuario y un pedido respectivamente. Un pedido lo realiza un usuario.

```java
public class User {
    // ... constructor and other methods ...
}

public class Order {
    // ... constructor and other methods ...
}
```

Cada servicio (por ejemplo, PaymentService, ShippingService, MessagingService, EmployeeHandle) tiene su propia base de datos y puede lanzar excepciones para simular fallos. Por ejemplo, el PaymentService puede lanzar una DatabaseUnavailableException si su base de datos no está disponible.

```java
public class PaymentService {
    // ... constructor and other methods ...
}
```

Las clases DatabaseUnavailableException, ItemUnavailableException y ShippingNotPossibleException representan diferentes tipos de excepciones que pueden ocurrir.

```java
public class DatabaseUnavailableException extends Exception {
    // ... constructor and other methods ...
}

public class ItemUnavailableException extends Exception {
    // ... constructor and other methods ...
}

public class ShippingNotPossibleException extends Exception {
    // ... constructor and other methods ...
}
```

En el método principal de cada clase (AppQueueFailCases, AppShippingFailCases), se simulan diferentes escenarios creando instancias de la clase Commander con diferentes configuraciones y llamando al método placeOrder.

## Diagrama de clases

![alt text](./etc/commander.urm.png "Commander class diagram")

## Aplicabilidad

Utilice el patrón Commander para transacciones distribuidas cuando:

* Necesites asegurar la consistencia de los datos entre servicios distribuidos en caso de fallos parciales del sistema.
* Las transacciones abarcan múltiples microservicios o componentes distribuidos que requieren un commit o rollback coordinado.
* Está implementando transacciones de larga duración que requieren acciones compensatorias para la reversión.

## Usos conocidos

* Protocolos Two-Phase Commit (2PC): Coordinación de commit o rollback a través de bases de datos o servicios distribuidos.
* Implementaciones del patrón Saga: Gestión de procesos de negocio de larga duración que abarcan múltiples microservicios, con cada paso teniendo una acción compensatoria para la reversión.
* Transacciones distribuidas en arquitectura de microservicios: Coordinación de operaciones complejas entre microservicios manteniendo la integridad y consistencia de los datos.

## Consecuencias

Beneficios:

* Proporciona un mecanismo claro para gestionar transacciones distribuidas complejas, mejorando la fiabilidad del sistema.
* Permite la implementación de transacciones compensatorias, que son cruciales para mantener la coherencia en transacciones de larga duración.
* Facilita la integración de sistemas heterogéneos en un contexto transaccional.

Contrapartidas:

* Aumenta la complejidad, especialmente en situaciones de fallo, debido a la necesidad de mecanismos de reversión coordinados.
* Potencialmente afecta al rendimiento debido a la sobrecarga de la coordinación y las comprobaciones de coherencia.
* Las implementaciones basadas en Saga pueden llevar a una mayor complejidad en la comprensión del flujo global del proceso de negocio.

## Patrones relacionados

[Saga Pattern](https://java-design-patterns.com/patterns/saga/): A menudo se discute junto con el patrón Commander para transacciones distribuidas, centrándose en transacciones de larga duración con acciones compensatorias.

## Créditos

* [Distributed Transactions: The Icebergs of Microservices](https://www.grahamlea.com/2016/08/distributed-transactions-microservices-icebergs/)
* [Microservices Patterns: With examples in Java](https://amzn.to/4axjnYW)
* [Designing Data-Intensive Applications: The Big Ideas Behind Reliable, Scalable, and Maintainable Systems](https://amzn.to/4axHwOV)
* [Enterprise Integration Patterns: Designing, Building, and Deploying Messaging Solutions](https://amzn.to/4aATcRe)
