---
layout: pattern
title: Event Queue
folder: event-queue
permalink: /patterns/event-queue/
categories: Concurrency
language: en
tags:
 - Game programming
---

## Intent
The intent of the event queue design pattern, also known as message queues, is to decouple the relationship between the 
sender and receiver of events within a system. By decoupling the two parties, they do not interact with the event queue 
simultaneously. Essentially, the event queue handles and processes requests in an asynchronous manner, therefore, this 
system can be described as a first in, first out design pattern model. Event Queue is a suitable pattern if there is a 
resource with limited accessibility (i.e. Audio or Database), however, you need to provide access to all the requests 
which seeks this resource. Upon accessing an event from the queue, the program also removes it from the queue.

![alt text](./etc/event-queue-model.png "Event Queue Visualised")

## Explanation 

Real world example

> The modern emailing system is an example of the fundamental process behind the event-queue design pattern. When an email
> is sent, the sender continues their daily tasks without the necessity of an immediate response from the receiver. 
> Additionally, the receiver has the freedom to access and process the email at their leisure. Therefore, this process 
> decouples the sender and receiver so that they are not required to engage with the queue at the same time.


In plain words

> The buffer between sender and receiver improves maintainability and scalability of a system. Event queues are typically 
> used to organise and carry out interprocess communication (IPC).

Wikipedia says

> Message queues (also known as event queues) implement an asynchronous communication pattern between two or more processes/
>threads whereby the sending and receiving party do not need to interact with the queue at the same time.

**Programmatic Example**



## Class diagram
![alt text](./etc/model.png "Event Queue")

## Applicability

Use the Event Queue Pattern when

* The sender does not require a response from the receiver.
* You wish to decouple the sender & the receiver.
* You want to process events asynchronously.
* You have a limited accessibility resource and the asynchronous process is acceptable to reach that.

## Credits

* [Mihaly Kuprivecz - Event Queue] (http://gameprogrammingpatterns.com/event-queue.html)
* [Wikipedia - Message Queue] (https://en.wikipedia.org/wiki/Message_queue)
* [AWS - Message Queues] (https://aws.amazon.com/message-queue/)
