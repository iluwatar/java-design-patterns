# Java Design Patterns - Fallback Pattern

## Overview

This project demonstrates the implementation of the Fallback Pattern with Circuit Breaker in Java. The Fallback Pattern is used to handle failures gracefully by providing alternative solutions when the primary service fails. The Circuit Breaker pattern is integrated to prevent cascading failures and to monitor the health of the primary service.

## Features

- **Primary Service**: The main service that attempts to retrieve data.
- **Fallback Service**: A backup service that provides data when the primary service fails.
- **Circuit Breaker**: Monitors the health of the primary service and prevents further calls when the service is deemed unhealthy.
- **Service Monitor**: Tracks the performance metrics of the services.
- **Rate Limiter**: Limits the number of requests to prevent overload.
- **Service Exception**: Custom exceptions to handle service-specific errors.

## Components

- **Service Interface**: Defines the contract for services.
- **RemoteService**: Implementation of the primary service that makes HTTP calls.
- **LocalCacheService**: Implementation of the fallback service that provides cached data.
- **FallbackService**: Manages the primary and fallback services, integrating the circuit breaker and rate limiter.
- **DefaultCircuitBreaker**: Implementation of the circuit breaker with state transitions and failure tracking.
- **ServiceMonitor**: Monitors and records the performance metrics of the services.
- **Service Exception**: Custom exception class to handle errors specific to service operations.

## Usage

To run the application, execute the `App` class. The application will attempt to retrieve data using the primary service and fallback to the cached data if the primary service fails.

## Reflection

This assignment provided a practical application of Object-Oriented Analysis and Design (OOAD) principles. By implementing the Fallback Pattern with Circuit Breaker, I was able to apply concepts such as encapsulation, polymorphism, and separation of concerns. Each component in the system has a clear responsibility, and the use of interfaces allows for flexibility and easy substitution of different implementations.

One of the challenges faced was ensuring thread safety and proper synchronization, especially in the circuit breaker and rate limiter components. To overcome this, I used concurrent data structures and synchronized methods to manage state transitions and request handling. Additionally, integrating the health monitoring and metrics collection required careful consideration of performance and resource management, which was addressed by using scheduled executors and atomic variables.
