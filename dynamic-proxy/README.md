---
title: Dynamic Proxy
category: Structural
language: en
tag:
  - Decoupling
  - Dynamic typing
  - Enhancement
  - Extensibility
  - Proxy
  - Runtime
---

## Also known as

* Runtime Proxy

## Intent

To provide a flexible proxy mechanism capable of dynamically creating proxies for various interfaces at runtime, allowing for controlled access or functionality enhancement of objects.

## Explanation

Real-world example

> Mockito, a popular Java mocking framework, employs dynamic proxy to create mock objects for testing. Mock objects mimic the behavior of real objects, allowing developers to isolate components and verify interactions in unit tests. Consider a scenario where a service class depends on an external component, such as a database access object (DAO). Instead of interacting with a real DAO in a test, Mockito can dynamically generate a proxy, intercepting method invocations and returning predefined values. This enables focused unit testing without the need for a real database connection.

In plain words

> Dynamic proxy is a specialized form of proxy in Java, serving as a flexible and dynamic method to intercept and manipulate method calls. By utilizing dynamic proxies, developers can implement additional functionalities without modifying the original class code.

Wikipedia says

> A dynamic proxy class is a class that implements a list of interfaces specified at runtime such that a method invocation through one of the interfaces on an instance of the class will be encoded and dispatched to another object through a uniform interface. Thus, a dynamic proxy class can be used to create a type-safe proxy object for a list of interfaces without requiring pre-generation of the proxy class, such as with compile-time tools. Method invocations on an instance of a dynamic proxy class are dispatched to a single method in the instance's invocation handler, and they are encoded with a _java.lang.reflect.Method_ object identifying the method that was invoked and an array of type _Object_ containing the arguments.

**Programmatic Example**

This example demonstrates using the Dynamic Proxy pattern in Java to hit the public fake API [JSONPlaceholder](https://jsonplaceholder.typicode.com) for the resource `Album` through an interface. 

In this demo, the Dynamic Proxy pattern helps us run business logic through an interface without explicitly implementing that interface, leveraging Java Reflection.

The `App` class sets up the dynamic proxy for the `AlbumService` interface and demonstrates how to use the proxy to make API calls.

```java
@Slf4j
public class App {

    static final String REST_API_URL = "https://jsonplaceholder.typicode.com";
    private AlbumService albumServiceProxy;

    public static void main(String[] args) {
        App app = new App();
        app.createDynamicProxy();
        app.callMethods();
    }

    public void createDynamicProxy() {
        AlbumInvocationHandler handler = new AlbumInvocationHandler(REST_API_URL, HttpClient.newHttpClient());
        albumServiceProxy = (AlbumService) Proxy.newProxyInstance(
                App.class.getClassLoader(), new Class<?>[]{AlbumService.class}, handler);
    }

    public void callMethods() {
        var albums = albumServiceProxy.readAlbums();
        albums.forEach(album -> LOGGER.info("{}", album));

        var album = albumServiceProxy.readAlbum(17);
        LOGGER.info("{}", album);

        var newAlbum = albumServiceProxy.createAlbum(Album.builder().title("Big World").userId(3).build());
        LOGGER.info("{}", newAlbum);

        var editAlbum = albumServiceProxy.updateAlbum(17, Album.builder().title("Green Valley").userId(3).build());
        LOGGER.info("{}", editAlbum);

        var removedAlbum = albumServiceProxy.deleteAlbum(17);
        LOGGER.info("{}", removedAlbum);
    }
}
```

* The `createDynamicProxy` method uses `Proxy.newProxyInstance` to create a new dynamic proxy for the `AlbumService` interface.
* The `callMethods` method demonstrates using the dynamic proxy to make various API calls.

The `AlbumService` interface defines the API operations that can be dynamically proxied. It uses custom annotations to specify HTTP methods and paths.

```java
public interface AlbumService {
    @Get("/albums")
    List<Album> readAlbums();

    @Get("/albums/{albumId}")
    Album readAlbum(@Path("albumId") Integer albumId);

    @Post("/albums")
    Album createAlbum(@Body Album album);

    @Put("/albums/{albumId}")
    Album updateAlbum(@Path("albumId") Integer albumId, @Body Album album);

    @Delete("/albums/{albumId}")
    Album deleteAlbum(@Path("albumId") Integer albumId);
}
```

* Annotations like `@Get` and `@Post` indicate the HTTP method and the path for each method.
* These annotations are used by the `TinyRestClient` to construct appropriate HTTP requests.

The `AlbumInvocationHandler` class is where method calls on the proxy are intercepted and delegated to the `TinyRestClient`.

```java
@Slf4j
public class AlbumInvocationHandler implements InvocationHandler {

    private TinyRestClient restClient;

    public AlbumInvocationHandler(String baseUrl, HttpClient httpClient) {
        this.restClient = new TinyRestClient(baseUrl, httpClient);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        LOGGER.info("===== Calling the method {}.{}()",
                method.getDeclaringClass().getSimpleName(), method.getName());
        return restClient.send(method, args);
    }
}
```

* Implements `InvocationHandler`, which requires defining the `invoke` method.
* The `invoke` method is called every time a method on the proxy is invoked. It delegates the call to the `TinyRestClient`, passing along the method and its arguments.

The `TinyRestClient` handles the construction and sending of HTTP requests based on the annotations and method details.

```java
@Slf4j
public class TinyRestClient {
    private String baseUrl;
    private HttpClient httpClient;

    public TinyRestClient(String baseUrl, HttpClient httpClient) {
        this.baseUrl = baseUrl;
        this.httpClient = httpClient;
    }

    public Object send(Method method, Object[] args) throws IOException, InterruptedException {
        var url = baseUrl + buildUrl(method, args);
        var httpRequest = HttpRequest.newBuilder().uri(URI.create(url)).build();
        var httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return getResponse(method, httpResponse);
    }

    private String buildUrl(Method method, Object[] args) {
        // Simplified URL building logic
        return "/albums";
    }

    private Object getResponse(Method method, HttpResponse<String> httpResponse) {
        // Simplified response handling logic
        return httpResponse.body();
    }
}
```

* This class uses Java's `HttpClient` to send HTTP requests.
* The `send` method constructs a `HttpRequest` using details from the method's annotations, although simplified here to focus on the dynamic proxy mechanism.
* The response is returned as a simple string, demonstrating basic interaction with an API.

Running the example produces the following console output showcasing the API calls and responses. See the `App` class and `main` method above.

```
16:05:41.964 [main] INFO com.iluwatar.dynamicproxy.AlbumInvocationHandler -- ===== Calling the method AlbumService.readAlbums()
16:05:42.409 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=1, title=quidem molestiae enim, userId=1)
16:05:42.410 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=2, title=sunt qui excepturi placeat culpa, userId=1)
16:05:42.410 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=3, title=omnis laborum odio, userId=1)
16:05:42.410 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=4, title=non esse culpa molestiae omnis sed optio, userId=1)
16:05:42.410 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=5, title=eaque aut omnis a, userId=1)
16:05:42.410 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=6, title=natus impedit quibusdam illo est, userId=1)
16:05:42.410 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=7, title=quibusdam autem aliquid et et quia, userId=1)
16:05:42.410 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=8, title=qui fuga est a eum, userId=1)
16:05:42.410 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=9, title=saepe unde necessitatibus rem, userId=1)
16:05:42.410 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=10, title=distinctio laborum qui, userId=1)
16:05:42.410 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=11, title=quam nostrum impedit mollitia quod et dolor, userId=2)
16:05:42.410 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=12, title=consequatur autem doloribus natus consectetur, userId=2)
16:05:42.410 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=13, title=ab rerum non rerum consequatur ut ea unde, userId=2)
16:05:42.410 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=14, title=ducimus molestias eos animi atque nihil, userId=2)
16:05:42.410 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=15, title=ut pariatur rerum ipsum natus repellendus praesentium, userId=2)
16:05:42.410 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=16, title=voluptatem aut maxime inventore autem magnam atque repellat, userId=2)
16:05:42.410 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=17, title=aut minima voluptatem ut velit, userId=2)
16:05:42.410 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=18, title=nesciunt quia et doloremque, userId=2)
16:05:42.410 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=19, title=velit pariatur quaerat similique libero omnis quia, userId=2)
16:05:42.410 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=20, title=voluptas rerum iure ut enim, userId=2)
16:05:42.410 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=21, title=repudiandae voluptatem optio est consequatur rem in temporibus et, userId=3)
16:05:42.410 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=22, title=et rem non provident vel ut, userId=3)
16:05:42.410 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=23, title=incidunt quisquam hic adipisci sequi, userId=3)
16:05:42.410 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=24, title=dolores ut et facere placeat, userId=3)
16:05:42.410 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=25, title=vero maxime id possimus sunt neque et consequatur, userId=3)
16:05:42.410 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=26, title=quibusdam saepe ipsa vel harum, userId=3)
16:05:42.410 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=27, title=id non nostrum expedita, userId=3)
16:05:42.410 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=28, title=omnis neque exercitationem sed dolor atque maxime aut cum, userId=3)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=29, title=inventore ut quasi magnam itaque est fugit, userId=3)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=30, title=tempora assumenda et similique odit distinctio error, userId=3)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=31, title=adipisci laborum fuga laboriosam, userId=4)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=32, title=reiciendis dolores a ut qui debitis non quo labore, userId=4)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=33, title=iste eos nostrum, userId=4)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=34, title=cumque voluptatibus rerum architecto blanditiis, userId=4)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=35, title=et impedit nisi quae magni necessitatibus sed aut pariatur, userId=4)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=36, title=nihil cupiditate voluptate neque, userId=4)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=37, title=est placeat dicta ut nisi rerum iste, userId=4)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=38, title=unde a sequi id, userId=4)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=39, title=ratione porro illum labore eum aperiam sed, userId=4)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=40, title=voluptas neque et sint aut quo odit, userId=4)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=41, title=ea voluptates maiores eos accusantium officiis tempore mollitia consequatur, userId=5)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=42, title=tenetur explicabo ea, userId=5)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=43, title=aperiam doloremque nihil, userId=5)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=44, title=sapiente cum numquam officia consequatur vel natus quos suscipit, userId=5)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=45, title=tenetur quos ea unde est enim corrupti qui, userId=5)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=46, title=molestiae voluptate non, userId=5)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=47, title=temporibus molestiae aut, userId=5)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=48, title=modi consequatur culpa aut quam soluta alias perspiciatis laudantium, userId=5)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=49, title=ut aut vero repudiandae voluptas ullam voluptas at consequatur, userId=5)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=50, title=sed qui sed quas sit ducimus dolor, userId=5)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=51, title=odit laboriosam sint quia cupiditate animi quis, userId=6)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=52, title=necessitatibus quas et sunt at voluptatem, userId=6)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=53, title=est vel sequi voluptatem nemo quam molestiae modi enim, userId=6)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=54, title=aut non illo amet perferendis, userId=6)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=55, title=qui culpa itaque omnis in nesciunt architecto error, userId=6)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=56, title=omnis qui maiores tempora officiis omnis rerum sed repellat, userId=6)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=57, title=libero excepturi voluptatem est architecto quae voluptatum officia tempora, userId=6)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=58, title=nulla illo consequatur aspernatur veritatis aut error delectus et, userId=6)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=59, title=eligendi similique provident nihil, userId=6)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=60, title=omnis mollitia sunt aliquid eum consequatur fugit minus laudantium, userId=6)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=61, title=delectus iusto et, userId=7)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=62, title=eos ea non recusandae iste ut quasi, userId=7)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=63, title=velit est quam, userId=7)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=64, title=autem voluptatem amet iure quae, userId=7)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=65, title=voluptates delectus iure iste qui, userId=7)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=66, title=velit sed quia dolor dolores delectus, userId=7)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=67, title=ad voluptas nostrum et nihil, userId=7)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=68, title=qui quasi nihil aut voluptatum sit dolore minima, userId=7)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=69, title=qui aut est, userId=7)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=70, title=et deleniti unde, userId=7)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=71, title=et vel corporis, userId=8)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=72, title=unde exercitationem ut, userId=8)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=73, title=quos omnis officia, userId=8)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=74, title=quia est eius vitae dolor, userId=8)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=75, title=aut quia expedita non, userId=8)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=76, title=dolorem magnam facere itaque ut reprehenderit tenetur corrupti, userId=8)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=77, title=cupiditate sapiente maiores iusto ducimus cum excepturi veritatis quia, userId=8)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=78, title=est minima eius possimus ea ratione velit et, userId=8)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=79, title=ipsa quae voluptas natus ut suscipit soluta quia quidem, userId=8)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=80, title=id nihil reprehenderit, userId=8)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=81, title=quibusdam sapiente et, userId=9)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=82, title=recusandae consequatur vel amet unde, userId=9)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=83, title=aperiam odio fugiat, userId=9)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=84, title=est et at eos expedita, userId=9)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=85, title=qui voluptatem consequatur aut ab quis temporibus praesentium, userId=9)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=86, title=eligendi mollitia alias aspernatur vel ut iusto, userId=9)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=87, title=aut aut architecto, userId=9)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=88, title=quas perspiciatis optio, userId=9)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=89, title=sit optio id voluptatem est eum et, userId=9)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=90, title=est vel dignissimos, userId=9)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=91, title=repellendus praesentium debitis officiis, userId=10)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=92, title=incidunt et et eligendi assumenda soluta quia recusandae, userId=10)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=93, title=nisi qui dolores perspiciatis, userId=10)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=94, title=quisquam a dolores et earum vitae, userId=10)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=95, title=consectetur vel rerum qui aperiam modi eos aspernatur ipsa, userId=10)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=96, title=unde et ut molestiae est molestias voluptatem sint, userId=10)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=97, title=est quod aut, userId=10)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=98, title=omnis quia possimus nesciunt deleniti assumenda sed autem, userId=10)
16:05:42.411 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=99, title=consectetur ut id impedit dolores sit ad ex aut, userId=10)
16:05:42.412 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=100, title=enim repellat iste, userId=10)
16:05:42.412 [main] INFO com.iluwatar.dynamicproxy.AlbumInvocationHandler -- ===== Calling the method AlbumService.readAlbum()
16:05:42.741 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=17, title=aut minima voluptatem ut velit, userId=2)
16:05:42.741 [main] INFO com.iluwatar.dynamicproxy.AlbumInvocationHandler -- ===== Calling the method AlbumService.createAlbum()
16:05:43.073 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=101, title=Big World, userId=3)
16:05:43.073 [main] INFO com.iluwatar.dynamicproxy.AlbumInvocationHandler -- ===== Calling the method AlbumService.updateAlbum()
16:05:43.220 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=17, title=Green Valley, userId=3)
16:05:43.220 [main] INFO com.iluwatar.dynamicproxy.AlbumInvocationHandler -- ===== Calling the method AlbumService.deleteAlbum()
16:05:43.357 [main] INFO com.iluwatar.dynamicproxy.App -- Album(id=null, title=null, userId=null)
```

## Applicability

Dynamic proxy should be used when you need to augment or enhance your current functionality without modifying your current code. Some examples of that usage could be:

* You want to intercept calls to methods of an object at runtime for handling purposes such as logging, transaction management, or security checks.
* You need to create a proxy object for one or more interfaces dynamically at runtime without coding it explicitly for each interface.
* You aim to simplify complex systems by decoupling the client and the real object through a flexible proxy mechanism.

## Tutorials

* [Dynamic Proxies in Java (CodeGym)](https://codegym.cc/groups/posts/208-dynamic-proxies)
* [Introduction To Java Dynamic Proxy (Xperti)](https://xperti.io/blogs/java-dynamic-proxies-introduction/)
* [Dynamic Proxies in Java (Baeldung)](https://www.baeldung.com/java-dynamic-proxies)
* [Intro To Java Dynamic Proxies (KapreSoft)](https://www.kapresoft.com/java/2023/12/27/intro-to-java-proxies.html)
* [Exploring the Depths of Dynamic Proxy in Java: A Comprehensive Guide (Medium)](https://naveen-metta.medium.com/exploring-the-depths-of-dynamic-proxy-in-java-a-comprehensive-guide-f34fb45b38a3)

## Known uses

Many frameworks and libraries use dynamic proxy to implement their functionalities:

* Java's `java.lang.reflect.Proxy` class is a built-in dynamic proxy mechanism.
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
* [Dynamic Proxy Classes - Oracle](https://docs.oracle.com/javase/8/docs/technotes/guides/reflection/proxy.html)
