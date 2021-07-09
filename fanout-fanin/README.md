---
layout: pattern
title: Fan-Out/Fan-In
folder: fanout-fanin
permalink: /patterns/fanout-fanin/
categories: Integration
language: en
tags:
- Microservices
---

## Intent
The pattern is used when a source system needs to run one or more long-running process that will fetch some data. 
Source will not block itself waiting for the reply. <br> The pattern will run the same function in multiple 
services or machines to fetch the data. This is equivalent to invoking the function multiple times on different chunks of data.  

## Explanation
The FanOut/FanIn service will take in a list of requests and a consumer. Each request might complete at different time.
FanOut/FanIn service will accept the input params and returns the initial system an ID to acknowledge that the pattern
service has received the requests. Now the caller will not wait or expect the result in the same connection. 

Meanwhile, the pattern service will invoke the requests that have come. The requests might complete at different time. 
These requests will be processed in different instances of the same function in different machines or services. As the 
requests get completed, a callback service everytime is called that transforms the result into a common format, or a single object 
that gets pushed to a consumer. That caller will be at the other end of the consumer receiving the result.

## Class diagram
![alt-text](./etc/fanout-fanin.png)

## Applicability

Use this pattern when you can chunk the workload or load into multiple chunks that can be dealt with separately.

## Related patterns

- Aggregator Microservices
- API Gateway

## Credits

* [Understanding Azure Durable Functions - Part 8: The Fan Out/Fan In Pattern](http://dontcodetired.com/blog/post/Understanding-Azure-Durable-Functions-Part-8-The-Fan-OutFan-In-Pattern)
* [Fan-out/fan-in scenario in Durable Functions - Cloud backup example](https://docs.microsoft.com/en-us/azure/azure-functions/durable/durable-functions-cloud-backup)
* [Understanding the Fan-Out/Fan-In API Integration Pattern](https://dzone.com/articles/understanding-the-fan-out-fan-in-api-integration-p)