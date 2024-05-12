---
title: Aggregator Microservices
category: Architectural
language: fr
tag:
- Cloud distributed
- Decoupling
- Microservices
---

## Intention

L'utilisateur fait un appel unique au service d'agrégation, et ce dernier appelle ensuite chaque microservice approprié.

## Explication

Exemple concret

> Notre marché en ligne a besoin d'informations sur les produits et leur stock actuel. Elle fait appel à un service 
> d'agrégation qui, à son tour, appelle le microservice d'information sur les produits et le microservice d'inventaire
> des produits et renvoie les informations combinées.

En clair

> Aggregator Microservice collecte des éléments de données provenant de divers microservices et renvoie un agrégat pour traitement.. 

Stack Overflow dit

> Aggregator Microservice invoque plusieurs services pour réaliser la fonctionnalité requise par l'application.

**Exemple de programme**

Commençons par le modèle de données. Voici notre `Product`.

```java
public class Product {
  private String title;
  private int productInventories;
  // getters and setters ->
  ...
}
```

Ensuite, nous pouvons présenter notre microservice `Aggregator`. Il contient les clients `ProductInformationClient` et
`ProductInventoryClient` pour appeler les microservices respectifs.

```java
@RestController
public class Aggregator {

  @Resource
  private ProductInformationClient informationClient;

  @Resource
  private ProductInventoryClient inventoryClient;

  @RequestMapping(path = "/product", method = RequestMethod.GET)
  public Product getProduct() {

    var product = new Product();
    var productTitle = informationClient.getProductTitle();
    var productInventory = inventoryClient.getProductInventories();

    //Fallback to error message
    product.setTitle(requireNonNullElse(productTitle, "Error: Fetching Product Title Failed"));

    //Fallback to default error inventory
    product.setProductInventories(requireNonNullElse(productInventory, -1));

    return product;
  }
}
```

Voici l'essentiel de la mise en œuvre du microservice d'information. Le microservice dInventory est similaire, il renvoie simplement l'inventaire.

```java
@RestController
public class InformationController {
  @RequestMapping(value = "/information", method = RequestMethod.GET)
  public String getProductTitle() {
    return "The Product Title.";
  }
}
```

L'appel à notre API REST `Aggregator` renvoie les informations sur le produit.

```bash
curl http://localhost:50004/product
{"title":"The Product Title.","productInventories":5}
```

## Class diagram

![alt text](../../../aggregator-microservices/aggregator-service/etc/aggregator-service.png "Aggregator Microservice")

## Application

Utilisez le modèle de microservices agrégateur lorsque vous avez besoin d'une API unifiée pour différents microservices, quel que soit l'appareil client.

## Crédits

* [Microservice Design Patterns](http://web.archive.org/web/20190705163602/http://blog.arungupta.me/microservice-design-patterns/)
* [Microservices Patterns: With examples in Java](https://www.amazon.com/gp/product/1617294543/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1617294543&linkId=8b4e570267bc5fb8b8189917b461dc60)
* [Architectural Patterns: Uncover essential patterns in the most indispensable realm of enterprise architecture](https://www.amazon.com/gp/product/B077T7V8RC/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=B077T7V8RC&linkId=c34d204bfe1b277914b420189f09c1a4)
