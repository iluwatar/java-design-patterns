---
layout: pattern
title: Ambassador
folder: ambassador
permalink: /patterns/ambassador/
categories: Structural
tags:
 - Decoupling
---

## Intent
Provide a helper service instance on a client and offload common functionality away from a shared resource.

## Explanation
Real world example

> A remote service has many clients accessing a function it provides. The service is a legacy application and is impossible to update. Large numbers of requests from users are causing connectivity issues. New rules for request frequency should be implemented along with latency checks and client-side logging.

In plain words

> Using the ambassador pattern, we can implement less-frequent polling from clients along with latency checks and logging.

Microsoft documentation states

> An ambassador service can be thought of as an out-of-process proxy that is co-located with the client.
  This pattern can be useful for offloading common client connectivity tasks such as monitoring, logging, routing, security (such as TLS), and resiliency patterns in a language agnostic way. It is often used with legacy applications, or other applications that are difficult to modify, in order to extend their networking capabilities. It can also enable a specialized team to implement those features.

**Programmatic Example**

With the above example in mind we will imitate the functionality in a simple manner. We have an interface implemented by the remote service as well as the ambassador service:

```java
interface RemoteServiceInterface {

    long doRemoteFunction(int value) throws Exception;
}
```

A remote services represented as a singleton.

```java
public class RemoteService implements RemoteServiceInterface {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteService.class);
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

A service ambassador adding additional features such as logging, latency checks

```java
public class ServiceAmbassador implements RemoteServiceInterface {

  private static final Logger LOGGER = LoggerFactory.getLogger(ServiceAmbassador.class);
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

A client has a local service ambassador used to interact with the remote service:

```java
public class Client {

  private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);
  private final ServiceAmbassador serviceAmbassador = new ServiceAmbassador();

  long useService(int value) {
    var result = serviceAmbassador.doRemoteFunction(value);
    LOGGER.info("Service result: " + result);
    return result;
  }
}
```

And here are two clients using the service.

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

## Class diagram
![alt text](./etc/ambassador.urm.png "Ambassador class diagram")

## Applicability
Ambassador is applicable when working with a legacy remote service that cannot
be modified or would be extremely difficult to modify. Connectivity features can
be implemented on the client avoiding the need for changes on the remote service.

* Ambassador provides a local interface for a remote service.
* Ambassador provides logging, circuit breaking, retries and security on the client.

## Typical Use Case

* Control access to another object
* Implement logging
* Implement circuit breaking
* Offload remote service tasks
* Facilitate network connection

## Real world examples

* [Kubernetes-native API gateway for microservices](https://github.com/datawire/ambassador)

## Credits

* [Ambassador pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/ambassador)
* [Designing Distributed Systems: Patterns and Paradigms for Scalable, Reliable Services](https://books.google.co.uk/books?id=6BJNDwAAQBAJ&pg=PT35&lpg=PT35&dq=ambassador+pattern+in+real+world&source=bl&ots=d2e7GhYdHi&sig=Lfl_MDnCgn6lUcjzOg4GXrN13bQ&hl=en&sa=X&ved=0ahUKEwjk9L_18rrbAhVpKcAKHX_KA7EQ6AEIWTAI#v=onepage&q=ambassador%20pattern%20in%20real%20world&f=false)
