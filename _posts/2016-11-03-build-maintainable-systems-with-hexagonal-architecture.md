---
layout: post
title: Build Maintainable Systems With Hexagonal Architecture
author: ilu
---

![Hexagonal Architecture]({{ site.baseurl }}/assets/hexagonal-architecture.png)

## The fallacies of layered architecture

This blog post is about implementing Alistair Cockburn's [Hexagonal Architecture](http://alistair.cockburn.us/Hexagonal+architecture). To have something familiar to start with let's first talk about Layered Architecture. It is a well known architectural pattern that organizes application into layers each having their specific purpose. The database layer takes care of data transactions, the business layer is responsible for business logic and the presentation layer deals with the user input. The Layered Architecture implements so called separation of concerns principle which leads to more maintainable applications. Changes to one area in the software do not affect the other areas.

![Layers]({{ site.baseurl }}/assets/layers.png)

This way of building applications can be considered simple and effective. But it also has several drawbacks. When you see an application implemented with Layered Architecture, where is the application core? Is it the database? Maybe it's the business logic with some little things scattered over to the presentation layer. This is the typical problem with layers. There is no application core, there are just the layers and the core logic is scattered here and there. When the business logic starts to leak over to the presentation layer, the application can no longer be tested without the user interface.

## Core, ports and adapters

Hexagonal Architecture tackles this issue by building the application around the core. The main objective is to create fully testable systems that can be driven equally by users, programs and batch scripts in isolation of database.

The core alone is not very useful. Something has to drive this application, call the business logic methods. It may be a HTTP request, automatic test or integration API. These interfaces that drive the application we call the primary ports and the modules that use them are primary adapters.

Also, the core has its dependencies. For example, there may be a data storage module that the core calls upon to retrieve and update data. The interfaces of these modules that are driven by the core are called the secondary ports of the application.

The secondary ports may have one or more implementations. For example there may be a mock database for testing and a real database for running the application. The secondary port implementations are called secondary adapters. Here comes the alias name Ports and Adapters for Hexagonal Architecture. Other architectural patterns describing the same concept are Uncle Bob's [Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html) and Jeffrey Palermo's [Onion Architecture](http://jeffreypalermo.com/blog/the-onion-architecture-part-1/).

The name of the pattern comes from the following hexagonal drawing.

![Ports and adapters]({{ site.baseurl }}/assets/ports_and_adapters.png)

The diagram shows how the domain is in the middle surrounded by ports on sides of the hexagon. The actual amount of ports does not have to be exactly six, it can be less or it can be more depending on the application needs. On the outer hexagon layer reside the primary and secondary adapters.

Naked Objects design pattern is considered an implementation of Hexagonal Architecture. Naked Objects is utilized by [Apache Isis](https://isis.apache.org/) framework. User defines the domain objects and the framework automatically generates user interface and REST API around it.

## Lottery system

Next we will demonstrate Hexagonal Architecture by building a lottery system. The lottery system will provide two primary ports: One for the users to submit lottery tickets and another for system administrators to perform the draw.

The secondary ports consist of lottery ticket database, banking for wire transfers and event log for handling and storing lottery events. The resulting hexagon of the system can be seen in the following diagram.

![Lottery system]({{ site.baseurl }}/assets/lottery.png)

## Start from the core concepts

We start the implementation from the system core. First we need to identify the core concepts of the lottery system. Probably the most important one is the lottery ticket. In lottery ticket you are supposed to pick the numbers and provide your contact details. This leads us to write the following classes.

<script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/hexagonal/src/main/java/com/iluwatar/hexagonal/domain/LotteryTicket.java?slice=24:44"></script>
<script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/hexagonal/src/main/java/com/iluwatar/hexagonal/domain/LotteryNumbers.java?slice=32:144"></script>

`LotteryTicket` contains `LotteryNumbers` and `PlayerDetails`. `LotteryNumbers` contains means to hold given numbers or generate random numbers and test the numbers for equality with another `LotteryNumbers` instance. [PlayerDetails](https://github.com/iluwatar/java-design-patterns/blob/master/hexagonal/src/main/java/com/iluwatar/hexagonal/domain/PlayerDetails.java) is a simple value object containing player's email address, bank account number and phone number.

## The core business logic

Now that we have the nouns presenting our core concepts we need to implement the core business logic that defines how the system works. In `LotteryAdministration` and `LotteryService` classes we write the methods that are needed by the lottery players and system administrators.

<script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/hexagonal/src/main/java/com/iluwatar/hexagonal/domain/LotteryAdministration.java?slice=31:"></script>

For administrators `LotteryAdministration` has `resetLottery()` method for starting a new lottery round. At this stage the players submit their lottery tickets into the database and when the time is due the administration calls `performLottery()` to draw the winning numbers and check each of the tickets for winnings.

<script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/hexagonal/src/main/java/com/iluwatar/hexagonal/domain/LotteryService.java?slice=31:"></script>

The lottery players use `submitTicket()` to submit tickets for lottery round. After the draw has been performed `checkTicketForPrize()` tells the players whether they have won.

`LotteryAdministration` and `LotteryService` have dependencies to lottery ticket database, banking and event log ports. We use [Guice](https://github.com/google/guice) dependency injection framework to provide the correct implementation classes for each purpose. The core logic is tested in [LotteryTest](https://github.com/iluwatar/java-design-patterns/blob/master/hexagonal/src/test/java/com/iluwatar/hexagonal/domain/LotteryTest.java).

## Primary adapter for the players

Now that the core implementation is ready we need to define the primary adapter for the players. We introduce `ConsoleLottery` class to provide command line interface that allows players to interact with the lottery system.

<script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/hexagonal/src/main/java/com/iluwatar/hexagonal/service/ConsoleLottery.java?slice=41:"></script>

It has commands to view and transfer bank account funds, submit and check lottery tickets.

## Primary adapter for the administrators

We also need to define the lottery administrator facing adapter. This is another command line interface named `ConsoleAdministration`.

<script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/hexagonal/src/main/java/com/iluwatar/hexagonal/administration/ConsoleAdministration.java?slice=35:"></script>

The interface's commands allow us to view submitted tickets, perform the lottery draw and reset the lottery ticket database.

## Secondary port for banking

Next we implement the secondary ports and adapters. The first one is the banking support that enables us to manipulate bank account funds. To explain the concept, the player can write his bank account number on the lottery ticket and in case the ticket wins the lottery system automatically wire transfers the funds.

<script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/hexagonal/src/main/java/com/iluwatar/hexagonal/banking/WireTransfers.java?slice=24:"></script>

The banking port has two adapters for different purposes. The first one `InMemoryBank` is a simple `HashMap` based implementation for testing. The lottery service's bank account is statically initialized to contain enough funds to pay the prizes in case some of the lottery tickets win.

The other adapter `MongoBank` is based on Mongo and is intended for production use. Running either one of the command line interfaces use this adapter.

## Secondary port for event log

Another secondary port is the lottery event log. Events are sent as the players submit their lottery tickets and when the draw is performed.

<script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/hexagonal/src/main/java/com/iluwatar/hexagonal/eventlog/LotteryEventLog.java?slice=26:"></script>

We have two adapters for this port: The first one `StdOutEventLog` is for testing and simply sends the events to standard output. The second `MongoEventLog` is more sophisticated, has persistent storage and is based on Mongo.

## Secondary port for database

The last secondary port is the database. It contains methods for storing and retrieving lottery tickets.

<script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/hexagonal/src/main/java/com/iluwatar/hexagonal/database/LotteryTicketRepository.java?slice=30:"></script>

The port has two adapters. The `LotteryTicketInMemoryRepository` is a mock database holding its contents in memory only and is meant for testing. The `MongoTicketRepository` is used for production runs and provides persistent storage over application restarts.

## Lottery application

With all the pieces in place we create a command line application to drive the lottery system. The test application begins the lottery round using the administration methods and starts collecting lottery tickets from the players. Once all the lottery tickets have been submitted the lottery number draw is performed and all the submitted tickets are checked for wins.

<script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/hexagonal/src/main/java/com/iluwatar/hexagonal/App.java?slice=62:"></script>

Running the test application produces the following output:

```
Lottery ticket for monica@google.com was submitted. Bank account 265-748 was charged for 3 credits.
Lottery ticket for lisa@google.com was submitted. Bank account 024-653 was charged for 3 credits.
Lottery ticket for harriet@google.com was submitted. Bank account 842-404 was charged for 3 credits.
Lottery ticket for ian@google.com was submitted. Bank account 663-765 was charged for 3 credits.
Lottery ticket for warren@google.com was submitted. Bank account 225-946 was charged for 3 credits.
Lottery ticket for bruno@google.com was submitted. Bank account 023-638 was charged for 3 credits.
Lottery ticket for johnie@google.com was submitted. Bank account 983-322 was charged for 3 credits.
Lottery ticket for andy@google.com was submitted. Bank account 934-734 was charged for 3 credits.
Lottery ticket for ted@google.com was submitted. Bank account 735-964 was charged for 3 credits.
Lottery ticket for lolita@google.com was submitted. Bank account 425-907 was charged for 3 credits.
Lottery ticket for warren@google.com was submitted. Bank account 225-946 was charged for 3 credits.
Lottery ticket for monica@google.com was submitted. Bank account 265-748 was charged for 3 credits.
Lottery ticket for bobbie@google.com was submitted. Bank account 946-384 was charged for 3 credits.
Lottery ticket for lolita@google.com was submitted. Bank account 425-907 was charged for 3 credits.
Lottery ticket for kevin@google.com could not be submitted because the credit transfer of 3 credits failed.
Lottery ticket for steve@google.com was submitted. Bank account 833-836 was charged for 3 credits.
Lottery ticket for bruce@google.com was submitted. Bank account 284-936 was charged for 3 credits.
Lottery ticket for peter@google.com was submitted. Bank account 335-886 was charged for 3 credits.
Lottery ticket for tyron@google.com was submitted. Bank account 310-992 was charged for 3 credits.
Lottery ticket for mary@google.com was submitted. Bank account 234-987 was charged for 3 credits.
Lottery ticket for lolita@google.com was checked and unfortunately did not win this time.
Lottery ticket for mary@google.com was checked and unfortunately did not win this time.
Lottery ticket for bruce@google.com was checked and unfortunately did not win this time.
Lottery ticket for warren@google.com was checked and unfortunately did not win this time.
Lottery ticket for lolita@google.com was checked and unfortunately did not win this time.
Lottery ticket for peter@google.com was checked and unfortunately did not win this time.
Lottery ticket for harriet@google.com was checked and unfortunately did not win this time.
Lottery ticket for bobbie@google.com was checked and unfortunately did not win this time.
Lottery ticket for andy@google.com was checked and unfortunately did not win this time.
Lottery ticket for tyron@google.com was checked and unfortunately did not win this time.
Lottery ticket for lisa@google.com was checked and unfortunately did not win this time.
Lottery ticket for ian@google.com was checked and unfortunately did not win this time.
Lottery ticket for monica@google.com was checked and unfortunately did not win this time.
Lottery ticket for ted@google.com was checked and unfortunately did not win this time.
Lottery ticket for warren@google.com was checked and unfortunately did not win this time.
Lottery ticket for monica@google.com was checked and unfortunately did not win this time.
Lottery ticket for steve@google.com was checked and unfortunately did not win this time.
Lottery ticket for bruno@google.com was checked and unfortunately did not win this time.
Lottery ticket for johnie@google.com was checked and unfortunately did not win this time.
```

## Final words

Applications implemented with Hexagonal Architecture are a joy to maintain and work with. Implementation details such as frameworks, user interfaces and databases are pushed out of the core and the application can work without them. We can clearly point in the center of the hexagon and say that this is our application and it uses these technologies to implement the submodule interfaces. Restricting communication to happen only through the ports forces the application to produce testable and maintainable code.

The full demo application of Hexagonal Architecture is available in [Java Design Patterns](https://github.com/iluwatar/java-design-patterns) Github repository.
