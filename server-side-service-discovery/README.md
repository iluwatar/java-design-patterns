# Server-Side Service Discovery Pattern

## Intent

The Server-Side Service Discovery pattern is a microservice architecture pattern that provides a centralized mechanism for services to register themselves and for consumers to discover available services dynamically. This pattern enhances system scalability and flexibility by decoupling service consumers from the physical locations of service providers.

## Explanation

Real-world example

> Consider a large e-commerce platform with multiple microservices like Product Service, Order Service, Payment Service, and Inventory Service. Instead of hardcoding the network locations of each service, they register themselves with a central Service Registry (like Netflix Eureka). When the Order Service needs to check product availability, it queries the Service Registry to discover available instances of the Product Service. The registry returns healthy instances, and a load balancer distributes the request among them. This approach allows services to scale up/down dynamically, fail gracefully, and be discovered automatically without manual configuration.

In plain words

> Server-Side Service Discovery provides a centralized registry where services register themselves and consumers can discover and communicate with available services dynamically.

Wikipedia says

> Service discovery is the automatic detection of devices and services offered by these devices on a computer network. In the context of microservices, service discovery refers to the mechanism by which services find and communicate with each other.

## Programmatic Example

This implementation demonstrates the Server-Side Service Discovery pattern using Spring Cloud Netflix Eureka. The example includes:

### 1. Service Registry (Eureka Server)

```java
@SpringBootApplication
@EnableEurekaServer
public class ServiceRegistryApp {
    public static void main(String[] args) {
        SpringApplication.run(ServiceRegistryApp.class, args);
    }
}
```

### 2. Service Providers (Product Service & Order Service)

```java
@SpringBootApplication
@EnableEurekaClient
public class ProductServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApp.class, args);
    }
}
```

### 3. Service Consumer with Load Balancing

```java
@Configuration
public class ServiceConsumerConfig {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

@Service
public class ServiceDiscoveryService {
    private final RestTemplate restTemplate;
    private final EurekaClient eurekaClient;
    
    public String callService(String serviceName, String endpoint) {
        try {
            String serviceUrl = "http://" + serviceName + endpoint;
            return restTemplate.getForObject(serviceUrl, String.class);
        } catch (Exception e) {
            return "Error calling service: " + e.getMessage();
        }
    }
}
```

## Key Components

### Service Registry
- **Purpose**: Central repository for service instance information
- **Implementation**: Netflix Eureka Server
- **Features**: Service registration, health monitoring, instance management

### Service Provider
- **Purpose**: Services that register themselves with the registry
- **Examples**: Product Service, Order Service
- **Features**: Auto-registration, health checks, graceful shutdown

### Service Consumer
- **Purpose**: Applications that discover and consume services
- **Features**: Service discovery, load balancing, fault tolerance
- **Implementation**: Uses Eureka Client and Spring Cloud LoadBalancer

### Load Balancer
- **Purpose**: Distributes requests among available service instances
- **Implementation**: Spring Cloud LoadBalancer
- **Strategies**: Round-robin, random, weighted

## Running the Example

### Prerequisites
- Java 21+
- Maven 3.8+

### Step 1: Start the Service Registry
```bash
cd service-registry
mvn spring-boot:run
```
The Eureka dashboard will be available at: http://localhost:8761

### Step 2: Start Service Providers
```bash
# Terminal 1 - Product Service
cd product-service
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081 --spring.application.name=product-service"

# Terminal 2 - Order Service  
cd order-service
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8082 --spring.application.name=order-service"
```

### Step 3: Start Service Consumer
```bash
cd service-consumer
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8080 --spring.application.name=service-consumer"
```

### Step 4: Test the Pattern
```bash
# Discover all services
curl http://localhost:8080/api/services

# Get products through service discovery
curl http://localhost:8080/api/products

# Get orders through service discovery  
curl http://localhost:8080/api/orders

# Check service health
curl http://localhost:8080/api/services/product-service/health
```

## Pattern Benefits

1. **Dynamic Service Discovery**: Services can be discovered at runtime without hardcoded configurations
2. **Load Balancing**: Automatic distribution of requests across available instances
3. **Fault Tolerance**: Failed instances are automatically removed from the registry
4. **Scalability**: New service instances are automatically discovered and included
5. **Health Monitoring**: Only healthy services participate in request handling
6. **Decoupling**: Services are decoupled from physical network locations

## Pattern Drawbacks

1. **Single Point of Failure**: Service registry becomes critical infrastructure
2. **Network Overhead**: Additional network calls for service discovery
3. **Complexity**: Adds operational complexity to the system
4. **Consistency**: Potential delays in service registry updates
5. **Network Partitions**: Service registry unavailability affects all services

## Related Patterns

- **Client-Side Service Discovery**: Service consumers are responsible for discovering services
- **Circuit Breaker**: Provides fault tolerance when calling discovered services
- **API Gateway**: Often combined with service discovery for external access
- **Health Check**: Essential for maintaining accurate service registry information

## Credits

- [Microservices Patterns by Chris Richardson](https://microservices.io/patterns/service-registry.html)
- [Spring Cloud Netflix Documentation](https://spring.io/projects/spring-cloud-netflix)
- [Building Microservices by Sam Newman](https://samnewman.io/books/building_microservices/)