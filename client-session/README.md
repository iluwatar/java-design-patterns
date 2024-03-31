---
title: Client Session
category: Behavioral
language: en
tags:
    - Session management
    - Web development
---

## Also known as

* User session

## Intent

The Client Session design pattern aims to maintain a user's state and data across multiple requests within a web application, ensuring a continuous and personalized user experience.

## Explanation

Real-World Example

> You're looking to create a data management app allowing users to send requests to the server to modify and make changes to data stored on their devices. These requests are small and the data is individual to each user, negating the need for a large scale database implementation. Using the client session pattern, you are able to handle multiple concurrent requests, load balancing clients across different servers with ease due to servers remaining stateless. You also remove the need to store session IDs on the server side due to clients providing all the information that a server needs to perform their process.

In Plain words

> Instead of storing information about the current client and the information being accessed on the server, it is maintained client side only. Client has to send session data with each request to the server and has to send an updated state back to the client, which is stored on the clients machine. The server doesn't have to store the client information. ([ref](https://dzone.com/articles/practical-php-patterns/practical-php-patterns-client))

**Programmatic Example**

Here is the sample code to describe the client-session pattern. In the below code we are first creating an instance of the Server. This server instance will then be used to get Session objects for two clients. As you can see from the code below the Session object can be used to store any relevant information that are required by the server to process the client request. These session objects will then be passed on with every Request to the server. The Request will have the Session object that stores the relevant client details along with the required data for processing the request. The session information in every request helps the server identify the client and process the request accordingly.

```java
public class App {

    public static void main(String[] args) {
        var server = new Server("localhost", 8080);
        var session1 = server.getSession("Session1");
        var session2 = server.getSession("Session2");
        var request1 = new Request("Data1", session1);
        var request2 = new Request("Data2", session2);
        server.process(request1);
        server.process(request2);
    }
}

@Data
@AllArgsConstructor
public class Session {

    /**
     * Session id.
     */
    private String id;

    /**
     * Client name.
     */
    private String clientName;

}

@Data
@AllArgsConstructor
public class Request {

    private String data;

    private Session session;

}
```

## Architecture Diagram

![alt text](./etc/session_state_pattern.png "Session State Pattern")

## Applicability

Use the client state pattern when:

* Web applications requiring user authentication and authorization.
* Applications needing to track user activities and preferences over multiple requests or visits.
* Systems where server resources need to be optimized by offloading state management to the client side.

## Known Uses

* E-commerce websites to track shopping cart contents across sessions.
* Online platforms that offer personalized content based on user preferences and history.
* Web applications requiring user login to access personalized or secured content.

## Consequences

Benefits:

* Improved server performance by reducing the need to store user state on the server.
* Enhanced user experience through personalized content and seamless navigation across different parts of the application.
* Flexibility in managing sessions through various client-side storage mechanisms (e.g., cookies, Web Storage API).

Trade-offs:

* Potential security risks if sensitive information is stored in client sessions without proper encryption and validation.
* Dependence on client-side capabilities and settings, such as cookie policies, which can vary across browsers and user configurations.
* Increased complexity in session management logic, especially in handling session expiration, renewal, and synchronization across multiple devices or tabs.

## Related Patterns

* Server Session: Often used in conjunction with the Client Session pattern to provide a balance between client-side efficiency and server-side control.
* [Singleton](https://java-design-patterns.com/patterns/singleton/): Ensuring a single instance of a user's session throughout the application.
* [State](https://java-design-patterns.com/patterns/state/): Managing state transitions in a session, such as authenticated, guest, or expired states.

## Credits

* [DZone - Practical PHP patterns](https://dzone.com/articles/practical-php-patterns/practical-php-patterns-client)
* [Client Session State Design Pattern - Ram N Java](https://www.youtube.com/watch?v=ycOSj9g41pc)
* [Professional Java for Web Applications](https://amzn.to/4aazY59)
* [Securing Web Applications with Spring Security](https://amzn.to/3PCCEA1)
