---
title: Partial Response
category: Behavioral
language: en
tag:
    - API design
    - Asynchronous
    - Client-server
    - Decoupling
    - Performance
    - Scalability
    - Web development
---

## Also known as

* Incremental Response
* Partial Result

## Intent

To enable an application to return a partial response to a client, improving perceived performance and enabling the client to start processing parts of the data before the entire response is available.

## Explanation

Real-world example

> Imagine a restaurant where a customer orders a multi-course meal. Instead of waiting until all courses are prepared before serving, the restaurant brings out each dish as soon as it's ready. This allows the customer to start enjoying the meal without delay, improves the dining experience, and optimizes the kitchen's workflow by letting them prepare and serve courses incrementally. Similarly, in software, the Partial Response design pattern delivers portions of data as they become available, allowing the client to begin processing immediately and improving overall performance and responsiveness.

In plain words

> The Partial Response design pattern allows a system to send portions of data to the client as they become available, enabling the client to start processing the data before the complete response is received.

**Programmatic Example**

The Partial Response design pattern allows clients to specify which fields of a resource they need. This pattern is useful for reducing the amount of data transferred over the network and allowing clients to start processing data sooner.

The programmatic example shows a simple video streaming application.

`Video` class represents a video object with several fields.

```java
public class Video {
    private String id;
    private String title;
    private String description;
    private String url;

    // Getters and setters...
}
```

`FieldJsonMapper` utility class converts video objects to JSON, including only the requested fields. Method `mapFields` takes a `Video` object and a set of field names. It creates a JSON object including only the specified fields. The `ObjectMapper` from Jackson library is used to build the JSON object.

```java
public class FieldJsonMapper {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static ObjectNode mapFields(Video video, Set<String> fields) {
        ObjectNode node = mapper.createObjectNode();

        if (fields.contains("id")) {
            node.put("id", video.getId());
        }
        if (fields.contains("title")) {
            node.put("title", video.getTitle());
        }
        if (fields.contains("description")) {
            node.put("description", video.getDescription());
        }
        if (fields.contains("url")) {
            node.put("url", video.getUrl());
        }

        return node;
    }
}
```

`VideoResource` class handles HTTP requests and returns only the requested fields of the video data.

- The `VideoResource` class is a RESTful resource handling HTTP GET requests.
- The `getVideo` method fetches a `Video` by its ID and processes the `fields` query parameter.
- It splits the `fields` parameter into a set of field names, uses `FieldJsonMapper` to include only those fields in the response, and returns the partial JSON response.

```java
@Path("/videos")
public class VideoResource {
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVideo(@PathParam("id") String id, @QueryParam("fields") String fieldsParam) {
        Video video = findVideoById(id); // Assume this method fetches the video by ID

        Set<String> fields = new HashSet<>(Arrays.asList(fieldsParam.split(",")));
        ObjectNode responseNode = FieldJsonMapper.mapFields(video, fields);

        return Response.ok(responseNode.toString()).build();
    }

    private Video findVideoById(String id) {
        // Dummy data for demonstration purposes
        Video video = new Video();
        video.setId(id);
        video.setTitle("Sample Video");
        video.setDescription("This is a sample video.");
        video.setUrl("http://example.com/sample-video");

        return video;
    }
}
```

`App` class initializes the web server and registers the `VideoResource`.

- The `App` class sets up the server using Jersey.
- It registers the `VideoResource` class, which will handle incoming HTTP requests.
- The server listens on `http://localhost:8080/`.

```java
public class App {
    public static void main(String[] args) {
        ResourceConfig config = new ResourceConfig();
        config.register(VideoResource.class);
        SimpleContainerFactory.create("http://localhost:8080/", config);
    }
}
```

To summarize, in this example:

- The `Video` class defines the video data structure.
- The `FieldJsonMapper` class helps create JSON responses including only the requested fields.
- The `VideoResource` class processes client requests, fetching the necessary video data and returning partial responses based on the specified fields.
- The `App` class configures and starts the web server.

By implementing the Partial Response design pattern, clients can request only the necessary data, enhancing performance and reducing bandwidth usage.

## Applicability

Use the Partial Response pattern when

* When the response data is large or takes a long time to process and transfer.
* When it’s beneficial for the client to begin processing the data as it arrives rather than waiting for the complete response.
* In APIs where different clients might need different subsets of data, allowing them to specify what they need.

## Known Uses

* RESTful APIs allowing clients to specify fields they want using query parameters.
* Streaming large datasets where initial parts of the data can be sent immediately (e.g., video streaming).
* GraphQL queries where clients can request only specific fields to be returned.

## Consequences

Benefits:

* Improved Performance: Reduces wait time for the client by allowing it to start processing data as soon as it begins to arrive.
* Resource Optimization: Decreases server load and bandwidth usage by sending only the required data.
* Scalability: Enhances system scalability by handling large datasets more efficiently and reducing the likelihood of timeouts.

Trade-offs:

* Complexity: Increases the complexity of both client and server implementations to handle partial responses properly.
* Error Handling: May complicate error handling and recovery if only parts of the data are received correctly.
* State Management: Requires careful management of state, especially if the partial responses are to be processed incrementally.

## Related Patterns

* Asynchronous Messaging: Often used together with asynchronous messaging patterns to handle partial responses without blocking the client.
* [Caching](https://java-design-patterns.com/patterns/caching/): Can be combined with caching patterns to store partial responses and avoid redundant data transfers.
* [Proxy](https://java-design-patterns.com/patterns/proxy/): The proxy can intercept requests and manage partial responses, providing a buffer between the client and server.

## Credits

* [Building Microservices](https://amzn.to/3UACtrU)
* [Designing Data-Intensive Applications: The Big Ideas Behind Reliable, Scalable, and Maintainable Systems](https://amzn.to/4dKEwBa)
* [RESTful Web APIs: Services for a Changing World](https://amzn.to/3wG4fu3)
