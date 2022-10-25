---
title: Data Bus
category: Architectural
language: en
tags:
 - Decoupling
---

## Intent

Allows send of messages/events between components of an application
without them needing to know about each other. They only need to know
about the type of the message/event being sent.

## Explanation

Real world example

Say you have an app that enables online bookings and participation of events. You want the app to send notifications such as event advertisements to everyone who is an ordinary member of the community or organisation holding the events. However, you do not want to send such notifications like advertisements to the event administrators or organisers but you desire to send them and them only the time whenever a new advertisement is sent to all members of the community. The Data Bus enables you to selectively notify people of a community by type, whether it be ordinary community members or event administrators, by making their classes or components only accept messages of a certain type. Ultimately, there is no need for the components or classes of ordinary community members nor administrators to know anything about you in terms of the classes or components you are using to notify the entire community except for the need to know the type of the messages you are sending.

In plain words

> Data Bus is a design pattern that is able to connect components of an application for communication simply and solely by the type of message or event that may be transferred.

Programmatic Example

Translating the online events app example above, we firstly have our Member interface and its implementations composed of MessageCollectorMember (the ordinary community members) and StatusMember (the event administrators or organisers).

Then we have a databus to subscribe someone to be a member or unsubcribe, and also to publish an event so to notify every member in the community.

As you can see, the accept method is applied for each member under the publish method.

Hence, the accept method can be used to check the type of message to be published and successfully send that message if the accept method has an instance for that message. Otherwise, the accept method cannot as is for the case of the MessageCollectorMember (the ordinary community members) when the type of message being sent is StartingData or StoppingData (information on the time whenever a new advertisement is sent to all members). The MessageCollectorMember class



However, the StatusMember(the event administrators or organisers) can

The Status Member

Thus, the data bus outputs as follows:

## Class diagram
![data bus pattern uml diagram](./etc/data-bus.urm.png "Data Bus pattern")

## Applicability
Use Data Bus pattern when

* you want your components to decide themselves which messages/events they want to receive
* you want to have many-to-many communication
* you want your components to know nothing about each other

## Related Patterns
Data Bus is similar to

* Mediator pattern with Data Bus Members deciding for themselves if they want to accept any given message
* Observer pattern but supporting many-to-many communication
* Publish/Subscribe pattern with the Data Bus decoupling the publisher and the subscriber
