---
title: Abstract Document
category: Structural
language: it
tag: 
 - Extensibility
---

## Intento

Il design pattern "Abstract Document" è un design pattern strutturale che mira a fornire un modo coerente per gestire strutture dati gerarchiche e ad albero, definendo un'interfaccia comune per vari tipi di documenti.

## Spiegazione

Il pattern Abstract Document consente di gestire proprietà aggiuntive e non statiche. Questo pattern utilizza il concetto di trait per consentire la sicurezza dei tipi e separare le proprietà delle diverse classi in un insieme di interfacce.

Esempio del mondo reale

>  Prendiamo ad esempio un'auto composta da diverse parti. Tuttavia, non sappiamo se l'auto ha tutte le parti o solo alcune di esse. Le nostre auto sono dinamiche ed estremamente flessibili.

In parole semplici

> Il pattern Abstract Document permette di associare proprietà agli oggetti senza che gli oggetti ne siano a conoscenza.

Wikipedia dice

> Un design pattern strutturale orientato agli oggetti per organizzare gli oggetti in archivi chiave-valore con
> tipizzazione debole ed esporre i dati utilizzando viste tipizzate. Lo scopo del pattern è ottenere un alto grado di
> flessibilità tra i componenti in un linguaggio fortemente tipizzato in cui nuove proprietà possono essere aggiunte
> all'albero degli oggetti al volo, senza perdere il supporto della sicurezza dei tipi. Il pattern fa uso di trait per
> separare diverse proprietà di una classe in diverse interfacce. Il termine "documento" è ispirato dai database
> orientati ai documenti. _(Testo tradotto dalla voce Abstract Document Pattern da Wikipedia in lingua inglese)._

**Esempio di codice**

Per prima cosa definiamo le classi di base `Document` e `AbstractDocument`. Essenzialmente, queste classi fanno sì che l'oggetto contenga una mappa di proprietà e una qualunque quantità di oggetti figlio.

```java
public interface Document {

  Void put(String key, Object value);

  Object get(String key);

  <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor);
}

public abstract class AbstractDocument implements Document {

  private final Map<String, Object> properties;

  protected AbstractDocument(Map<String, Object> properties) {
    Objects.requireNonNull(properties, "properties map is required");
    this.properties = properties;
  }

  @Override
  public Void put(String key, Object value) {
    properties.put(key, value);
    return null;
  }

  @Override
  public Object get(String key) {
    return properties.get(key);
  }

  @Override
  public <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor) {
    return Stream.ofNullable(get(key))
        .filter(Objects::nonNull)
        .map(el -> (List<Map<String, Object>>) el)
        .findAny()
        .stream()
        .flatMap(Collection::stream)
        .map(constructor);
  }
  ...
}
```
Successivamente definiamo un enum `Property` e un insieme di interfacce per tipo, prezzo, modello e parti. Questo ci permette di creare interfacce dall'aspetto statico per la nostra classe `Car`.

```java
public enum Property {

  PARTS, TYPE, PRICE, MODEL
}

public interface HasType extends Document {

  default Optional<String> getType() {
    return Optional.ofNullable((String) get(Property.TYPE.toString()));
  }
}

public interface HasPrice extends Document {

  default Optional<Number> getPrice() {
    return Optional.ofNullable((Number) get(Property.PRICE.toString()));
  }
}
public interface HasModel extends Document {

  default Optional<String> getModel() {
    return Optional.ofNullable((String) get(Property.MODEL.toString()));
  }
}

public interface HasParts extends Document {

  default Stream<Part> getParts() {
    return children(Property.PARTS.toString(), Part::new);
  }
}
```

Ora siamo pronti per introdurre la classe `Car`.

```java
public class Car extends AbstractDocument implements HasModel, HasPrice, HasParts {

  public Car(Map<String, Object> properties) {
    super(properties);
  }
}
```

E infine, costruiamo e usiamo la classe `Car` in un esempio completo.

```java
    LOGGER.info("Constructing parts and car");

    var wheelProperties = Map.of(
        Property.TYPE.toString(), "wheel",
        Property.MODEL.toString(), "15C",
        Property.PRICE.toString(), 100L);

    var doorProperties = Map.of(
        Property.TYPE.toString(), "door",
        Property.MODEL.toString(), "Lambo",
        Property.PRICE.toString(), 300L);

    var carProperties = Map.of(
        Property.MODEL.toString(), "300SL",
        Property.PRICE.toString(), 10000L,
        Property.PARTS.toString(), List.of(wheelProperties, doorProperties));

    var car = new Car(carProperties);

    LOGGER.info("Here is our car:");
    LOGGER.info("-> model: {}", car.getModel().orElseThrow());
    LOGGER.info("-> price: {}", car.getPrice().orElseThrow());
    LOGGER.info("-> parts: ");
    car.getParts().forEach(p -> LOGGER.info("\t{}/{}/{}",
        p.getType().orElse(null),
        p.getModel().orElse(null),
        p.getPrice().orElse(null))
    );

    // Constructing parts and car
    // Here is our car:
    // model: 300SL
    // price: 10000
    // parts: 
    // wheel/15C/100
    // door/Lambo/300
```

## Diagramma delle classi

![alt text](../../../abstract-document/etc/abstract-document.png "Abstract Document Traits and Domain")

## Applicabilità

Questo pattern è particolarmente utile in scenari in cui si hanno diversi tipi di documenti che condividono alcune proprietà o comportamenti comuni, ma hanno anche proprietà o comportamenti unici specifici per i rispettivi tipi. Ecco alcuni scenari in cui il pattern Abstract Document può essere applicabile:

* Sistemi di Gestione dei Contenuti (CMS): In un CMS, potresti avere vari tipi di contenuti, come articoli, immagini, video, ecc. Ogni tipo di contenuto potrebbe condividere attributi comuni come la data di creazione, l'autore e i tag, ma potrebbe anche avere attributi specifici come le dimensioni dell'immagine per le immagini o la durata del video per i video.

* File System: Se stai progettando un file system in cui è necessario gestire diversi tipi di file, come documenti, immagini, file audio e directory, il pattern Abstract Document può aiutare a fornire un modo coerente per accedere agli attributi come la dimensione del file, la data di creazione, ecc., consentendo allo stesso tempo attributi specifici come la risoluzione dell'immagine o la durata dell'audio.

* Sistemi di E-commerce: Una piattaforma di e-commerce potrebbe avere diversi tipi di prodotti, come prodotti fisici, download digitali e abbonamenti. Ogni tipo potrebbe condividere attributi comuni come il nome, il prezzo e la descrizione, ma avere attributi unici come il peso di spedizione per i prodotti fisici o il link di download per i prodotti digitali.

* Sistemi di Gestione delle Cartelle Cliniche: Nel settore sanitario, le cartelle cliniche dei pazienti potrebbero includere vari tipi di dati come dati demografici, storia clinica, risultati dei test e prescrizioni. Il pattern Abstract Document può aiutare a gestire attributi condivisi come l'ID del paziente e la data di nascita, consentendo allo stesso tempo di gestire attributi specializzati come i risultati dei test o le prescrizioni.

* Gestione delle Configurazioni: Quando si tratta di impostazioni di configurazione per applicazioni software, possono esserci diversi tipi di elementi di configurazione, ciascuno con il proprio insieme di attributi. Il pattern Abstract Document può essere utilizzato per gestire questi elementi di configurazione, garantendo un modo coerente per accedere e manipolare i loro attributi.

* Piattaforme Educative: I sistemi educativi potrebbero avere vari tipi di materiali didattici come contenuti basati su testo, video, quiz e compiti. Attributi comuni come il titolo, l'autore e la data di pubblicazione possono essere condivisi, mentre attributi unici come la durata del video o le scadenze degli esercizi possono essere specifici per ciascun tipo.

* Strumenti di Gestione dei Progetti: Nelle applicazioni di gestione dei progetti, potresti avere diversi tipi di attività come attività specifiche, fasi e problemi. Il pattern Abstract Document potrebbe essere utilizzato per gestire attributi generali come il nome dell'attività specifica e l'assegnatario, consentendo allo stesso tempo attributi specifici come la data della fase o la priorità del problema.

L'idea chiave alla base del design pattern Abstract Document è fornire un modo flessibile ed estensibile per gestire diversi tipi di documenti o entità con attributi comuni e distinti. Definendo un'interfaccia comune e implementandola in vari tipi di documenti, è possibile ottenere un approccio più organizzato e coerente alla gestione di strutture dati complesse.

## Collegamenti esterni

* [Wikipedia: Abstract Document Pattern](https://en.wikipedia.org/wiki/Abstract_Document_Pattern)
* [Martin Fowler: Dealing with properties](http://martinfowler.com/apsupp/properties.pdf)
* [Pattern-Oriented Software Architecture Volume 4: A Pattern Language for Distributed Computing (v. 4)](https://www.amazon.com/gp/product/0470059028/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0470059028&linkId=e3aacaea7017258acf184f9f3283b492)