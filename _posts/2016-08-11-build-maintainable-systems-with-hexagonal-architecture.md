---
layout: post
title: Build Maintainable Systems With Hexagonal Architecture
author: ilu
---

![Nut]({{ site.url }}{{ site.baseurl }}/assets/nut-small.jpg)

## The fallacies of layered architecture

This blog post is about implementing Alistair Cockburn's [Hexagonal Architecture](http://alistair.cockburn.us/Hexagonal+architecture). To have something familiar to start with let's first talk about Layered Architecture. It is a well known architectural pattern that organizes application into layers each having their specific purpose. The database layer takes care of data transactions, the business layer is responsible for business logic and the presentation layer deals with the user input. The Layered Architecture implements so called separation of concerns principle which leads to more maintainable applications. Changes to one area in the software are not propagated to other areas.

![Layers]({{ site.url }}{{ site.baseurl }}/assets/layers.png)

This way of building applications can be considered simple and effective. But it also has several drawbacks. When you see an application implemented with Layered Architecture, where is the application core? Is it the database? Maybe it's the business logic with some little things scattered over to the presentation layer. This is the typical problem with layers. There is no application core, there are just the layers and the core logic is scattered here and there. When the business logic starts to leak over to the presentation layer, the application can no longer be tested without the user interface.

## Core, ports and adapters

Hexagonal Architecture tackles this issue by building the application around the core. The main objective is to create fully testable systems that can be driven equally by users, programs and batch scripts in isolation of database. 

However, the core alone may not be very useful. Something has to drive this application, call the business logic methods. It may be a HTTP request, automatic test or integration API. These interfaces we call the primary ports. Also, the core has its dependencies. For example, there may be a data storage module that the core calls upon to retrieve and update data. The interfaces of these modules that are driven by the core are called the secondary ports of the application.

Each of the ports may have one or more implementation. For example there may be a mock database for testing and a real database for running the application. The port implementations are called adapters. Thus the alias name Ports and Adapters for Hexagonal Architecture. Other architectural patterns describing the same concept are Uncle Bob's [Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html) and Jeffrey Palermo's [Onion Architecture](http://jeffreypalermo.com/blog/the-onion-architecture-part-1/).

The name of the pattern comes from the following hexagonal drawing.

![Hexagon]({{ site.url }}{{ site.baseurl }}/assets/hexagon.png)

The diagram shows how the domain is in the middle surrounded by ports on each side of hexagon. The actual amount of ports does not have to be exactly six, it can be less or it can be more depending on the application needs. 

Naked Objects design pattern is considered an implementation of Hexagonal Architecture. Naked Objects is utilized by [Apache Isis](https://isis.apache.org/) framework. User defines the domain objects and the framework automatically generates user interface and REST API around it.

## Lottery system

![Lottery ticket]({{ site.url }}{{ site.baseurl }}/assets/lotto.jpg)

Next we will demonstrate Hexagonal Architecture by building a lottery system. The lottery system will provide two primary ports: One for the users to submit lottery tickets and another for system administrators to perform the draw.

Secondary ports consist of lottery ticket database, banking for wire transfers and notifications for pushing the lottery results to the players. The resulting hexagon of the system can be seen in the following diagram.

![Lottery system]({{ site.url }}{{ site.baseurl }}/assets/lottery.png)

## Start from the core

We start the implementation from the system core. First we need to identify the core concepts of the lottery system. Probably the most important one is the lottery ticket. In lottery ticket you are supposed to pick the numbers and write your contact details to receive email notifications and possibly the prize money directly to your bank account. This leads us to write the following classes.

<script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/hexagonal/src/main/java/com/iluwatar/hexagonal/domain/LotteryTicket.java?slice=24:71"></script>
<script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/hexagonal/src/main/java/com/iluwatar/hexagonal/domain/LotteryNumbers.java?slice=30:121"></script>

LotteryTicket contains LotteryNumbers and PlayerDetails. LotteryNumbers contains means to hold given numbers or generate random numbers and test the numbers for equality with another LotteryNumbers instance. [PlayerDetails](https://github.com/iluwatar/java-design-patterns/blob/master/hexagonal/src/main/java/com/iluwatar/hexagonal/domain/PlayerDetails.java) is a simple value object containing player's email address, bank account number and phone number.

## Primary port for the players

Now that we can create lottery tickets we need a way for the players to submit them to participate in the next draw. Another feature that we need is to check a lottery ticket against the winning numbers. We will combine these things into single LotteryService that becomes one of our primary ports.

<script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/hexagonal/src/main/java/com/iluwatar/hexagonal/service/LotteryService.java?slice=31:"></script>
<script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/hexagonal/src/main/java/com/iluwatar/hexagonal/service/LotteryServiceImpl.java?slice=39:"></script>

LotteryService is the port and LotteryServiceImpl is the adapter. LotteryServiceImpl needs access to the lottery ticket database to add new tickets and to check submitted ticket for winnings.

## Primary port for the administrators

We also need a lottery administrator facing interface that enables browsing of the submitted lottery tickets to determine the winners and a method for performing the lottery draw. The implementation for this primary port is presented next.

<script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/hexagonal/src/main/java/com/iluwatar/hexagonal/administration/LotteryAdministration.java?slice=30:"></script>
<script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/hexagonal/src/main/java/com/iluwatar/hexagonal/administration/LotteryAdministrationImpl.java?slice=41:"></script>

LotteryAdministration port has `resetLottery()` method for starting new lottery round. At this stage the player submit their lottery tickets into the database and when the time is due the administration calls `performLottery()` to draw the winning numbers and check each of the tickets for winnings.

## Secondary port for banking

Next we implement the secondary ports and adapters. The first one is the banking support that enables us to manipulate bank account funds. To explain the concept, the player can write his bank account number on the lottery ticket and in case the ticket wins the lottery system automatically wire transfers the funds.

<script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/hexagonal/src/main/java/com/iluwatar/hexagonal/banking/WireTransfers.java?slice=23:"></script>
<script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/hexagonal/src/main/java/com/iluwatar/hexagonal/banking/WireTransfersImpl.java?slice=29:"></script>

The bank back office is a simple HashMap based implementation. The lottery service's bank account is statically initialized to contain enough funds to pay the prizes in case some of the lottery tickets win.

## Secondary port for notifications

Another secondary port is the notification service. If the player has written his email address in the lottery ticket the system automatically sends notifications of the lottery system events, most importantly whether the ticket has won or not.

<script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/hexagonal/src/main/java/com/iluwatar/hexagonal/notifications/LotteryNotifications.java?slice=26:"></script>
<script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/hexagonal/src/main/java/com/iluwatar/hexagonal/notifications/LotteryNotificationsImpl.java?slice=26:"></script>

The methods in LotteryNotificationsImpl adapter are simple System.out printers so the implementation is trivial to understand.

## Secondary port for database

The last secondary port is the database. It contains methods for storing and retrieving lottery tickets.

<script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/hexagonal/src/main/java/com/iluwatar/hexagonal/database/LotteryTicketRepository.java?slice=30:"></script>
<script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/hexagonal/src/main/java/com/iluwatar/hexagonal/database/LotteryTicketInMemoryRepository.java?slice=31:"></script>

The LotteryTicketInMemoryRepository is a mock database holding its contents in memory only. We use Optionals to indicate whether the operation was successful or not.

## Lottery application

With all the pieces in place we create a command line application to drive the lottery system. The test application initializes the lottery system using the admin interface and starts collecting lottery tickets from the players. Once all the lottery tickets have been submitted the lottery number draw is performed and all the submitted tickets are checked for wins.

<script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/hexagonal/src/main/java/com/iluwatar/hexagonal/App.java?slice=23:"></script>

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

Applications implemented with Hexagonal Architecture are a joy to work with. Implementation details such as frameworks, user interfaces and databases are pushed out of the core and the application can work without them. We can clearly point in the center of the hexagon and say that this is our application and it uses these technologies to implement the submodule interfaces. Restricting communication to happen only through the ports forces the application to produce testable and maintainable code.

The full demo application of Hexagonal Architecture is available in [Java Design Patterns](https://github.com/iluwatar/java-design-patterns) Github repository.
