---
title: Adapter
category: Structural
language: it
tag:
 - Gang of Four
---

## Anche conosciuto come
Wrapper

## Intento
Convertire l'interfaccia di una classe in un'altra interfaccia attesa dal cliente. Il pattern Adapter consente a classi di collaborare con altre classi con cui altrimenti non potrebbero farlo a causa di problemi di compatibilità.

## Spiegazione

Esempio del mondo reale

> Immagina di avere delle immagini su una scheda di memoria e desideri trasferirle sul tuo computer. Per effettuare il trasferimento, hai bisogno di un tipo di adattatore compatibile con le porte del tuo computer, in modo da poter inserire la tua scheda. In questo caso, il lettore di schede è un adattatore (adapter).
> Un altro esempio potrebbe essere l'ampiamente noto adattatore di corrente; una spina con tre pin non può essere collegata a una presa con due fori, è necessario un adattatore per renderla compatibile con la presa di corrente.
> Un altro esempio sarebbe un traduttore simultaneo che traduce le parole pronunciate da una persona a un'altra.

In parole semplici

> Il pattern Adapter permette di incapsulare un oggetto in un adattatore per renderlo compatibile con una classe con cui sarebbe altrimenti incompatibile.

Wikipedia dice

> In ingegneria del software, il pattern Adapter è un design pattern del software che consente all'interfaccia di una classe esistente di essere utilizzata come un'altra interfaccia. Spesso viene utilizzato per far sì che classi esistenti possano collaborare con altre senza dover modificare il loro codice sorgente. _(Testo tradotto dalla voce Adapter Pattern da Wikipedia in lingua inglese)._

**Esempio di codice**

Immagina un capitano che sa utilizzare solo barche a remi e non sa navigare affatto.

Innanzitutto, abbiamo le interfacce `RowingBoat` e `FishingBoat`

```java
public interface RowingBoat {
  void row();
}

@Slf4j
public class FishingBoat {
  public void sail() {
    LOGGER.info("The fishing boat is sailing");
  }
}
```

E il capitano si aspetta che ci sia un'implementazione dell'interfaccia `RowingBoat` per potersi muovere.

```java
public class Captain {

  private final RowingBoat rowingBoat;
  // default constructor and setter for rowingBoat
  public Captain(RowingBoat rowingBoat) {
    this.rowingBoat = rowingBoat;
  }

  public void row() {
    rowingBoat.row();
  }
}
```

Ora immaginiamo che i pirati stiano arrivando e il nostro capitano debba scappare, ma è disponibile solo una barca da pesca. Dobbiamo creare un adattatore che consenta al capitano di navigare con la barca da pesca con le sue abilità di saper utilizzare le barche a remi.
```java
@Slf4j
public class FishingBoatAdapter implements RowingBoat {

  private final FishingBoat boat;

  public FishingBoatAdapter() {
    boat = new FishingBoat();
  }

  @Override
  public void row() {
    boat.sail();
  }
}
```

E ora il `Captain` può usare la `FishingBoat` per sfuggire ai pirati.

```java
var captain = new Captain(new FishingBoatAdapter());
captain.row();
```

## Diagramma delle classi
![alt text](../../../adapter/etc/adapter.urm.png "Adapter class diagram")

## Applicabilità
Utilizza il pattern Adapter quando

* Vuoi utilizzare una classe esistente, ma la sua interfaccia non corrisponde a quella di cui hai bisogno
* Desideri creare una classe riutilizzabile che collabori con classi non correlate o non previste, ovvero classi che non necessariamente hanno interfacce compatibili.
* Hai bisogno di utilizzare diverse sottoclassi esistenti, ma sarebbe impraticabile adattare la loro interfaccia mediante la creazione di sottoclassi per ognuna di esse. Un adapter può adattare l'interfaccia della sua classe padre.
* Nella maggior parte delle applicazioni che utilizzano librerie di terze parti, si utilizzano adapter come strato intermedio tra l'applicazione e la libreria di terze parti per disaccoppiare l'applicazione dalla libreria stessa. Se è necessario utilizzare un'altra libreria, è sufficiente creare un nuovo adapter per la nuova libreria senza dover modificare il codice dell'applicazione.

## Tutorial

* [Dzone](https://dzone.com/articles/adapter-design-pattern-in-java)
* [Refactoring Guru](https://refactoring.guru/design-patterns/adapter/java/example)
* [Baeldung](https://www.baeldung.com/java-adapter-pattern)
* [GeeksforGeeks](https://www.geeksforgeeks.org/adapter-pattern/)


## Conseguenze
Gli adapter di classe e oggetti comportano scelte diverse. Un adapter di classe

*   Effettua l'adattamento rimanendo legato a una classe adattata specifica. Di conseguenza, un adapter di classe non funzionerà quando desideriamo adattare una classe e le sue sottoclassi.
*   Permette all'Adapater di modificare il comportamento della classe adattata perché l'adpater è una sottoclasse della classe adattata.  
*   Utilizza un solo oggetto e non richiede l'uso di puntatori aggiuntivi per fare riferimento alla classe adattata.

Un adapter di oggetto	

*   Permette a un singolo Adapter di lavorare con diverse classi adattate, ovvero con la classe adattata e tutte le sue sottoclassi (se ne ha). L'Adapter può anche aggiungere funzionalità a tutte le classi adattate contemporaneamente.
*   Rende più complicata la modifica del comportamento della classe adattata. Sarebbe necessario creare una sottoclasse della classe da adattare e far sì che l'Adapter faccia riferimento alla sottoclasse anziché alla classe da adattare.

## Esempi del mondo reale

* [java.util.Arrays#asList()](http://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#asList%28T...%29)
* [java.util.Collections#list()](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#list-java.util.Enumeration-)
* [java.util.Collections#enumeration()](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#enumeration-java.util.Collection-)
* [javax.xml.bind.annotation.adapters.XMLAdapter](http://docs.oracle.com/javase/8/docs/api/javax/xml/bind/annotation/adapters/XmlAdapter.html#marshal-BoundType-)


## Collegamenti esterni

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
