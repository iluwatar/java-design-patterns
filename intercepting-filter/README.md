---
title: Intercepting Filter
category: Behavioral
language: en
tag:
 - Decoupling
---

## Intent
An intercepting filter is a useful Java Design Pattern used when you want to pre-process
or post-process a request in an application. These filters are created and applied to the
request before it is given to the target application. Such examples of uses include authentication,
which is necessary to be processed before the request is given to the application.

## Explanation
Real-world Example
> An example of using the Intercepting Filter design pattern is relevant when making an ecommerce
> platform. It is important to implement various filters for authentication of account, authentication
> of payment, logging and caching. Important types of filters in this example are authentication, logging,
> security, and caching filters.

In plain words
> An intercepting filter in Java is like a series of security checkpoints for requests and responses in a 
> software application. It checks and processes data as it comes in and goes out, helping with tasks like 
> authentication, logging, and security, while keeping the core application safe and clean.

Wikipedia says
> Intercepting Filter is a Java pattern which creates pluggable filters to process common services in a 
> standard manner without requiring changes to core request processing code.

## Programmatic Example
As an example, we can create a basic Filter class and define an Authentication Filter. The filter has missing logic,
but 
```java
// 1. Define a Filter interface
interface Filter {
    void runFilter(String request);
}
// 2. Create a Authentication filter
class AuthenticationFilter implements Filter {
    public void runFilter(String request) {
        // Authentication logic would be passed in here
        if (request.contains("authenticated=true")) {
            System.out.println("Authentication successful for request: " + request);
        } else {
            System.out.println("Authentication failed for request: " + request);
        }
    }
}
// 3. Create a Client to send requests and activate the filter
class Client {
    // create an instance of the filter in the Client class
    private Filter filter;

    // create constructor
    public Client(Filter filter) {
        this.filter = filter;
    }

    // send the String request to the filter, the request does not have to be a string
    // it can be anything
    public void sendRequest(String request) {
        filter.runFilter(request);
    }
}
// 4. Demonstrate the Authentication Filter
public class AuthenticationFilterExample {
    public static void main(String[] args) {
        Filter authenticationFilter = new AuthenticationFilter();
        Client client = new Client(authenticationFilter);

        // Simulate requests for false
        client.sendRequest("GET /public-page");
        // this request would come back as true as the link includes an argument
        // for successful authentication
        client.sendRequest("GET /private-page?authenticated=true");
    }
}
```
This is a basic example of how to implement the skeleton of a filter. The authentication logic in AuthenticationFilterExample is missing, but can be filled into the gaps. 

Additionally, the client can be setup to run multiple filters on its request using a For loop populated with filters as can be seen below:
```java
// 1. Define a Filter interface
interface Filter {
    void runFilter(String request);
}

// 2. Create an Authentication filter
class AuthenticationFilter implements Filter {
    public void runFilter(String request) {
        // Authentication logic would be placed here
        if (request.contains("authenticated=true")) {
            System.out.println("Authentication successful for request: " + request);
        } else {
            System.out.println("Authentication failed for request: " + request);
        }
    }
}

// 3. Create a Client to send requests and activate multiple filters
class Client {
    // create a list of filters in the Client class
    private List<Filter> filters = new ArrayList<>();

    // add filters to the list
    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    // send the request through all the filters
    public void sendRequest(String request) {
        for (Filter filter : filters) {
            filter.runFilter(request);
        }
    }
}

// 4. Demonstrate multiple filters
public class MultipleFiltersExample {
    public static void main(String[] args) {
        // Create a client
        Client client = new Client();

        // Add filters to the client
        Filter authenticationFilter = new AuthenticationFilter();
        client.addFilter(authenticationFilter);

        // Add more filters as needed
        // Filter anotherFilter = new AnotherFilter();
        // client.addFilter(anotherFilter);

        // Simulate requests
        client.sendRequest("GET /public-page");
        client.sendRequest("GET /private-page?authenticated=true");
    }
}
```
This method allows quick and easy manipulation and checking of data before authenticating a login or finishing some other sort of action.

## Class diagram 
![alt text](./etc/intercepting-filter.png "Intercepting Filter")

## Applicability
Use the Intercepting Filter pattern when

* A program needs to pre-process or post-process data
* A system needs authorisation/authentication services to access sensitive data
* You want to log/audit requests or responses for debugging or storing purposes, such as timestamps and user actions
* You want to transform data of a type to another type before it is given to the end process
* You want to implement specific exception handling

## Consequences
Consequences that come with implementing Intercepting Filter

* Increase in code complexity, diminishing ease of readability
* Can have issues in the order that filters are applied if order is important
* Applying multiple filters to a request can create a delay in response time
* Testing the effects of multiple filters on a request can be hard
* Compatibility and version management can be difficult if you have a lot of filters

## Tutorials

* [Introduction to Intercepting Filter Pattern in Java](https://www.baeldung.com/intercepting-filter-pattern-in-java)

## Real world examples

* [javax.servlet.FilterChain](https://tomcat.apache.org/tomcat-8.0-doc/servletapi/javax/servlet/FilterChain.html) and [javax.servlet.Filter](https://tomcat.apache.org/tomcat-8.0-doc/servletapi/javax/servlet/Filter.html)
* [Struts 2 - Interceptors](https://struts.apache.org/core-developers/interceptors.html)

## Credits

* [TutorialsPoint - Intercepting Filter](http://www.tutorialspoint.com/design_pattern/intercepting_filter_pattern.htm)
