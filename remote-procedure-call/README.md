---
title: Remote Procedure Call in Java using gRPC
shortTitle: gRPC in Java
description: Remote Procedure Call in Java using gRPC communicating between 2 microservices product and cart application
category: Data Transfer
language: en
tag:
- Behavioral
- Integration
- Messaging
- Client-Server
- Data Transfer
- Microservices
- Remote Procedure Call
- Remote Method Invocation
---
# Remote Method Invocation / Remote Procedure Call
Remote Method Invocation has various different aliases, Remote Procedure Call / Remote Method Invocation or RPC for short. Is a protocol
that one program can use to request a service from a different program located in same or another computer in a network without having to understand network details.

RMI can be implemented in various different languages and frameworks. Technologies like REST, gRPC and Thrift can be used for RMI. In this example we
will be using gRPC to implement Remote Procedure Call in Java.

## Terminologies
- Client: The client is the application that sends a request to the server.
- Server: The server is the application that receives the request from the client and sends a response back to the client.
- Stub: The client-side proxy for the server.
- Skeleton: The server-side proxy for the client.
- Protocol: The set of rules that the client and server follow to communicate with each other.
- Stream: The sequence of messages that are sent between the client and server, understand it as list of objects.

## What is gRPC?
[gRPC](https://grpc.io/docs/what-is-grpc/introduction/) is a high-performance, open-source and universal RPC framework. gRPC was developed by Google but is now open-source
and is based on the HTTP/2 protocol.

A gRPC client can call a method on a gRPC server similar to if it was calling a method on a local object.
This allows client and server to communicate with each other by just using method calls. gRPC internally uses [protobuf](https://protobuf.dev/) to serialize the data for communication.

## When to use gRPC?
gRPC should be used when you need high performance communication between client and server. It is mostly used in micro-service architecture when one
service needs to communicate with another service.

For communication you need to define contract / interfaces denoting the method signature, data types and parameters along with return type.
These methods can be called by client service just like a method call, when using gRPC, a gRPC service is created which will in-turn call the
implementation of the RPC method in the server and return the response (if any).

Start by creating a .proto file which should have all the methods and data types you need for communication between the services.
When you compile your code `maven/gradle gRPC` plugin will in return create objects and interfaces which you need to implement / extend in your
server side. The client will then call the method defined in .proto file using the generated stubs by gPRC. In return inside the server the
implementation of the method will be called and the response will be sent back to the client.

### In this example
We will be using 2 different micro-services
- product-service
- cart-service

Along with a shopping.proto file to define the contract between the services.
- ShoppingService

This is a basic e-commerce simulation.

In this simple example the `product-service` has data related to products and is used a source of truth. The `cart-service`
needs the product data that is available in `product-service`. Certain number of products in turn may be bought by a customer,
inside the cart service at which point the product quantity needs to be decreased in `product-service`, hence the need for bidirectional
communication from `product-service` -> `cart-service` and vice versa they both communicate via gRPC.

- getAllProducts() - gets all the product from state in `product-service` and stores it in `cart-service`
- reduceProductQuantity() - reduces the quantity of a product by `id` fetched by `getAllProducts` and stored in `cart-service`
 when the method is hit, it reduces the quantity of product with same `id` in `product-service`

## How to implement gRPC in Java?
### .proto file
- Create a [.proto](https://protobuf.dev/programming-guides/proto2/) file [example](./proto/shopping.proto) defining the service and message contracts
- Define service interfaces, method signatures and data types in your .proto file
### At the server end
- Add gRPC and protobuf dependencies in your `pom.xml`
- Include gRPC and protobuf plugins in `mvn build plugins`, for it to generate interfaces from your `.proto` during compilation
- Include the .proto file directory in mvn build plugins to generate the interfaces
- Build the project via `mvn clean install`
- gRPC will generate the stubs and skeletons for you
- Implement the service logic for the generated methods of skeleton in your service classes
- Start the gRPC server at server's side on a specific port and attach the gRPC Implementation service to it
### At the client end
- Add gRPC and protobuf dependencies in your `pom.xml`
- Include gRPC and protobuf plugins in `mvn build plugins`, for it to generate interfaces from your `.proto` during compilation
- Include the .proto file directory in mvn build plugins to generate the interfaces
- Build the project via `mvn clean install`
- gRPC will generate the stubs and skeletons for you
- A stub will expose the available methods to be called by the client, call the methods you need on server via the stub
- Create Channel with server's host and port at client's end to communicate between server and client
- Start client, and you are good to go

## gRPC in action
### Product Service
#### Service
- ProductService - API Interface for Internal logic in `product-service`
- ProductServiceImpl - Implementation of ProductService, saves product data in memory for simplicity, exposes getter(s) for the same.
 Houses Composition of ProductService to store state.
- ProductServiceGrpcImpl - gRPC contract implementation, methods to retrieve all products and reduce quantity of a product.
This file implements the logic that should be executed when gRPC methods are called
     
#### Model
- Product - Product POJO Model
#### Mocks
- ProductMocks - Mock data of Product for testing and state initialization.

### Cart Service
#### Service
- CartService - API Interface for Internal logic in `cart-service`, 
- CartServiceImpl - Implementation of CartService, methods to call the stub to populate data in cart and reduce quantities.
 This file calls the gRPC method to communicate with `product-service`.
#### Model
- ProductInCart - Cut from Product POJO in `product-service`

### proto
Proto folder contains all the proto files which define contract for the services.
proto files end with .proto and contain the types, methods and services that are to be used in gRPC communication.

### Good practise
- Keep types / method names in PascalCase in .proto file

### How to run this project
- Clone the project
- navigate to 'remote-procedure-call' directory via
```shell
cd java-design-patterns/remote-procedure-call
```
- build the project with, this will download dependencies, compile .proto to java interface and classes and create final jar
```shell
mvn clean install
```
- Start the `product-service` before `cart-service` as `cart-service` depends on product-service
```shell
mvn exec:java -Dexec.mainClass="com.iluwatar.rpc.product.Main" -pl product-service
```
- Start a new terminal session
- navigate to 'remote-procedure-call' directory
- Start the `cart-service`
```shell
mvn exec:java -Dexec.mainClass="com.iluwatar.rpc.cart.Main" -pl cart-service
```
- `cart-service` on startup will hit a gRPC call to `product-service` to get all products and populate the cart.
- `cart-service` will then reduce the quantity of a product in `product-service` when a product is bought.
- `cart-service` will then shut-down gracefully.
- all the operations will be logged in the console.
- `product-service` will continue to run and can be used for further operations by running `cart-service` again.
- To stop the services, press `ctrl+c` in the terminal.
