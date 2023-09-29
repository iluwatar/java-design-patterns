---
title: Commander
category: Concurrency
language: en
tag:
 - Cloud distributed
---

## Intent

> Used to handle all problems that can be encountered when doing distributed transactions.

## Class diagram
![alt text](./etc/commander.urm.png "Commander class diagram")

## Applicability
This pattern can be used when we need to make commits into 2 (or more) databases to complete transaction, which cannot be done atomically and can thereby create problems.

## Explanation
Handling distributed transactions can be tricky, but if we choose to not handle it carefully, there could be unwanted consequences. Say, we have an e-commerce website which has a Payment microservice and a Shipping microservice. If the shipping is available currently but payment service is not up, or vice versa, how would we deal with it after having already received the order from the user?
We need a mechanism in place which can handle these kinds of situations. We have to direct the order to either one of the services (in this example, shipping) and then add the order into the database of the other service (in this example, payment), since two databses cannot be updated atomically. If currently unable to do it, there should be a queue where this request can be queued, and there has to be a mechanism which allows for a failure in the queueing as well. All this needs to be done by constant retries while ensuring idempotence (even if the request is made several times, the change should only be applied once) by a commander class, to reach a state of eventual consistency.

### Real-world example:

Imagine you're running an e-commerce website with separate microservices for payments and shipping. When a customer places an order, you need to ensure that both the payment and shipping processes are successful. However, if one service is available and the other isn't, handling this situation can be complex. The Commander pattern comes to the rescue by directing the order to one service, then recording it in the database of the other service, even though atomic updates across both databases are not possible. It also includes a queuing mechanism for handling situations when immediate processing isn't possible. The Commander pattern, with its constant retries and idempotence, ensures eventual consistency in such scenarios.

### In plain words:

The Commander pattern helps manage distributed transactions, where multiple actions need to happen across different services or databases, but you can't guarantee they'll all happen atomically. Think of it as orchestrating a dance between services – if one is ready to perform and the other isn't, you have to guide the process and keep track of what's happening. Even if things go wrong or need to be retried, you make sure that the end result is consistent and doesn't result in double work.

### Wikipedia says:

The [Commander pattern](https://en.wikipedia.org/wiki/Commander_pattern) is a design pattern used in distributed systems and microservices architectures. It addresses the challenges of handling distributed transactions, where actions across multiple services or databases need to be coordinated without atomic guarantees. The pattern involves directing requests, queuing, and retry mechanisms to ensure eventual consistency in a distributed environment.

### Programmatic example:
Here's a programmatic example in Python that demonstrates a simplified implementation of the Commander pattern for handling distributed transactions across two imaginary microservices: payment and shipping. This example uses a basic queuing mechanism and focuses on directing orders between the two services and ensuring idempotence:
import time
import random

# Mocked databases for payment and shipping
payment_database = []
shipping_database = []

# Commander class to handle distributed transactions
class Commander:
    def __init__(self):
        self.queue = []

    def execute(self, order):
        # Simulate a random failure scenario (e.g., one service is down)
        if random.random() < 0.3:
            print("Service is temporarily unavailable. Queuing the order for retry.")
            self.queue.append(order)
            return

        # Process the order in one service
        if random.random() < 0.5:
            payment_database.append(order)
            print(f"Order processed by Payment Service: {order}")
        else:
            shipping_database.append(order)
            print(f"Order processed by Shipping Service: {order}")

    def retry(self):
        print("Retrying queued orders...")
        for order in self.queue:
            self.execute(order)
        self.queue = []

# Simulate placing orders
def place_orders(commander, num_orders):
    for i in range(num_orders):
        order = f"Order {i+1}"
        commander.execute(order)

# Create a Commander instance
commander = Commander()

# Simulate placing orders
place_orders(commander, 10)

# Simulate a service recovery scenario
time.sleep(5)
print("Service is back online.")

# Retry queued orders
commander.retry()
In this example:

We have two mocked databases (payment_database and shipping_database) to simulate the storage of orders in the payment and shipping services.

The Commander class handles the distributed transactions. It directs orders to either the payment or shipping service, simulating the unavailability of services in some cases. If a service is unavailable, orders are queued for retry.

The place_orders function simulates placing orders, and the program attempts to process these orders through the Commander.

After a simulated service recovery, the retry method is called to process any queued orders.

## Credits

* [Distributed Transactions: The Icebergs of Microservices](https://www.grahamlea.com/2016/08/distributed-transactions-microservices-icebergs/)
