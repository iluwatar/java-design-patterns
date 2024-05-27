---
title: Server Session
category: Resource management
language: en
tag:
    - Client-server
    - Cookies
    - Session management 
    - State tracking
    - Web development
---

## Also known as

* Server-Side Session Management

## Intent

Manage user session data on the server-side to maintain state across multiple client requests.

## Explanation

Real-world example

> Imagine a hotel where each guest is given a unique room key card upon check-in. This key card stores the guest's personal preferences, such as preferred room temperature, wake-up call times, and minibar choices. Whenever the guest interacts with hotel services, such as ordering room service or accessing the gym, the system retrieves their preferences using the information on the key card. The hotel’s central server maintains these preferences, ensuring consistent and personalized service throughout the guest's stay. Similarly, the Server Session design pattern manages user data on the server, providing a seamless experience across multiple interactions within a web application. 

In plain words

> The Server Session design pattern manages and stores user session data on the server to maintain state across multiple client requests in web applications.

Wikipedia says

> A session token is a unique identifier that is generated and sent from a server to a client to identify the current interaction session. The client usually stores and sends the token as an HTTP cookie and/or sends it as a parameter in GET or POST queries. The reason to use session tokens is that the client only has to handle the identifier—all session data is stored on the server (usually in a database, to which the client does not have direct access) linked to that identifier.

**Programmatic Example**

The Server Session design pattern is a behavioral design pattern that assigns the responsibility of storing session data on the server side. This pattern is particularly useful in the context of stateless protocols like HTTP where all requests are isolated events independent of previous requests.

In this pattern, when a user logs in, a session identifier is created and stored for future requests in a list. When a user logs out, the session identifier is deleted from the list along with the appropriate user session data.

Let's take a look at a programmatic example of the Server Session design pattern.

The `main` application starts a server and assigns handlers to manage login and logout requests. It also starts a background task to check for expired sessions.

```java
public class App {

  private static Map<String, Integer> sessions = new HashMap<>();
  private static Map<String, Instant> sessionCreationTimes = new HashMap<>();
  private static final long SESSION_EXPIRATION_TIME = 10000;

  public static void main(String[] args) throws IOException {
    HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

    server.createContext("/login", new LoginHandler(sessions, sessionCreationTimes));
    server.createContext("/logout", new LogoutHandler(sessions, sessionCreationTimes));

    server.start();

    sessionExpirationTask();
  }

  private static void sessionExpirationTask() {
    new Thread(() -> {
      while (true) {
        try {
          Thread.sleep(SESSION_EXPIRATION_TIME);
          Instant currentTime = Instant.now();
          synchronized (sessions) {
            synchronized (sessionCreationTimes) {
              Iterator<Map.Entry<String, Instant>> iterator =
                  sessionCreationTimes.entrySet().iterator();
              while (iterator.hasNext()) {
                Map.Entry<String, Instant> entry = iterator.next();
                if (entry.getValue().plusMillis(SESSION_EXPIRATION_TIME).isBefore(currentTime)) {
                  sessions.remove(entry.getKey());
                  iterator.remove();
                }
              }
            }
          }
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    }).start();
  }
}
```

The LoginHandler is responsible for handling login requests. When a user logs in, a session identifier is created and stored for future requests in a list.

```java
public class LoginHandler {

  private Map<String, Integer> sessions;
  private Map<String, Instant> sessionCreationTimes;

  public LoginHandler(Map<String, Integer> sessions, Map<String, Instant> sessionCreationTimes) {
    this.sessions = sessions;
    this.sessionCreationTimes = sessionCreationTimes;
  }

  public void handle(HttpExchange exchange) {
    // Implementation of the handle method
  }
}
```

The LogoutHandler is responsible for handling logout requests. When a user logs out, the session identifier is deleted from the list along with the appropriate user session data.

```java
public class LogoutHandler {

  private Map<String, Integer> sessions;
  private Map<String, Instant> sessionCreationTimes;

  public LogoutHandler(Map<String, Integer> sessions, Map<String, Instant> sessionCreationTimes) {
    this.sessions = sessions;
    this.sessionCreationTimes = sessionCreationTimes;
  }

  public void handle(HttpExchange exchange) {
    // Implementation of the handle method
  }
}
```

Console output for starting the `App` class's `main` method:

```
12:09:50.998 [Thread-1] INFO com.iluwatar.sessionserver.App -- Session expiration checker started...
12:09:50.998 [main] INFO com.iluwatar.sessionserver.App -- Server started. Listening on port 8080...
```

This is a basic example of the Server Session design pattern. The actual implementation of the `handle` methods in the `LoginHandler` and `LogoutHandler` classes would depend on the specific requirements of your application.

## Applicability

* Use when building web applications that require maintaining user state information across multiple requests.
* Suitable for applications needing to track user interactions, preferences, or authentication state.
* Ideal for scenarios where client-side storage is insecure or insufficient.

## Known Uses

* Java EE applications using HttpSession for session management.
* Spring Framework's `@SessionAttributes` for handling user session data.
* Apache Tomcat's session management mechanism.

## Consequences

Benefits:

* Simplifies client-side logic by offloading state management to the server.
* Enhances security by storing sensitive information server-side.
* Supports complex state management scenarios like multistep forms or shopping carts.

Trade-offs:

* Increases server memory usage due to storage of session data.
* Requires session management logic to handle session timeouts and data persistence.
* Potential scalability issues with high user concurrency.

## Related Patterns

* [State](https://java-design-patterns.com/patterns/state/): Manages state-specific behavior, which can be utilized within session management to handle different user states.
* [Proxy](https://java-design-patterns.com/patterns/proxy/): Can be used to add a layer of control over session data access.
* [Singleton](https://java-design-patterns.com/patterns/singleton/): Often used to create a single instance of a session manager.

## Credits

* [Core J2EE Patterns: Best Practices and Design Strategies](https://amzn.to/4cAbDap)
* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
