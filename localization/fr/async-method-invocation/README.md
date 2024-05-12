---
title: Async Method Invocation
category: Concurrency
language: fr
tag:
 - Reactive
---

## Intention

Le pattron de conception Asynchronous method invocation est un pattron dans lequel le thread appelé n'est pas bloqué en 
attendant la fin de l'exécution des tâches.
Ce pattron permet le traitement parallèle de plusieurs tâches indépendantes et récupère les résultats via les callback ou patiente jusqu'à la fin.

## Explication

Exemple concret

> Le lancement de fusées spatiales est une activité passionnante. Le commandant de la mission donne
> l'ordre de lancement et, après un temps indéterminé, la fusée réussit son lancement ou échoue lamentablement.

En clair

> Asynchronous method invocation démarre l'exécution de la tâche et retourne directement avant la fin de l'exécution de la tâche.
> Les résultats de l'exécution des tâches sont retournés après.

Wikipedia dit

> Dans la programmation multithread, asynchronous method invocation (AMI), également connue sous le nom de asynchronous method calls 
> ou le pattron asynchronous, est un pattron de conception dans lequel le site d'appel n'est pas bloqué en attendant que le code appelé se termine.
> Au lieu de cela, le thread appelant est informé de l'arrivée de la réponse. L'interrogation pour une réponse est une option non souhaitée.

**Exemple de programme**

Dans cet exemple, nous lançons des fusées spatiales et déployons des rovers lunaires.

L'application démontre le pattron async method invocation. Les éléments clés de ce modèle sont `AsyncResult`
qui est un conteneur intermédiaire pour une valeur évaluée de manière asynchrone, `AsyncCallback` qui peut être fourni
pour être exécuté à la fin de la tâche et `AsyncExecutor` qui gère l'exécution des tâches asynchrones. qui gère l'exécution des tâches asynchrones.

```java
public interface AsyncResult<T> {
  boolean isCompleted();
  T getValue() throws ExecutionException;
  void await() throws InterruptedException;
}
```

```java
public interface AsyncCallback<T> {
  void onComplete(T value, Optional<Exception> ex);
}
```

```java
public interface AsyncExecutor {
  <T> AsyncResult<T> startProcess(Callable<T> task);
  <T> AsyncResult<T> startProcess(Callable<T> task, AsyncCallback<T> callback);
  <T> T endProcess(AsyncResult<T> asyncResult) throws ExecutionException, InterruptedException;
}
```

`ThreadAsyncExecutor` est une implémentation de `AsyncExecutor`. Certains de ses éléments clés sont décrits ci-dessous.

```java
public class ThreadAsyncExecutor implements AsyncExecutor {

  @Override
  public <T> AsyncResult<T> startProcess(Callable<T> task) {
    return startProcess(task, null);
  }

  @Override
  public <T> AsyncResult<T> startProcess(Callable<T> task, AsyncCallback<T> callback) {
    var result = new CompletableResult<>(callback);
    new Thread(
            () -> {
              try {
                result.setValue(task.call());
              } catch (Exception ex) {
                result.setException(ex);
              }
            },
            "executor-" + idx.incrementAndGet())
        .start();
    return result;
  }

  @Override
  public <T> T endProcess(AsyncResult<T> asyncResult)
      throws ExecutionException, InterruptedException {
    if (!asyncResult.isCompleted()) {
      asyncResult.await();
    }
    return asyncResult.getValue();
  }
}
```

Nous sommes ensuite prêts à lancer quelques fusées pour voir comment tout fonctionne ensemble.

```java
public static void main(String[] args) throws Exception {
  // construct a new executor that will run async tasks
  var executor = new ThreadAsyncExecutor();

  // start few async tasks with varying processing times, two last with callback handlers
  final var asyncResult1 = executor.startProcess(lazyval(10, 500));
  final var asyncResult2 = executor.startProcess(lazyval("test", 300));
  final var asyncResult3 = executor.startProcess(lazyval(50L, 700));
  final var asyncResult4 = executor.startProcess(lazyval(20, 400), callback("Deploying lunar rover"));
  final var asyncResult5 =
      executor.startProcess(lazyval("callback", 600), callback("Deploying lunar rover"));

  // emulate processing in the current thread while async tasks are running in their own threads
  Thread.sleep(350); // Oh boy, we are working hard here
  log("Mission command is sipping coffee");

  // wait for completion of the tasks
  final var result1 = executor.endProcess(asyncResult1);
  final var result2 = executor.endProcess(asyncResult2);
  final var result3 = executor.endProcess(asyncResult3);
  asyncResult4.await();
  asyncResult5.await();

  // log the results of the tasks, callbacks log immediately when complete
  log("Space rocket <" + result1 + "> launch complete");
  log("Space rocket <" + result2 + "> launch complete");
  log("Space rocket <" + result3 + "> launch complete");
}
```

Voici la sortie de la console du programme.

```java
21:47:08.227 [executor-2] INFO com.iluwatar.async.method.invocation.App - Space rocket <test> launched successfully
21:47:08.269 [main] INFO com.iluwatar.async.method.invocation.App - Mission command is sipping coffee
21:47:08.318 [executor-4] INFO com.iluwatar.async.method.invocation.App - Space rocket <20> launched successfully
21:47:08.335 [executor-4] INFO com.iluwatar.async.method.invocation.App - Deploying lunar rover <20>
21:47:08.414 [executor-1] INFO com.iluwatar.async.method.invocation.App - Space rocket <10> launched successfully
21:47:08.519 [executor-5] INFO com.iluwatar.async.method.invocation.App - Space rocket <callback> launched successfully
21:47:08.519 [executor-5] INFO com.iluwatar.async.method.invocation.App - Deploying lunar rover <callback>
21:47:08.616 [executor-3] INFO com.iluwatar.async.method.invocation.App - Space rocket <50> launched successfully
21:47:08.617 [main] INFO com.iluwatar.async.method.invocation.App - Space rocket <10> launch complete
21:47:08.617 [main] INFO com.iluwatar.async.method.invocation.App - Space rocket <test> launch complete
21:47:08.618 [main] INFO com.iluwatar.async.method.invocation.App - Space rocket <50> launch complete
```

# Diagramme de classes

![alt text](../../../async-method-invocation/etc/async-method-invocation.png "Async Method Invocation")

## Application

Utilisez le pattron async method invocation lorsque

* Vous avez plusieurs tâches indépendantes qui peuvent être exécutées en parallèle
* Vous devez améliorer les performances d'un groupe de tâches séquentielles
* Vous disposez d'une capacité de traitement limitée ou de tâches à long terme et l'appelant ne doit pas attendre que les tâches soient exécutées.

## Application concrete

* [FutureTask](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/FutureTask.html)
* [CompletableFuture](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html)
* [ExecutorService](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html)
* [Task-based Asynchronous Pattern](https://msdn.microsoft.com/en-us/library/hh873175.aspx)
