---
title: Abstract Factory
category: Creational
language: it
tag:
 - Gang of Four
---

## Anche conosciuto come

Kit

## Intento

Fornire un'interfaccia per la creazione di famiglie di oggetti correlati o dipendenti senza specificarne le classi concrete.

## Spiegazione

Esempio del mondo reale

> Per creare un regno, abbiamo bisogno di oggetti con un tema comune. Il regno elfico ha bisogno di un re elfico, di un castello elfico e di un esercito elfico, mentre il regno degli orchi ha bisogno di un re degli orchi, di un castello degli orchi e di un esercito degli orchi. Esiste una dipendenza tra gli oggetti all'interno del regno.

In parole semplici

> Una fabbrica di fabbriche; una fabbrica che raggruppa le singole fabbriche correlate o dipendenti senza specificare le loro classi concrete.

Wikipedia dice

> L'Abstract Factory fornisce un'interfaccia per creare famiglie di oggetti connessi o dipendenti tra loro, in modo che non ci sia necessità da parte dei client di specificare i nomi delle classi concrete all'interno del proprio codice.

**Esempio di codice**

Traducendo l'esempio del regno sopra. Prima di tutto, abbiamo alcune interfacce e implementazioni per gli oggetti all'interno del regno.

```java
public interface Castle {
  String getDescription();
}

public interface King {
  String getDescription();
}

public interface Army {
  String getDescription();
}

// Elven implementations ->
public class ElfCastle implements Castle {
  static final String DESCRIPTION = "This is the elven castle!";
  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
public class ElfKing implements King {
  static final String DESCRIPTION = "This is the elven king!";
  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
public class ElfArmy implements Army {
  static final String DESCRIPTION = "This is the elven Army!";
  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}

// Orcish implementations similarly -> ...

```

Successivamente, abbiamo l'astrazione e le implementazioni per la fabbrica del regno.

```java
public interface KingdomFactory {
  Castle createCastle();
  King createKing();
  Army createArmy();
}

public class ElfKingdomFactory implements KingdomFactory {

  @Override
  public Castle createCastle() {
    return new ElfCastle();
  }

  @Override
  public King createKing() {
    return new ElfKing();
  }

  @Override
  public Army createArmy() {
    return new ElfArmy();
  }
}

public class OrcKingdomFactory implements KingdomFactory {

  @Override
  public Castle createCastle() {
    return new OrcCastle();
  }

  @Override
  public King createKing() {
    return new OrcKing();
  }
  
  @Override
  public Army createArmy() {
    return new OrcArmy();
  }
}
```

Ora abbiamo la fabbrica astratta che ci consente di creare una famiglia di oggetti correlati, ad esempio la fabbrica del regno elfico crea il castello elfico, il re elfico e l'esercito elfico, ecc.

```java
var factory = new ElfKingdomFactory();
var castle = factory.createCastle();
var king = factory.createKing();
var army = factory.createArmy();

castle.getDescription();
king.getDescription();
army.getDescription();
```

Output del programma:

```java
This is the elven castle!
This is the elven king!
This is the elven Army!
```

Ora possiamo progettare una fabbrica per le nostre diverse fabbriche di regni. In questo esempio, abbiamo creato `FactoryMaker`, responsabile di restituire un'istanza di `ElfKingdomFactory` o `OrcKingdomFactory`.  
Il client può utilizzare `FactoryMaker` per creare la fabbrica concreta desiderata, che a sua volta produrrà diversi oggetti concreti (derivati da `Army`, `King`, `Castle`).  
In questo esempio, abbiamo anche utilizzato un enum per parametrizzare il tipo di fabbrica di regno richiesto dal client.

```java
public static class FactoryMaker {

    public enum KingdomType {
        ELF, ORC
    }

    public static KingdomFactory makeFactory(KingdomType type) {
        return switch (type) {
            case ELF -> new ElfKingdomFactory();
            case ORC -> new OrcKingdomFactory();
            default -> throw new IllegalArgumentException("KingdomType not supported.");
        };
    }
}

    public static void main(String[] args) {
        var app = new App();

        LOGGER.info("Elf Kingdom");
        app.createKingdom(FactoryMaker.makeFactory(KingdomType.ELF));
        LOGGER.info(app.getArmy().getDescription());
        LOGGER.info(app.getCastle().getDescription());
        LOGGER.info(app.getKing().getDescription());

        LOGGER.info("Orc Kingdom");
        app.createKingdom(FactoryMaker.makeFactory(KingdomType.ORC));
        --similar use of the orc factory
    }
```

## Diagramma delle classi

![alt text](../../../abstract-factory/etc/abstract-factory.urm.png "Abstract Factory class diagram")


## Applicabilità

Utilizza il pattern Abstract Factory quando

* Il sistema deve essere indipendente dal modo in cui i suoi prodotti vengono creati, composti e rappresentati.
* Il sistema deve essere configurato con una delle molteplici famiglie di prodotti.
* La famiglia di oggetti di prodotto correlati è progettata per essere utilizzata interamente, e hai bisogno di imporre questo presupposto.
* Vuoi fornire una libreria di classi di prodotto e vuoi esporre solo le loro interfacce, non le loro implementazioni.
* Il tempo di vita della dipendenza è concettualmente più breve di quella del consumer.
* Hai bisogno di un valore di runtime per costruire una particolare dipendenza.
* Vuoi decidere quale prodotto chiamare da una famiglia a runtime.
* Devi fornire uno o più parametri conosciuti solo a runtime prima di poter risolvere una dipendenza.
* Hai bisogno di coerenza tra i prodotti.
* Non vuoi modificare il codice esistente quando aggiungi nuovi prodotti o famiglie di prodotti al programma.

Esempi di casi d'uso	

* Selezionare la chiamata all'implementazione appropriata di FileSystemAcmeService o DatabaseAcmeService o NetworkAcmeService in fase di esecuzione. 
* La scrittura di casi di unit test diventa molto più semplice. 
* Strumenti UI per diversi sistemi operativi.

## Conseguenze

* La dependency injection in Java nasconde le dipendenze delle classi di servizio, il che può portare a errori a runtime che sarebbero stati rilevati in fase di compilazione. 
* Se da un lato il pattern è ottimo per la creazione di oggetti predefiniti, l'aggiunta di nuovi oggetti potrebbe essere complicato. 
* Il codice diventa più complesso di quanto dovrebbe essere, poiché vengono introdotte molte nuove interfacce e classi insieme al pattern.

## Tutorial

* [Abstract Factory Pattern Tutorial](https://www.journaldev.com/1418/abstract-factory-design-pattern-in-java) 

## Usi noti

* [javax.xml.parsers.DocumentBuilderFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/parsers/DocumentBuilderFactory.html)
* [javax.xml.transform.TransformerFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/transform/TransformerFactory.html#newInstance--)
* [javax.xml.xpath.XPathFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/xpath/XPathFactory.html#newInstance--)

## Pattern correlati

* [Factory Method](https://java-design-patterns.com/patterns/factory-method/)
* [Factory Kit](https://java-design-patterns.com/patterns/factory-kit/)

## Collegamenti esterni

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
