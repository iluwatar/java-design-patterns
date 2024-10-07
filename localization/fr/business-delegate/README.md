---
title: "Business Delegate Pattern en Java: Simplifier l'intéraction avec les services métiers"
shortTitle: Business Delegate
description: "Apprenez à connaître le patron Business Delegate en Java. Ce patron ajoute une niveau d'abstraction en la couche de présentation et la couche métier, assurant un couplage faible et une intéraction simplifiée entre les sevices."
category: Structural
language: fr
tag:
  - Business
  - Decoupling
  - Delegation
  - Enterprise patterns
  - Layered architecture
---

## Aussi connu sous le nom de 

* Service Representative

## But du modèle de conception Business-Delegate

Le modèle Business Delegate est un modèle de conception structurel en Java qui ajoute un niveau d'abstraction en la couche de pésentation et la couche métier. Utiliser ce modèle garanti un couplage faible entre les couches et encapsule les informations sur la localisation, la connexion et les interactions des objets métiers qui composent l'application.

## Explication détaillée et exemples concrets

Exemple concret

> Dans une application d'entreprise utilisant Java EE, le modèle Business-Delegate permet de gérer les interactions entre les différents services commerciaux.
> 
> Imaginez un restaurant où le personnel de service sert d'intermédiaire entre les clients et la cuisine. Lorsqu'un client passe une commande, le serveur l'apporte à la cuisine, transmet toute demande spécifique et ramène ensuite le plat préparé au client. Le personnel de salle fait abstraction de la complexité des opérations de cuisine, ce qui permet aux chefs de se concentrer uniquement sur la préparation des plats, sans avoir à interagir directement avec les clients. Cette configuration permet au service à la clientèle (niveau de présentation) et à la cuisine (service commercial) de fonctionner de manière indépendante et efficace. Le personnel de service joue le rôle de délégué commercial, en gérant la communication et en garantissant des interactions harmonieuses entre les deux secteurs distincts.

En clair

> Le Business-Delegate ajoute une couche d'abstraction entre le niveau de présentation et le niveau commercial.

Définition Wikipedia : 

> Business Delegate est un modèle de conception Java EE. Ce modèle vise à réduire le couplage entre les services métier et le niveau de présentation connecté, et à masquer les détails de mise en œuvre des services (y compris la consultation et l'accessibilité de l'architecture EJB). Les délégués commerciaux agissent comme un adaptateur permettant d'invoquer des objets commerciaux à partir du niveau de présentation.

## Example de programme

Le code Java suivant montre comment mettre en œuvre le modèle de délégué commercial. Ce modèle est particulièrement utile dans les applications nécessitant un couplage lâche et une interaction efficace entre les services.

Une application pour téléphone portable promet de diffuser en continu n'importe quel film existant sur votre appareil. Elle capture la chaîne de recherche de l'utilisateur et la transmet au Business Delegate. Ce dernier sélectionne le service de streaming vidéo le plus approprié et lit la vidéo à partir de là.

Tout d'abord, nous disposons d'une abstraction pour les services de streaming vidéo et de quelques implémentations.

```java
public interface VideoStreamingService {
    void doProcessing();
}

@Slf4j
public class NetflixService implements VideoStreamingService {
    @Override
    public void doProcessing() {
        LOGGER.info("NetflixService is now processing");
    }
}

@Slf4j
public class YouTubeService implements VideoStreamingService {
    @Override
    public void doProcessing() {
        LOGGER.info("YouTubeService is now processing");
    }
}
```

Ensuite, nous avons un service de recherche qui décide quel service de streaming vidéo utiliser.

```java

@Setter
public class BusinessLookup {

    private NetflixService netflixService;
    private YouTubeService youTubeService;

    public VideoStreamingService getBusinessService(String movie) {
        if (movie.toLowerCase(Locale.ROOT).contains("die hard")) {
            return netflixService;
        } else {
            return youTubeService;
        }
    }
}
```

Le Business Delegate utilise un service de recherche pour acheminer les requêtes vers le bon service de streaming.

```java

@Setter
public class BusinessDelegate {

    private BusinessLookup lookupService;

    public void playbackMovie(String movie) {
        VideoStreamingService videoStreamingService = lookupService.getBusinessService(movie);
        videoStreamingService.doProcessing();
    }
}
```

Le client mobile se sert du Business Delegate pour appeler la couche métier.

```java
public class MobileClient {

    private final BusinessDelegate businessDelegate;

    public MobileClient(BusinessDelegate businessDelegate) {
        this.businessDelegate = businessDelegate;
    }

    public void playbackMovie(String movie) {
        businessDelegate.playbackMovie(movie);
    }
}
```

Finalement, l'exemple concret en action.

```java
public static void main(String[] args) {

    // prepare the objects
    var businessDelegate = new BusinessDelegate();
    var businessLookup = new BusinessLookup();
    businessLookup.setNetflixService(new NetflixService());
    businessLookup.setYouTubeService(new YouTubeService());
    businessDelegate.setLookupService(businessLookup);

    // create the client and use the business delegate
    var client = new MobileClient(businessDelegate);
    client.playbackMovie("Die Hard 2");
    client.playbackMovie("Maradona: The Greatest Ever");
}
```

Voici la sortie affichée dans la console.

```
21:15:33.790 [main] INFO com.iluwatar.business.delegate.NetflixService - NetflixService is now processing
21:15:33.794 [main] INFO com.iluwatar.business.delegate.YouTubeService - YouTubeService is now processing
```

## Diagramme de classe 

![Business Delegate](./etc/business-delegate.urm.png "Business Delegate")

## Quand utiliser ce modèle de conception 

Utilisez le modèle Business Delegate lorsque

* Vous avez besoin d'un couplage lâche entre les niveaux de présentation et d'activité ou vous avez besoin d'abstraire les recherches de services.
* Vous souhaitez organiser des appels à de multiples services commerciaux.
* Vous voulez encapsuler les recherches et les appels de services.
* Il est nécessaire d'abstraire et d'encapsuler la communication entre le niveau client et les services métier.

## Tutoriels 

* [Design Patterns - Business Delegate Pattern (TutorialsPoint)](https://www.tutorialspoint.com/design_pattern/business_delegate_pattern.htm)

## Applications concrêtes 

* Applications d'entreprise utilisant Java EE (Java Platform, Enterprise Edition)
* Applications nécessitant un accès à distance aux services de l'entreprised

## Avantages et inconvénients 

Avantages :

* Découplage des niveaux de présentation et d'activité : Permet au niveau client et aux services commerciaux d'évoluer indépendamment.
* Transparence de l'emplacement : Les clients ne sont pas affectés par les changements de localisation ou d'instanciation des services commerciaux.
* Réutilisation et évolutivité : Les objets Business Delegate peuvent être réutilisés par plusieurs clients, et le modèle prend en charge l'équilibrage de la charge et l'évolutivité.

Inconvénients :

* Complexity: Introduces additional layers and abstractions, which may increase complexity.
* Performance Overhead: The additional indirection may incur a slight performance penalty.

## Réferences et crédits

* [Core J2EE Patterns: Best Practices and Design Strategies](https://amzn.to/4cAbDap)
* [J2EE Design Patterns](https://amzn.to/4dpzgmx)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
