---
title: Leader/Followers
category: Concurrency
language: en
tag:
- Performance
---

## Intent
The Leader/Followers design pattern is a pattern used to coordinate a selection of 'workers'. It allows tasks to execute concurrently
with the Leader delegating tasks to the Follower threads for execution. It is a very common design pattern used in multithreaded
situations such as servers, and works to help prevent ambiguity around delegation of tasks.

## Explanation
Real-world Example
> The best real world example of Leader/Followers is a web server. Web servers have to be able to handle a multitude of incoming
> connections all at once. In a web server, the Leader/Followers pattern works by using the Leader to listen to incoming requests
> and accept connections. Once a connection is made to a client the Leader can then find a Follower thread to delegate the task
> to for execution and return to the client. This means that the Leader does not have to wait to finish execution before it can
> accept another incoming connection, and can focus on delegating tasks. This pattern is created to aid in concurrency of applicaitons,
> allowing for many connections to work simultaneously.

In plain words
> You can picture the Leader as a traffic controller that has to direct traffic from one lane into 25 lanes. As a car comes in,
> the Leader sends it down a road that isn't full or busy. This car can then have its request filled, or reach its destination.
> If the Leader had to drive each car down the lane itself, the line would pile up and progress would be slow. But as the Leader
> has Followers that can also drive the cars, the Leader can focus on making the line move quickly and ensuring traffic doesn't
> back up.

Wikipedia says
> A concurrency pattern are those types of design patterns that deal with the multi-threaded programming paradigm.

## Programmatic Example
This example shows Java code that sets up a Leader that listens for client requests on port 8080. Once a request is sent,
the leader will accept it and delegate it to a new Follower to execute. This means that the Leader can keep delegating,
and ensure requests are fulfilled timely. This is only pseudocode and the working code would require a more concrete implementation
of Leader and Followers.
```java
public class LeaderFollowerWebServer {

    public static void main(String[] args) throws IOException {
        int port = 8080; // the port that clients can reach the leader on
        int numFollowers = 5; // the amount of followers we can delegate tasks to

        ServerSocket serverSocket = new ServerSocket(port); // pseudocode for creating a socket to the server
        ExecutorService executorService = Executors.newFixedThreadPool(numFollowers); // pseudocode to start execution for Followers

        System.out.println("Web server started. Listening on port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            // Accept a new connection and assign it to a follower thread for processing
            executorService.execute(new Follower(clientSocket));
        }
    }
}

class Follower implements Runnable {
    private final Socket clientSocket;

    public Follower(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            // handle the client request, e.g., read and write data
            // this is where you would implement your request processing logic.
            // we will just close the socket
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```
## Class diagram
![Leader/Followers class diagram](./etc/leader-followers.png)

## Applicability
Use Leader-Followers pattern when

* You want to establish a concurrent application
* You want faster response times on heavy load
* You want an easily scalable program
* You want to load balance a program

## Consequences
Consequences involved with using the Leader/Followers pattern

* Implementing this pattern will increase complexity of the code
* If the leader is too slow at delegating processes, some Followers may not get to execute tasks leading to a waste of resources
* There is overhead with organising and maintaining a thread pool
* Debugging is more complex

## Real world examples

* ACE Thread Pool Reactor framework
* JAWS
* Real-time CORBA

## Credits

* Douglas C. Schmidt and Carlos O’Ryan - Leader/Followers