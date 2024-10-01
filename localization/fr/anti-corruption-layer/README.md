---
title: "Patron de conception Couche Anti-Corruption en Java : Assurer l'intégrité du système face aux systèmes hérités"
shortTitle: Couche Anti-Corruption
description: "Découvrez comment le patron de conception de la Couche Anti-Corruption (Anti-Corruption Layer Pattern) aide à découpler les sous-systèmes, à prévenir la corruption des données et à faciliter une intégration fluide dans les applications Java."
category: Integration
language: fr
tag:
  - Architecture
  - Decoupling
  - Integration
  - Isolation
  - Layered architecture
  - Migration
  - Modernization
  - Refactoring
  - Wrapping
---

## Aussi connu sous le nom de

* ACL
* Couche d'interface
* Couche de traduction

## Intention du modèle de conception de la couche anti-corruption

La couche anti-corruption (ACL) est un patron de conception essentiel dans le développement Java, en particulier pour l'intégration des systèmes et le maintien de l'intégrité des données. Implémentez une couche de façade ou d'adaptateur entre différents sous-systèmes qui ne partagent pas la même sémantique. Elle traduit entre différents formats de données et systèmes, garantissant que l'intégration entre les systèmes ne conduit pas à une corruption de la logique métier ou à une perte d'intégrité des données.

## Explication détaillée du patron de conception Couche Anti-Corruption avec des exemples concrets

Exemple concret

> Cet exemple montre comment la couche anti-corruption garantit une intégration fluide entre les systèmes hérités et les plateformes modernes, essentiel pour maintenir l'intégrité de la logique métier lors de la migration du système.
> 
> Imaginez une grande entreprise de vente au détail qui passe d'un ancien logiciel de gestion des stocks à une nouvelle plateforme moderne. Le système hérité est utilisé depuis des décennies et contient des règles métier complexes et des formats de données incompatibles avec le nouveau système. Au lieu de connecter directement le nouveau système à l'ancien, l'entreprise implémente une couche anti-corruption (ACL).
> 
> L'ACL agit comme un médiateur, traduisant et adaptant les données entre les deux systèmes. Lorsque le nouveau système demande des données d'inventaire, l'ACL traduit la requête dans un format compréhensible par le système hérité, récupère les données, puis les traduit à nouveau dans un format adapté au nouveau système. Cette approche garantit que le nouveau système reste indépendant des complexités du système hérité, évitant ainsi la corruption des données et de la logique métier tout en facilitant une transition en douceur.

En termes simples

> Le patron de conception Couche Anti-Corruption protège un système des complexités et des changements des systèmes externes en fournissant une couche de traduction intermédiaire.

[La documentation de Microsoft](https://learn.microsoft.com/fr-fr/azure/architecture/patterns/anti-corruption-layer) dit

> Implémentez une couche de façade ou d’adaptateur entre différents sous-systèmes qui ne partagent pas la même sémantique. Cette couche traduit les requêtes qu’un sous-système envoie à l’autre sous-système. Utilisez ce modèle pour vous assurer que la conception d’une application n’est pas limitée par les dépendances aux sous-systèmes externes. Ce modèle a d’abord été décrit par Eric Evans dans Domain-Driven Design (Conception orientée domaine).

## Exemple programmatique du patron de conception de Couche Anti-Corruption en Java

Le modèle ACL en Java fournit une couche intermédiaire qui traduit les formats de données, garantissant que l'intégration entre différents systèmes ne conduit pas à une corruption des données.

Voici 2 systèmes de commande de magasin : `Legacy` et `Modern`.

Ces systèmes ont des modèles de domaine différents et doivent fonctionner simultanément. Comme ils travaillent de manière indépendante, les commandes peuvent provenir soit du système `Legacy`, soit du système `Modern`. Par conséquent, le système qui reçoit la commande `orderLegacy` doit vérifier si cette commande est valide et non présente dans l'autre système. Ensuite, il peut placer la commande `orderLegacy` dans son propre système.

Mais pour cela, le système doit connaître le modèle de domaine de l'autre système et pour éviter cela, la couche anti-corruption (ACL) est introduite. L'ACL est une couche qui traduit le modèle de domaine du système `Legacy` en celui du système `Modern` et inversement. De plus, elle masque toutes les autres opérations avec l'autre système, découplant les systèmes.

Modèle de domaine du système `Legacy` :

```java
public class LegacyOrder {
    private String id;
    private String customer;
    private String item;
    private String qty;
    private String price;
}
```

Modèle de domaine du système `Modern` :

```java
public class ModernOrder {
    private String id;
    private Customer customer;

    private Shipment shipment;

    private String extra;
}

public class Customer {
    private String address;
}

public class Shipment {
    private String item;
    private String qty;
    private String price;
}
```

Couche anti-corruption :

```java
public class AntiCorruptionLayer {

    @Autowired
    private ModernShop modernShop;

    @Autowired
    private LegacyShop legacyShop;

    public Optional<LegacyOrder> findOrderInModernSystem(String id) {
        return modernShop.findOrder(id).map(o -> /* map to legacyOrder*/);
    }

    public Optional<ModernOrder> findOrderInLegacySystem(String id) {
        return legacyShop.findOrder(id).map(o -> /* map to modernOrder*/);
    }

}
```

La connexion entre les systèmes. Chaque fois que le système `Legacy` ou `Modern` doit communiquer avec l'autre, l'ACL doit être utilisée pour éviter de corrompre le modèle de domaine actuel. L'exemple ci-dessous montre comment le système `Legacy` passe une commande avec une validation du système `Modern`.

```java
public class LegacyShop {
    @Autowired
    private AntiCorruptionLayer acl;

    public void placeOrder(LegacyOrder legacyOrder) throws ShopException {

        String id = legacyOrder.getId();

        Optional<LegacyOrder> orderInModernSystem = acl.findOrderInModernSystem(id);

        if (orderInModernSystem.isPresent()) {
            // la commande est déjà dans le système moderne
        } else {
            // passer la commande dans le système actuel
        }
    }
}
```
## Quand utiliser le patron de conception Couche Anti-Corruption en Java

Utilisez ce modèle lorsque :

* Une migration est prévue en plusieurs étapes, mais l'intégration entre les nouveaux et les anciens systèmes doit être maintenue
* Deux ou plusieurs sous-systèmes ont une sémantique différentes, mais doivent tout de même communiquer
* Lors de l'intégration avec des systèmes hérités ou des systèmes externes où une intégration directe pourrait polluer le modèle de domaine du nouveau système
* Dans des scénarios où différents sous-systèmes au sein d'un système plus large utilisent différents formats ou structures de données
* Lorsqu'il est nécessaire de garantir un découplage lâche entre différents sous-systèmes ou services externes pour faciliter la maintenance et la scalabilité

## Tutoriels sur le patron de conception Couche Anti-Corruption en Java

* [Couche Anti-Corruption](https://learn.microsoft.com/fr-fr/azure/architecture/patterns/anti-corruption-layer)
* [Patron de conception Couche Anti-Corruption](https://docs.aws.amazon.com/prescriptive-guidance/latest/cloud-design-patterns/acl.html)

## Applications concrètes du modèle de couche anti-corruption en Java

* Architectures microservices où les services individuels doivent communiquer sans être étroitement couplés aux schémas de données des autres
* Intégration des systèmes d'entreprise, notamment lors de l'intégration des systèmes modernes avec des systèmes hérités
* Dans des contextes délimités au sein de la Domain-Driven Design (DDD) pour maintenir l'intégrité d'un modèle de domaine lors de l'interaction avec des systèmes ou sous-systèmes externes

## Avantages et compromis du modèle de couche anti-corruption

Avantages :

* Protège l'intégrité du modèle de domaine en fournissant une frontière claire
* Favorise le découplage lâche entre les systèmes, rendant le système plus résilient aux changements des systèmes externes
* Facilite un code plus propre et plus facile à maintenir en isolant le code d'intégration de la logique métier

Compromis :

* Introduit une complexité supplémentaire et un potentiel de surcharge de performance en raison du processus de traduction
* Nécessite un effort supplémentaire dans la conception et la mise en œuvre pour s'assurer que la couche est efficace sans devenir un goulet d'étranglement
* Peut entraîner une duplication des modèles s'il n'est pas géré avec soin

## Patron de conception Java associés

* [Adaptateur (Adapter)](https://java-design-patterns.com/patterns/adapter/): La couche anti-corruption peut être implémentée en utilisant le modèle Adaptateur pour traduire entre différents formats ou structures de données
* [Façade](https://java-design-patterns.com/patterns/facade/): La couche anti-corruption peut être vue comme une forme spécialisée du modèle Façade utilisée pour isoler différents sous-systèmes
* [Passerelle (Gateway)](https://java-design-patterns.com/patterns/gateway/): La couche anti-corruption peut être utilisée comme une passerelle vers des systèmes externes pour fournir une interface unifiée

## Références et Crédits

* [Domain-Driven Design : Résoudre la complexité au cœur du logiciel](https://amzn.to/3vptcJz)
* [Implémentation du Domain-Driven Design](https://amzn.to/3ISOSRA)
* [Patterns of Enterprise Application Architecture (Modèles d'architecture des applications d'entreprise)](https://amzn.to/3WfKBPR)
