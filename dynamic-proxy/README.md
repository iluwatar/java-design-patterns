---
title: Dynamic Proxy
category: Structural
language: en
tag:
  - Decoupling
  - Dynamic typing
  - Proxy
  - Runtime
---

## Also known as

* Runtime Proxy
* Virtual Proxy

## Intent

To provide a flexible proxy mechanism capable of dynamically creating proxies for various interfaces at runtime, allowing for controlled access or functionality enhancement of objects.

## Explanation

### Real-world example

> Mockito, a popular Java mocking framework, employs dynamic proxy to create mock objects for testing. Mock objects mimic the behavior of real objects, allowing developers to isolate components and verify interactions in unit tests. Consider a scenario where a service class depends on an external component, such as a database access object (DAO). Instead of interacting with a real DAO in a test, Mockito can dynamically generate a proxy, intercepting method invocations and returning predefined values. This enables focused unit testing without the need for a real database connection.

### In plain words

> Dynamic proxy is a specialized form of proxy in Java, serving as a flexible and dynamic method to intercept and manipulate method calls. By utilizing dynamic proxies, developers can implement additional functionalities without modifying the original class code.

### Wikipedia says

> A dynamic proxy class is a class that implements a list of interfaces specified at runtime such that a method invocation through one of the interfaces on an instance of the class will be encoded and dispatched to another object through a uniform interface. Thus, a dynamic proxy class can be used to create a type-safe proxy object for a list of interfaces without requiring pre-generation of the proxy class, such as with compile-time tools. Method invocations on an instance of a dynamic proxy class are dispatched to a single method in the instance's invocation handler, and they are encoded with a _java.lang.reflect.Method_ object identifying the method that was invoked and an array of type _Object_ containing the arguments.

### Programmatic Example

This example allow us to hit the public fake API https://jsonplaceholder.typicode.com for the resource Album through an interface.

The call to _Proxy.newProxyInstance_ creates a new dynamic proxy for the _AlbumService_ interface and sets the _AlbumInvocationHandler_ class as the handler to intercept all the interface's methods. Everytime that we call an _AlbumService_'s method, the handler's method `invoke` will be call automatically, and it will pass all the method's metadata and arguments to other specialized class - _TinyRestClient_ - to prepare the Rest API call accordingly.

![Sequence diagram](./etc/dynamic-proxy-sequence.png "Dynamic Proxy sequence diagram")

In this demo, the Dynamic Proxy pattern help us to run business logic through an interface without an explicit implementation of that interface and supported on the Java Reflection approach.

The App class sets up the dynamic proxy for the AlbumService interface and demonstrates how to use the proxy to make API calls.

```java
public class App {

    private AlbumService albumServiceProxy;

    // Constructor initializes the dynamic proxy
    public App(String baseUrl, HttpClient httpClient) {
        AlbumInvocationHandler handler = new AlbumInvocationHandler(baseUrl, httpClient);
        albumServiceProxy = (AlbumService) Proxy.newProxyInstance(
                App.class.getClassLoader(),
                new Class<?>[] {AlbumService.class},
                handler);
    }

    // Method to perform API operations using the dynamic proxy
    public void performApiOperations() {
        System.out.println(albumServiceProxy.readAlbums());
        System.out.println(albumServiceProxy.readAlbum(1));
        System.out.println(albumServiceProxy.createAlbum(new Album("New Album", 1)));
    }

    // Main method to run the application
    public static void main(String[] args) {
        HttpClient httpClient = HttpClient.newHttpClient();
        App app = new App("https://jsonplaceholder.typicode.com", httpClient);
        app.performApiOperations();
    }
}
```

* The constructor creates a AlbumInvocationHandler using the provided baseUrl and httpClient.
* The Proxy.newProxyInstance method is used to dynamically create a proxy instance of AlbumService. This proxy intercepts all interface method calls and routes them through the AlbumInvocationHandler.
* performApiOperations demonstrates using the dynamic proxy to make API calls as if AlbumService were a regular implementation.

The AlbumService interface defines the API operations that can be dynamically proxied. It uses custom annotations to specify HTTP methods and paths.

```java
public interface AlbumService {
    @Get("/albums")
    List<Album> readAlbums();

    @Get("/albums/{id}")
    Album readAlbum(@Path("id") int albumId);

    @Post("/albums")
    Album createAlbum(@Body Album album);
}
```

* Annotations like @Get and @Post indicate the HTTP method and the path for each method.
* These annotations are used by the TinyRestClient to construct appropriate HTTP requests.

The AlbumInvocationHandler is where method calls on the proxy are intercepted and delegated to the TinyRestClient.

```java
public class AlbumInvocationHandler implements InvocationHandler {

    private TinyRestClient restClient;

    public AlbumInvocationHandler(String baseUrl, HttpClient httpClient) {
        restClient = new TinyRestClient(baseUrl, httpClient);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return restClient.send(method, args);
    }
}
```

* Implements InvocationHandler, which requires defining the invoke method.
* The invoke method is called every time a method on the proxy is invoked. It delegates the call to the TinyRestClient, passing along the method and its arguments.

The TinyRestClient handles the construction and sending of HTTP requests based on the annotations and method details.

```java
public class TinyRestClient {

    private String baseUrl;
    private HttpClient httpClient;

    public TinyRestClient(String baseUrl, HttpClient httpClient) {
        this.baseUrl = baseUrl;
        this.httpClient = httpClient;
    }

    public Object send(Method method, Object[] args) throws IOException, InterruptedException {
        String url = baseUrl + "/albums";  // Simplified URL handling for the example
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .GET()  // Simplified to always use GET for the example
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();  // Simplified response handling
    }
}
```

* This class uses Java's HttpClient to send HTTP requests.
* The send method constructs a HttpRequest using details from the method's annotations, although simplified here to focus on the dynamic proxy mechanism.
* The response is returned as a simple string, demonstrating basic interaction with an API.

Each component of this example plays a specific role in demonstrating the use of the Dynamic Proxy pattern, showcasing how Java can facilitate runtime proxy creation and method interception.

## Class diagram

![Class diagram](./etc/dynamic-proxy-classes.png "Dynamic Proxy class diagram")

## Applicability

Dynamic proxy should be used when you need to augment or enhance your current functionality without modifying your current code. Some examples of that usage could be:

* You want to intercept calls to methods of an object at runtime for handling purposes such as logging, transaction management, or security checks.
* You need to create a proxy object for one or more interfaces dynamically at runtime without coding it explicitly for each interface.
* You aim to simplify complex systems by decoupling the client and the real object through a flexible proxy mechanism.

## Tutorials

* [Dynamic Proxies in Java - CodeGym](https://codegym.cc/groups/posts/208-dynamic-proxies)
* [Introduction To Java Dynamic Proxy - Xperti](https://xperti.io/blogs/java-dynamic-proxies-introduction/)

## Known uses

Many frameworks and libraries use dynamic proxy to implement their functionalities:

* [Spring Framework](https://docs.spring.io/spring-framework/reference/core/aop.html), for aspect oriented programming.
* [Hibernate](https://hibernate.org/orm/), for data lazy loading.
* [Mockito](https://site.mockito.org/), for mocking objects in testing.
* [Cleverclient](https://github.com/sashirestela/cleverclient), for calling http endpoints through annotated interfaces.
* Java Reflection API: Java's built-in support for dynamic proxies with the use of java.lang.reflect.Proxy and java.lang.reflect.InvocationHandler.
* Frameworks: Extensively used in Java frameworks like Spring for AOP (Aspect-Oriented Programming) to handle transactions, security, logging, etc.
* Middleware: In middleware services for transparently adding services like load balancing and access control.

## Consequences

Benefits:

* Increased flexibility in code: Dynamic proxies in Java offer a high degree of flexibility, allowing developers to create versatile and adaptable applications. By using dynamic proxies, software engineers can modify the behavior of methods at runtime, which is particularly useful in scenarios where the behavior of classes needs to be augmented or manipulated without altering their source code. This flexibility is crucial in developing applications that require dynamic response to varying conditions or need to integrate with systems where interfaces may change over time.
* Simplifying complex operations: Dynamic proxies excel in simplifying complex operations, particularly in the areas of cross-cutting concerns such as logging, transaction management, and security. By intercepting method calls, dynamic proxies can uniformly apply certain operations across various methods and classes, thereby reducing the need for repetitive code. This capability is particularly beneficial in large-scale applications where such cross-cutting concerns are prevalent. For example, adding logging or authorization checks across multiple methods becomes a matter of implementing these features once in an invocation handler, rather than modifying each method individually.
* Enhancing code maintainability: Maintainability is a key advantage of using dynamic proxies. They promote cleaner and more organized code by separating the core business logic from cross-cutting concerns. This separation of concerns not only makes the codebase more understandable but also easier to test and debug. When the business logic is decoupled from aspects like logging or transaction handling, any changes in these areas do not impact the core functionality of the application. As a result, applications become more robust and easier to maintain and update, which is crucial in the fast-paced environment of software development where requirements and technologies are constantly evolving.
* Separation of Concerns: Promotes separation of concerns by decoupling the client code from the actual handling of method calls.

Trade-offs:

* Performance overhead: The use of reflection and method invocation through proxies can introduce latency, especially in performance-critical applications. This overhead might be negligible in most cases, but it becomes significant in scenarios with high-frequency method calls.
* Complexity of debugging: Since dynamic proxies introduce an additional layer of abstraction, tracing and debugging issues can be more challenging. It can be difficult to trace the flow of execution through proxies, especially when multiple proxies are involved.
* Limited to interface-based programming: They can only proxy interfaces, not classes. This limitation requires careful design considerations, particularly in situations where class-based proxies would be more appropriate.
* Higher level of expertise: Developers are normally not a fan of “magic code” — code that works in a non-transparent or overly complex manner. Those unfamiliar with the proxy pattern or reflection might find the codebase more complex to understand and maintain, potentially leading to errors or misuse of the feature. This complexity can be perceived as a form of “magic” that obscures the underlying process, making the code less intuitive and more challenging to debug or extend. Therefore, while dynamic proxies are powerful, their use should be approached with caution and a thorough understanding of their inner workings.

## Related patterns

* [Proxy](https://java-design-patterns.com/patterns/proxy): Static counterpart of the Dynamic Proxy, where proxies are explicitly coded.
* [Decorator](https://java-design-patterns.com/patterns/decorator/): Similar in structure by providing additional functionality, but without the dynamic proxy's capability to handle any interface.
* [Facade](https://java-design-patterns.com/patterns/facade/): Simplifies the interface to complex systems, not through dynamic proxies but through a single simplified interface.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3U0d8Gm)
* [Java Reflection in Action](https://amzn.to/3TVpe3t)
* [Pro Spring 6: An In-Depth Guide to the Spring Framework](https://amzn.to/43Zs2Bx)
* [Dynamic Proxies in Java - Baeldung](https://www.baeldung.com/java-dynamic-proxies)
* [Dynamic Proxy Classes - Oracle](https://docs.oracle.com/javase/8/docs/technotes/guides/reflection/proxy.html)
* [Intro To Java Dynamic Proxies - KapreSoft](https://www.kapresoft.com/java/2023/12/27/intro-to-java-proxies.html)
* [Exploring the Depths of Dynamic Proxy in Java: A Comprehensive Guide](https://naveen-metta.medium.com/exploring-the-depths-of-dynamic-proxy-in-java-a-comprehensive-guide-f34fb45b38a3)
