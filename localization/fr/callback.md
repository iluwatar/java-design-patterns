title: "Patron de Rappel (Callback) en Java : Maîtriser la Communication Asynchrone"
shortTitle: Callback
description: "Découvrez le Patron de Conception Callback en Java, incluant son objectif, ses scénarios d'utilisation, ses avantages, ses inconvénients, et des exemples concrets. Comprenez comment implémenter et utiliser efficacement les rappels dans vos applications Java."
category: Fonctionnel
language: fr
tag:
  - Asynchrone
  - Découplage
  - Idiome
  - Réactif
---

## Aussi connu sous le nom de

* Appel-Après
* Abonnement-Événement
* Écouteur

## Objectif du Patron de Conception Callback

Le Patron de Conception Callback en Java est un morceau de code exécutable passé comme argument à un autre code, qui est censé rappeler (exécuter) cet argument à un moment opportun.

## Explication détaillée du Patron Callback avec des exemples concrets

Exemple concret

> Une analogie dans le monde réel pour le patron de conception Callback peut être trouvée dans l'industrie de la restauration. Imaginez une situation où vous passez une commande dans un restaurant bondé. Au lieu d'attendre au comptoir que votre plat soit prêt, vous fournissez votre numéro de téléphone au caissier. Une fois que votre commande est prête, le personnel de cuisine vous appelle ou vous envoie un SMS pour vous informer que votre repas est prêt à être récupéré.
>
> Dans cette analogie, passer votre commande est analogue à l'initiation d'une tâche asynchrone. Fournir votre numéro de téléphone revient à transmettre une fonction de rappel. La préparation de votre commande par la cuisine représente le traitement asynchrone, et la notification que vous recevez correspond à l'exécution du rappel, vous permettant de récupérer votre repas sans avoir à attendre passivement. Cette séparation entre l'initiation et l'achèvement d'une tâche est l'essence même du patron de conception Callback.

En termes simples

> Un rappel est une méthode passée à un exécuteur qui sera appelée à un moment défini.

Wikipedia dit

> En programmation informatique, un rappel (callback), aussi connu comme une fonction "appel-après", est tout code exécutable qui est passé comme un argument à un autre code ; ce dernier est censé rappeler (exécuter) l'argument à un moment donné.

## Exemple programmatique du Patron Callback en Java

Nous devons être informés après que la tâche exécutée est terminée. Nous transmettons une méthode de rappel à l'exécuteur et attendons qu'il nous rappelle.

`Callback` est une interface simple avec une seule méthode.

```java
public interface Callback {

    void call();
}
Ensuite, nous définissons une classe Task qui exécutera le rappel après l'exécution de la tâche.
public abstract class Task {

    final void executeWith(Callback callback) {
        execute();
        Optional.ofNullable(callback).ifPresent(Callback::call);
    }

    public abstract void execute();
}

@Slf4j
public final class SimpleTask extends Task {

    @Override
    public void execute() {
        LOGGER.info("Effectuer une activité importante puis appeler la méthode de rappel.");
    }
}
Enfin, voici comment nous exécutons une tâche et recevons un rappel lorsqu'elle est terminée.
public static void main(final String[] args) {
    var task = new SimpleTask();
    task.executeWith(() -> LOGGER.info("C'est terminé maintenant."));
}
Sortie du programme :
17:12:11.680 [main] INFO com.iluwatar.callback.SimpleTask -- Effectuer une activité importante puis appeler la méthode de rappel.
17:12:11.682 [main] INFO com.iluwatar.callback.App -- C'est terminé maintenant.
  
## Quand utiliser le modèle Callback en Java

Utilisez le modèle Callback lorsque :

* Gestion d'événements asynchrones dans les applications GUI ou les systèmes orientés événements
* Mise en œuvre de mécanismes de notification où certains événements doivent déclencher des actions dans d'autres composants
* Découplage des modules ou composants qui doivent interagir sans avoir une dépendance directe

## Applications réelles du modèle Callback en Java

* Les frameworks GUI utilisent souvent des callbacks pour la gestion des événements, comme les interactions utilisateur (clics, pressions de touches)
* Node.js repose fortement sur les callbacks pour les opérations d'I/O non bloquantes
* Les frameworks traitant des opérations asynchrones, comme les Promises en JavaScript, utilisent des callbacks pour gérer la résolution ou le rejet des tâches asynchrones
* Le constructeur [CyclicBarrier](http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/CyclicBarrier.html#CyclicBarrier%28int,%20java.lang.Runnable%29) peut accepter un callback déclenché chaque fois qu'une barrière est atteinte.

## Avantages et compromis du modèle Callback

Avantages :

* Découple la logique d'exécution d'une opération de la logique de signalement ou de notification, améliorant la modularité et la réutilisabilité
* Facilite le traitement asynchrone, améliorant la réactivité et l'évolutivité des applications
* Permet un modèle de programmation réactive où les composants peuvent réagir aux événements au fur et à mesure qu'ils se produisent

Compromis :
  
* Callback hell ou pyramide de la mort : Les callbacks imbriqués en profondeur peuvent rendre le code difficile à lire et à maintenir
* L'inversion de contrôle peut entraîner un flux de code plus difficile à suivre, rendant le débogage plus complexe
* Problèmes potentiels de gestion des erreurs, en particulier dans les langages ou environnements où des exceptions sont utilisées, car les erreurs doivent être propagées à travers les callbacks

## Modèles de conception Java connexes

* [Command](https://java-design-patterns.com/patterns/command/): Les callbacks peuvent être implémentés comme des objets Command dans des scénarios nécessitant plus de flexibilité ou d'état dans l'opération de callback
* [Observer](https://java-design-patterns.com/patterns/observer/): Les callbacks peuvent être vus comme une forme plus dynamique et légère du modèle Observer, avec la possibilité de s'abonner et de se désabonner dynamiquement des fonctions de callback
* [Promise](https://java-design-patterns.com/patterns/promise/): Dans certains langages ou frameworks, les Promises ou Futures peuvent être utilisées pour gérer les opérations asynchrones plus proprement, souvent en utilisant des callbacks pour les cas de succès ou d'échec

## Références et crédits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Java Concurrency in Practice](https://amzn.to/4aRMruW)
