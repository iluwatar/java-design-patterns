---
title: API Gateway
category: Architectural
language: fr
tag:
  - Cloud distributed
  - Decoupling
  - Microservices
---

## Intention

Regrouper les appels aux microservices en un seul endroit, la passerelle API. 
L'utilisateur fait un seul appel à la passerelle API, qui appelle ensuite chaque microservice concerné.

## Explication

Avec les pattrons microservices, un client peut avoir besoin de données provenant de plusieurs microservices différents.
Si le client appelait directement chaque microservice, cela pourrait contribuer à allonger les temps de chargement, 
puisque le client devrait faire une requete réseau pour chaque microservice appelé. En outre, le fait que le client appelle
chaque microservice directement le lie à ce microservice - si les implémentations internes des microservices changent
(par exemple, si deux microservices sont combinés) ou si l'emplacement (hôte et port) d'un microservice change, 
alors chaque client qui utilise ces microservices doit être informé de l'existence de ce microservice.

L'objectif du pattron API Gateway est d'atténuer certains de ces problèmes. Dans le pattron API Gateway une entité supplémentaire
(la passerelle API) est placée entre le client et les microservices. Le rôle de la passerelle API est d'agréger les appels aux microservices. Plutôt que le client
appelle chaque microservice individuellement, le client appelle la passerelle API une seule fois.
La passerelle API appelle alors chacun des microservices dont le client a besoin.

Exemple concret

> Nous mettons en œuvre des microservices et un pattron API Gateway pour un site de commerce en ligne.
> Dans ce système la passerelle API fait des appels aux microservices Image et Prix.

En clair

> Pour un système mis en œuvre à l'aide d'une architecture de microservices,
> API Gateway est le point d'entrée unique qui regroupe les appels aux différents microservices.

Wikipedia dit

> La passerelle API est un serveur qui agit comme un front-end API, reçoit les requêtes API, applique des politiques de
> limitation et de sécurité, transmet les requêtes au service back-end et renvoie la réponse au demandeur. 
> Une passerelle comprend souvent un moteur de transformation pour orchestrer et modifier les demandes et les réponses à la volée.
> Une passerelle peut également fournir des fonctionnalités telles que la collecte de données analytiques et la mise en cache. 
> La passerelle peut fournir des fonctionnalités pour prendre en charge l'authentification, l'autorisation, la sécurité, 
> l'audit et la conformité réglementaire.

**Exemple de programme**

Cette mise en œuvre montre à quoi pourrait ressembler le pattron de conception API Gateway pour un site de commerce électronique.
La passerelle `ApiGateway` fait des appels aux microservices Image et Price en utilisant respectivement `ImageClientImpl` et `PriceClientImpl` respectivement.
Les clients qui consultent le site sur un ordinateur de bureau peuvent voir à la fois les informations sur le prix et l'image d'un produit.
Les clients qui consultent le site sur un ordinateur de bureau peuvent voir à la fois des informations sur le prix et l'image d'un produit,
donc la passerelle appelle les deux microservices et regroupe les données dans le modèle `DesktopProduct`.
Cependant, les utilisateurs mobiles ne voient que les informations sur le prix ; Ils ne voient pas l'image du produit.
Pour les utilisateurs mobiles, la passerelle `ApiGateway` ne récupère que les informations de prix, qu'elle utilise pour remplir le modèle `MobileProduct`.

Voici l'implémentation du microservice Image.

```java
public interface ImageClient {
  String getImagePath();
}

public class ImageClientImpl implements ImageClient {
  @Override
  public String getImagePath() {
    var httpClient = HttpClient.newHttpClient();
    var httpGet = HttpRequest.newBuilder()
        .GET()
        .uri(URI.create("http://localhost:50005/image-path"))
        .build();

    try {
      var httpResponse = httpClient.send(httpGet, BodyHandlers.ofString());
      return httpResponse.body();
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }

    return null;
  }
}
```

Voici l'implémentation du microservice Price.

```java
public interface PriceClient {
  String getPrice();
}

public class PriceClientImpl implements PriceClient {

  @Override
  public String getPrice() {
    var httpClient = HttpClient.newHttpClient();
    var httpGet = HttpRequest.newBuilder()
        .GET()
        .uri(URI.create("http://localhost:50006/price"))
        .build();

    try {
      var httpResponse = httpClient.send(httpGet, BodyHandlers.ofString());
      return httpResponse.body();
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }

    return null;
  }
}
```

Nous voyons ici comment API Gateway fait correspondre les demandes aux microservices.

```java
public class ApiGateway {

  @Resource
  private ImageClient imageClient;

  @Resource
  private PriceClient priceClient;

  @RequestMapping(path = "/desktop", method = RequestMethod.GET)
  public DesktopProduct getProductDesktop() {
    var desktopProduct = new DesktopProduct();
    desktopProduct.setImagePath(imageClient.getImagePath());
    desktopProduct.setPrice(priceClient.getPrice());
    return desktopProduct;
  }

  @RequestMapping(path = "/mobile", method = RequestMethod.GET)
  public MobileProduct getProductMobile() {
    var mobileProduct = new MobileProduct();
    mobileProduct.setPrice(priceClient.getPrice());
    return mobileProduct;
  }
}
```

## Diagramme de clqsses
![alt text](../../../api-gateway/etc/api-gateway.png "API Gateway")

## Application

Utilisez le pattron de passerelle API lorsque

* Vous utilisez une architecture de microservices et avez besoin d'un point d'agrégation unique pour vos appels de microservices.

## Tutoriels

* [Exploring the New Spring Cloud Gateway](https://www.baeldung.com/spring-cloud-gateway)
* [Spring Cloud - Gateway](https://www.tutorialspoint.com/spring_cloud/spring_cloud_gateway.htm)
* [Getting Started With Spring Cloud Gateway](https://dzone.com/articles/getting-started-with-spring-cloud-gateway)

## Crédits

* [microservices.io - API Gateway](http://microservices.io/patterns/apigateway.html)
* [NGINX - Building Microservices: Using an API Gateway](https://www.nginx.com/blog/building-microservices-using-an-api-gateway/)
* [Microservices Patterns: With examples in Java](https://www.amazon.com/gp/product/1617294543/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1617294543&linkId=ac7b6a57f866ac006a309d9086e8cfbd)
* [Building Microservices: Designing Fine-Grained Systems](https://www.amazon.com/gp/product/1491950358/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1491950358&linkId=4c95ca9831e05e3f0dadb08841d77bf1)
