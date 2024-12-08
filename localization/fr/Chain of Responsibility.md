---
title: "Le Modèle Chaîne de Responsabilité en Java : Construire des Mécanismes Robustes de Gestion des Requêtes"
shortTitle: Chaîne de Responsabilité
description: "Apprenez le modèle de conception Chaîne de Responsabilité en Java avec des exemples concrets, des extraits de code et des diagrammes de classes. Améliorez vos compétences en codage grâce à nos explications détaillées."
category: Comportemental
language: fr
tag:
  - Découplage
  - Événementiel
  - Gang of Four
  - Messagerie
---

## Aussi connu sous le nom de

* Chaîne de Commande
* Chaîne d'Objets
* Chaîne de Responsabilité

## Intention du Modèle de Conception Chaîne de Responsabilité

Le modèle Chaîne de Responsabilité en Java est un modèle de conception comportemental qui découple l'expéditeur d'une requête de ses récepteurs en donnant à plusieurs objets la possibilité de traiter la requête. Les objets récepteurs sont enchaînés, et la requête est transmise le long de la chaîne jusqu'à ce qu'un objet la traite.

## Explication Détaillée du Modèle Chaîne de Responsabilité avec des Exemples Concrets

Exemple concret

> Un exemple concret du modèle Chaîne de Responsabilité en Java est un centre d'assistance technique. Lors de la mise en œuvre de ce modèle en Java, chaque niveau de support représente un gestionnaire dans la chaîne. Lorsqu'un client appelle pour un problème, l'appel est d'abord reçu par un représentant de support de premier niveau. Si le problème est simple, le représentant le gère directement. Si le problème est plus complexe, l'appel est transmis à un technicien de support de deuxième niveau. Ce processus continue, l'appel étant escaladé à travers plusieurs niveaux de support jusqu'à atteindre un spécialiste capable de résoudre le problème. Chaque niveau de support représente un gestionnaire dans la chaîne, et l'appel est transmis le long de celle-ci jusqu'à ce qu'il trouve un gestionnaire approprié, découplant ainsi la requête de son récepteur spécifique.

En termes simples

> Cela aide à construire une chaîne d'objets. Une requête entre par un bout et passe d'un objet à un autre jusqu'à ce qu'elle trouve un gestionnaire adapté.

Wikipedia dit

> En conception orientée objet, le modèle de conception Chaîne de Responsabilité est un modèle qui consiste en une source d'objets de commande et une série d'objets de traitement. Chaque objet de traitement contient une logique qui définit les types d'objets de commande qu'il peut traiter ; les autres sont transmis au prochain objet de traitement dans la chaîne.

## Exemple Programmable du Modèle Chaîne de Responsabilité

Dans cet exemple Java, le Roi Orc donne des ordres qui sont traités par une chaîne de commandement représentant le modèle Chaîne de Responsabilité. Apprenez à implémenter ce modèle en Java avec l'extrait de code suivant.

Le Roi Orc donne des ordres forts à son armée. Le plus proche pour réagir est le commandant, puis un officier, et enfin un soldat. Le commandant, l'officier et le soldat forment une chaîne de responsabilité.

D'abord, nous avons la classe `Request` :

```java
@Getter
public class Request {

    private final RequestType requestType;
    private final String requestDescription;
    private boolean handled;

    public Request(final RequestType requestType, final String requestDescription) {
        this.requestType = Objects.requireNonNull(requestType);
        this.requestDescription = Objects.requireNonNull(requestDescription);
    }

    public void markHandled() {
        this.handled = true;
    }

    @Override
    public String toString() {
        return getRequestDescription();
    }
}

public enum RequestType {
    DEFEND_CASTLE, TORTURE_PRISONER, COLLECT_TAX
}
```
Ensuite, nous montrons la hiérarchie des RequestHandler.
```java
public interface RequestHandler {

    boolean canHandleRequest(Request req);

    int getPriority();

    void handle(Request req);

    String name();
}

@Slf4j
public class OrcCommander implements RequestHandler {
    @Override
    public boolean canHandleRequest(Request req) {
        return req.getRequestType() == RequestType.DEFEND_CASTLE;
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public void handle(Request req) {
        req.markHandled();
        LOGGER.info("{} handling request \"{}\"", name(), req);
    }

    @Override
    public String name() {
        return "Commandant Orc";
    }
}

// OrcOfficer et OrcSoldier sont définis de manière similaire à OrcCommander ...
```
Ensuite, nous montrons la hiérarchie des RequestHandler.
```java
public interface RequestHandler {

    boolean canHandleRequest(Request req);

    int getPriority();

    void handle(Request req);

    String name();
}

@Slf4j
public class OrcCommander implements RequestHandler {
    @Override
    public boolean canHandleRequest(Request req) {
        return req.getRequestType() == RequestType.DEFEND_CASTLE;
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public void handle(Request req) {
        req.markHandled();
        LOGGER.info("{} handling request \"{}\"", name(), req);
    }

    @Override
    public String name() {
        return "Commandant Orc";
    }
}

// OrcOfficer et OrcSoldier sont définis de manière similaire à OrcCommander ...
```
Le OrcKing donne les ordres et forme la chaîne.
```java
public class OrcKing {

    private List<RequestHandler> handlers;

    public OrcKing() {
        buildChain();
    }

    private void buildChain() {
        handlers = Arrays.asList(new OrcCommander(), new OrcOfficer(), new OrcSoldier());
    }

    public void makeRequest(Request req) {
        handlers
                .stream()
                .sorted(Comparator.comparing(RequestHandler::getPriority))
                .filter(handler -> handler.canHandleRequest(req))
                .findFirst()
                .ifPresent(handler -> handler.handle(req));
    }
}
```
La chaîne de responsabilité en action.
```java
  public static void main(String[] args) {

    var king = new OrcKing();
    king.makeRequest(new Request(RequestType.DEFEND_CASTLE, "défendre le château"));
    king.makeRequest(new Request(RequestType.TORTURE_PRISONER, "torturer un prisonnier"));
    king.makeRequest(new Request(RequestType.COLLECT_TAX, "collecter des impôts"));
}
```
La sortie console :
```java
Commandant Orc traitant la requête "défendre le château"
Officier Orc traitant la requête "torturer un prisonnier"
Soldat Orc traitant la requête "collecter des impôts"
```
## Diagramme de Classe du Modèle Chaîne de Responsabilité

![Chaîne de Responsabilité](./etc/chain-of-responsibility.urm.png "Chain of Responsibility class diagram")

## Quand Utiliser le Modèle Chaîne de Responsabilité en Java

Utilisez le modèle Chaîne de Responsabilité lorsque :

* Plus d'un objet peut traiter une requête, et le gestionnaire n'est pas connu à l'avance. Le gestionnaire doit être déterminé automatiquement.
* Vous souhaitez émettre une requête à l'un des nombreux objets sans spécifier explicitement le récepteur.
* L'ensemble des objets pouvant traiter une requête doit être spécifié dynamiquement.


## Applications Réelles du Modèle Chaîne de Responsabilité en Java

* La propagation d'événements dans les frameworks GUI, où un événement peut être traité à plusieurs niveaux d'une hiérarchie de composants UI.
* Les frameworks middleware où une requête passe par une chaîne d'objets de traitement.
* Les frameworks de journalisation où les messages peuvent être passés à travers une série de loggers, chacun les traitant différemment.
* [java.util.logging.Logger#log()](http://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html#log%28java.util.logging.Level,%20java.lang.String%29)
* [Apache Commons Chain](https://commons.apache.org/proper/commons-chain/index.html)
* [javax.servlet.Filter#doFilter()](http://docs.oracle.com/javaee/7/api/javax/servlet/Filter.html#doFilter-javax.servlet.ServletRequest-javax.servlet.ServletResponse-javax.servlet.FilterChain-)

## Avantages et Inconvénients du Modèle Chaîne de Responsabilité

Avantages :

* Couplage réduit. L'expéditeur d'une requête n'a pas besoin de connaître le gestionnaire concret qui traitera la requête.
* Flexibilité accrue dans l'attribution des responsabilités aux objets. Vous pouvez ajouter ou modifier les responsabilités pour traiter une requête en changeant les membres et l'ordre de la chaîne.
* Permet de définir un gestionnaire par défaut si aucun gestionnaire concret ne peut traiter la requête.

Inconvénients :

* Il peut être difficile de déboguer et de comprendre le flux, en particulier si la chaîne est longue et complexe.
La requête pourrait rester non traitée si la chaîne ne comprend pas de gestionnaire universel.
Des problèmes de performance peuvent survenir en raison du passage par plusieurs gestionnaires avant de trouver le bon, ou de ne pas le trouver du tout.
## Related Java Design Patterns

* [Command](https://java-design-patterns.com/patterns/command/): peut être utilisé pour encapsuler une requête en tant qu'objet, qui pourrait être passé le long de la chaîne.
* [Composite](https://java-design-patterns.com/patterns/composite/): la Chaîne de Responsabilité est souvent appliquée conjointement avec le modèle Composite.
* [Decorator](https://java-design-patterns.com/patterns/decorator/): les décorateurs peuvent être enchaînés de manière similaire aux responsabilités dans le modèle Chaîne de Responsabilité.

## Références et Crédits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [Pattern-Oriented Software Architecture, Volume 1: A System of Patterns](https://amzn.to/3PAJUg5)
* [Refactoring to Patterns](https://amzn.to/3VOO4F5)
* [Pattern languages of program design 3](https://amzn.to/4a4NxTH)
