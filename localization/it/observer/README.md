---
title: Observer
category: Behavioral
language: it
tag:
 - Gang Of Four
 - Reactive
---

## Anche conosciuto come

Dependents, Publish-Subscribe

## Intento

Stabilire una dipendenza uno-a-molti tra gli oggetti in modo che quando un oggetto cambia stato, tutti i suoi dipendenti vengano avvisati e aggiornati automaticamente.

## Spiegazione

Esempio del mondo reale

> In una terra lontana vivono le razze degli hobbit e degli orchi. Entrambi trascorrono la maggior parte del tempo all'aperto, quindi
> seguono attentamente i cambiamenti del tempo. Si potrebbe dire che osservano costantemente le
> condizioni meteorologiche.

In parole semplici

> Registrarsi come osservatore per ricevere notifiche di cambiamenti di stato nell'oggetto.

Wikipedia dice

> Il pattern observer è un design pattern in cui un oggetto, chiamato soggetto,
> mantiene una lista dei suoi dipendenti, chiamati osservatori, e li avvisa automaticamente di eventuali cambiamenti di stato,
> di solito chiamando uno dei loro metodi. _(Testo tradotto dalla voce Observer Pattern da Wikipedia in lingua inglese)._

**Esempio di codice**

Iniziamo introducendo l'interfaccia `WeatherObserver` e le nostre razze, `Orcs` e `Hobbits`.

```java
public interface WeatherObserver {

  void update(WeatherType currentWeather);
}

@Slf4j
public class Orcs implements WeatherObserver {

  @Override
  public void update(WeatherType currentWeather) {
    LOGGER.info("The orcs are facing " + currentWeather.getDescription() + " weather now");
  }
}

@Slf4j
public class Hobbits implements WeatherObserver {

  @Override
  public void update(WeatherType currentWeather) {
    switch (currentWeather) {
      LOGGER.info("The hobbits are facing " + currentWeather.getDescription() + " weather now");
    }
  }
}
```

Poi c'è il `Weather` che cambia continuamente.

```java
@Slf4j
public class Weather {

  private WeatherType currentWeather;
  private final List<WeatherObserver> observers;

  public Weather() {
    observers = new ArrayList<>();
    currentWeather = WeatherType.SUNNY;
  }

  public void addObserver(WeatherObserver obs) {
    observers.add(obs);
  }

  public void removeObserver(WeatherObserver obs) {
    observers.remove(obs);
  }

  /**
   * Makes time pass for weather.
   */
  public void timePasses() {
    var enumValues = WeatherType.values();
    currentWeather = enumValues[(currentWeather.ordinal() + 1) % enumValues.length];
    LOGGER.info("The weather changed to {}.", currentWeather);
    notifyObservers();
  }

  private void notifyObservers() {
    for (var obs : observers) {
      obs.update(currentWeather);
    }
  }
}
```

Ecco l'esempio completo in azione.

```java
    var weather = new Weather();
    weather.addObserver(new Orcs());
    weather.addObserver(new Hobbits());
    weather.timePasses();
    weather.timePasses();
    weather.timePasses();
    weather.timePasses();
```

Output del programma:

```
The weather changed to rainy.
The orcs are facing rainy weather now
The hobbits are facing rainy weather now
The weather changed to windy.
The orcs are facing windy weather now
The hobbits are facing windy weather now
The weather changed to cold.
The orcs are facing cold weather now
The hobbits are facing cold weather now
The weather changed to sunny.
The orcs are facing sunny weather now
The hobbits are facing sunny weather now
```

## Diagramma delle classi

![alt text](../../../observer/etc/observer.png "Observer")

## Applicabilità

Usa il pattern Observer in una qualsiasi delle seguenti situazioni:

* Quando un'astrazione ha due aspetti, uno dipendente dall'altro. L'incapsulamento di questi aspetti in oggetti separati ti permette di variarli e riutilizzarli in modo indipendente.
* Quando una modifica a un oggetto richiede la modifica di altri oggetti, e non sai quanti oggetti devono essere modificati.
* Quando un oggetto dovrebbe essere in grado di avvisare altri oggetti senza fare presupposizioni su chi siano questi oggetti. In altre parole, non desideri che questi oggetti siano strettamente accoppiati.

## Usi noti

* [java.util.Observer](http://docs.oracle.com/javase/8/docs/api/java/util/Observer.html)
* [java.util.EventListener](http://docs.oracle.com/javase/8/docs/api/java/util/EventListener.html)
* [javax.servlet.http.HttpSessionBindingListener](http://docs.oracle.com/javaee/7/api/javax/servlet/http/HttpSessionBindingListener.html)
* [RxJava](https://github.com/ReactiveX/RxJava)

## Collegamenti esterni

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Java Generics and Collections](https://www.amazon.com/gp/product/0596527756/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596527756&linkCode=as2&tag=javadesignpat-20&linkId=246e5e2c26fe1c3ada6a70b15afcb195)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
