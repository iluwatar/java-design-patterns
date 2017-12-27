---
layout: pattern
title: API Gateway
folder: api-gateway
permalink: /patterns/api-gateway/
categories: Architectural
tags:
- Java
- Difficulty-Intermediate
- Spring
---

## Intent

Aggregate calls to microservices in a single location: the API Gateway. The user makes a single
call to the API Gateway, and the API Gateway then calls each relevant microservice.

![alt text](./etc/api-gateway.png "API Gateway")

## Applicability

Use the API Gateway pattern when

* you're also using the Microservices pattern and need a single point of aggregation for your
microservice calls

## Credits

* [microservices.io - API Gateway](http://microservices.io/patterns/apigateway.html)
* [NGINX - Building Microservices: Using an API Gateway](https://www.nginx.com/blog/building-microservices-using-an-api-gateway/)
