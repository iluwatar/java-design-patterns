---
title: Ambassador
shortTitle: Ambassador
category: Structural
language: es
tag:
  - Decoupling
  - Cloud distributed
---

## Propósito

Proporcionar una instancia de servicio auxiliar a un cliente y delegar en ella las funcionalidades comunes de un recurso compartido.

## Explicación

Ejemplo real

> Un servicio remoto tiene muchos clientes accediendo a una función que este servicio proporciona. El servicio es una aplicación heredada y
> es imposible actualizarla. Un gran número de solicitudes por parte de los usuarios están causando problemas de conectividad. Nuevas reglas
> respecto a la frecuencia de solicitudes deberían implementarse junto con comprobaciones de latencia y registros del lado del cliente.

En otras palabras

> Con el patrón Ambassador, podemos implementar una menor frecuencia en solicitudes de clientes junto con comprobaciones de latencia y
> registros.

Según la Documentación de Microsoft

> Un servicio de Ambassador puede considerarse como un proxy fuera de proceso que coexiste con el cliente.
>
> Este patrón puede ser útil para la descarga de tareas comunes de conectividad de cliente, como la supervisión, el registro, el enrutamiento,
> la seguridad (por ejemplo, TLS) y los patrones de resistencia(*) de una manera independiente del lenguaje. A menudo se utiliza con aplicaciones heredadas,
> u otras aplicaciones que son difíciles de modificar, con el fin de ampliar sus capacidades de red. También puede
> habilitar un equipo especializado para implementar esas características.

**Código de ejemplo**

Con la introducción anterior en mente vamos a imitar su funcionalidad en el siguiente ejemplo. Tenemos una interface implementada
por el servicio remoto así como el servicio ambassador:

```java
interface RemoteServiceInterface {
    long doRemoteFunction(int value) throws Exception;
}
```

Un servicio remoto representado como un singleton (Instancia única).

```java
@Slf4j
public class RemoteService implements RemoteServiceInterface {
    private static RemoteService service = null;

    static synchronized RemoteService getRemoteService() {
        if (service == null) {
            service = new RemoteService();
        }
        return service;
    }

    private RemoteService() {}

    @Override
    public long doRemoteFunction(int value) {
        long waitTime = (long) Math.floor(Math.random() * 1000);

        try {
            sleep(waitTime);
        } catch (InterruptedException e) {
            LOGGER.error("Thread sleep interrupted", e);
        }

        return waitTime >= 200 ? value * 10 : -1;
    }
}
```

Un servicio ambassador añadiendo funcionalidades adicionales como registros, comprobación de latencia

```java
@Slf4j
public class ServiceAmbassador implements RemoteServiceInterface {
  private static final int RETRIES = 3;
  private static final int DELAY_MS = 3000;

  ServiceAmbassador() {
  }

  @Override
  public long doRemoteFunction(int value) {
    return safeCall(value);
  }

  private long checkLatency(int value) {
    var startTime = System.currentTimeMillis();
    var result = RemoteService.getRemoteService().doRemoteFunction(value);
    var timeTaken = System.currentTimeMillis() - startTime;

    LOGGER.info("Time taken (ms): " + timeTaken);
    return result;
  }

  private long safeCall(int value) {
    var retries = 0;
    var result = (long) FAILURE;

    for (int i = 0; i < RETRIES; i++) {
      if (retries >= RETRIES) {
        return FAILURE;
      }

      if ((result = checkLatency(value)) == FAILURE) {
        LOGGER.info("Failed to reach remote: (" + (i + 1) + ")");
        retries++;
        try {
          sleep(DELAY_MS);
        } catch (InterruptedException e) {
          LOGGER.error("Thread sleep state interrupted", e);
        }
      } else {
        break;
      }
    }
    return result;
  }
}
```

Un cliente tiene un servicio ambassador local usado para interactuar con el servicio remoto:

```java
@Slf4j
public class Client {
  private final ServiceAmbassador serviceAmbassador = new ServiceAmbassador();

  long useService(int value) {
    var result = serviceAmbassador.doRemoteFunction(value);
    LOGGER.info("Service result: " + result);
    return result;
  }
}
```

A continuación dos clientes usando el servicio.

```java
public class App {
  public static void main(String[] args) {
    var host1 = new Client();
    var host2 = new Client();
    host1.useService(12);
    host2.useService(73);
  }
}
```

Esta es la salida que obtendremos tras ejecutar el ejemplo:

```java
Time taken (ms): 111
Service result: 120
Time taken (ms): 931
Failed to reach remote: (1)
Time taken (ms): 665
Failed to reach remote: (2)
Time taken (ms): 538
Failed to reach remote: (3)
Service result: -1
```

## Diagrama de clase

![alt text](./etc/ambassador.urm.png "Ambassador class diagram")

## Aplicaciones

Ambassador es aplicable cuando trabajamos con un servicio remoto heredado que no puede ser modificado o que sería extremamente
difícil de modificar. Las características de conectividad pueden implementarse en el cliente sin necesidad de realizar cambios en el servicio
remoto.

* Ambassador proporciona una interface local para un servicio remoto.
* Ambassador proporciona registros, interrupción de circuitos, reintentos y seguridad en el cliente.

## Casos de uso típicos

* Control de acceso a otro objeto
* Implementación de registros o logs
* Implementación de interrupciones de circuito
* Delegar tareas de servicios remotos
* Facilitar la conexión a la red

## Usos conocidos

* [Pasarela API Kubernetes-native para microservicios](https://github.com/datawire/ambassador)

## Patrones relacionados

* [Proxy](https://java-design-patterns.com/patterns/proxy/)

## Créditos

* [Ambassador Pattern (Documentación de Microsoft en inglés)](https://docs.microsoft.com/en-us/azure/architecture/patterns/ambassador)
* [Designing Distributed Systems: Patterns and Paradigms for Scalable, Reliable Services](https://www.amazon.com/s?k=designing+distributed+systems&sprefix=designing+distri%2Caps%2C156&linkCode=ll2&tag=javadesignpat-20&linkId=a12581e625462f9038557b01794e5341&language=en_US&ref_=as_li_ss_tl)

## Notas del traductor
(*) La versión original en inglés de la documentación de Microsoft hace referencia al término resiliencia y
en su traducción al español lo traduce como resistencia, aunque enlaza al apartado patrones de confiabilidad. Véase:
* [Versión de la Documentación para el Patrón Ambassador de Microsoft en español.](https://learn.microsoft.com/es-es/azure/architecture/patterns/ambassador)
