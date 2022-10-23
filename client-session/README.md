---
layout: pattern
title: Client Session Pattern
folder: client-session
permalink: /patterns/client-session/
categories: Client Architecture
language: en
tags:
  - Client distributed
  - Stateless servers
---

## Name

[Client Session pattern](https://dzone.com/articles/practical-php-patterns/practical-php-patterns-client)

## Intent

- Create stateless servers that removes the problem of clustering, as users can switch between servers seemlessly.
- Makes data more resilient in case of server failover.
- Works well with smaller data sizes.

## Explanation

Real-World Example

> You're looking to create a 

In Plain words

> Instead of storing information about the current client and the information being accessed on the server, it is maintained client side only. Client has to send session data with each request to the server and has to send an updated state back to the client, which is stored on the clients machine. The server doesn't have to store the client information.([ref](https://dzone.com/articles/practical-php-patterns/practical-php-patterns-client))

## Architecture Diagram

![alt text](./etc/session_state_pattern.png "Session State Pattern")

## Applicability

Use the client state pattern when:

- Processing smaller amounts of data to prevent larger requests and response sizes.
- Remove the need for servers to save client states. Doing so also removes the need to store session IDs.
- Clustering is an issue and needs to be avoided. Stateless servers allow clients to be easily distributed across servers.
- Creates resilience from data losses due to server fails.

## Consequences

- The server is stateless. Any compute API will not store any data.
- Struggles to deal with large amounts of data. Creates longer send and recieve times due to larger amounts of session data to manage.
- Security. All data is stored on the client's machine. This means that any vulnerabilites on the clients side can expose all data being sent and recieved by the server.


## Credits

- [Dzone - Practical PHP patterns](https://dzone.com/articles/practical-php-patterns/practical-php-patterns-client)
- [Client Session State Design Pattern - Ram N Java](https://www.youtube.com/watch?v=ycOSj9g41pc)
