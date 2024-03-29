---
title: Ambassador
category: Structural
language: fr
tag:
  - Decoupling
  - Cloud distributed
---

## Intention

Fournir une instance de service d'assistance à un client et décharger une fonctionnalité commune d'une ressource partagée.

## Explication

Exemple concret

> Un service distant a de nombreux clients qui accèdent à une fonction qu'il fournit. Le service est une application
> ancienne qu'il est impossible de mettre à jour. Le grand nombre de requêtes des utilisateurs entraîne des problèmes de
> connectivité. De nouvelles règles relatives à la fréquence des requêtes doivent être mises en œuvre, ainsi que des 
> contrôles de latence et une journalisation côté client.

En clair

>Avec le modèle Ambassador, nous pouvons mettre en œuvre des interrogations moins fréquentes de la part des clients,
> ainsi que des contrôles de latence et une journalisation.

La documentation de Microsoft indique que

> Un service ambassadeur peut être considéré comme un mandataire hors processus qui se trouve au même endroit que le client.
> Ce pattron peut être utile pour décharger les tâches courantes de connectivité client telles que la surveillance, 
> la journalisation, le routage, la sécurité (comme TLS) et les modèles de résilience d'une manière indépendante du langage.
> Il est souvent utilisé avec des applications anciennes ou d'autres applications difficiles à modifier, afin d'étendre 
> leurs capacités de mise en réseau. Il peut également permettre à une équipe spécialisée de mettre en œuvre ces fonctionnalités.

**Exemple de programme**

En gardant en mémoire l'introduction ci-dessus, nous reproduirons cette fonctionnalité dans ce exemple. Nous avons une interface
implémentée par un service distant aussi bien que service ambassadeur
We have an interface implemented 
by the remote service as well as the ambassador service :

```java
interface RemoteServiceInterface {
    long doRemoteFunction(int value) throws Exception;
}
```

Un sercive distant représenté comme un singleton.

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

Un service ambassadeur ajoute des fonctionnalités supplémentaires à l'instar de la journalisation et des contrôles de latence

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

Un client dispose d'un service ambassadeur local utilisé pour interagir avec le service distant :

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

Voici deux clients utilisant le service.

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

Ci-déssous la sortie du programme :

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

## Class diagram

![alt text](../../../ambassador/etc/ambassador.urm.png "Ambassador class diagram")

## Application

* Le pattron ambassador s'applique lorsque l'on travaille avec un service distant existant qui ne peut pas être modifié ou
qui serait extrêmement difficile à modifier. Les fonctions de connectivité peuvent être mises en œuvre sur le client sans
qu'il soit nécessaire d'apporter des modifications au service distant.
* Ambassador fournit une interface locale pour un service distant.
* Ambassador assure la journalisation, la coupure de circuit, les tentatives et la sécurité sur le client.

## Cas d'utilisation typique

* Contrôler l'accès à un autre objet
* Mise en œuvre de la journalisation
* Mettre en œuvre d'un coupe-circuit
* Décharger les tâches de service à distance
* Faciliter la connexion au réseau

## Utilisations connues

* [Kubernetes-native API gateway for microservices](https://github.com/datawire/ambassador)

## Modèles connexes

* [Proxy](https://java-design-patterns.com/patterns/proxy/)

## Crédits

* [Ambassador pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/ambassador)
* [Designing Distributed Systems: Patterns and Paradigms for Scalable, Reliable Services](https://www.amazon.com/s?k=designing+distributed+systems&sprefix=designing+distri%2Caps%2C156&linkCode=ll2&tag=javadesignpat-20&linkId=a12581e625462f9038557b01794e5341&language=en_US&ref_=as_li_ss_tl)
