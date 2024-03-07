---
title: Dynamic Proxy
category: Structural
language: en
tag:
- Generic
---

## Intent
Dynamic Proxy is a Java mechanism that allows developers to create a proxy
instance for interfaces at runtime. It is primarily used for intercepting
method calls, enabling developers to add additional processing around the
actual method invocation.

## Explanation

### Programmatic Example
```java
public class App {

  static final String REST_API_URL = "https://jsonplaceholder.typicode.com";

  private AlbumService albumServiceProxy;

  /**
   * Create the Dynamic Proxy linked to the AlbumService interface and to the AlbumInvocationHandler.
   */
  public void createDynamicProxy() {
    AlbumInvocationHandler albumInvocationHandler = new AlbumInvocationHandler(REST_API_URL);

    albumServiceProxy = (AlbumService) Proxy.newProxyInstance(
        App.class.getClassLoader(), new Class<?>[]{AlbumService.class}, albumInvocationHandler);
  }

  /**
   * Call the methods of the Dynamic Proxy, in other words, the AlbumService interface's methods.
   */
  public void callMethods() {
    int albumId = 17;
    int userId = 3;

    var albums = albumServiceProxy.readAlbums();
    albums.forEach(System.out::println);

    var album = albumServiceProxy.readAlbum(albumId);
    System.out.println(album);

    var newAlbum = albumServiceProxy.createAlbum(Album.builder()
        .title("Big World").userId(userId).build());
    System.out.println(newAlbum);

    var editAlbum = albumServiceProxy.updateAlbum(albumId, Album.builder()
        .title("Green Valley").userId(userId).build());
    System.out.println(editAlbum);

    var removedAlbum = albumServiceProxy.deleteAlbum(albumId);
    System.out.println(removedAlbum);
  }

  /**
   * Application entry point.
   *
   * @param args External arguments to be passed. Optional.
   */
  public static void main(String[] args) {
    App app = new App();
    app.createDynamicProxy();
    app.callMethods();
  }

}
```

```java
public interface AlbumService {

  /**
   * Get a list of albums from an endpoint.
   *
   * @return List of albums' data.
   */
  @Get("/albums")
  List<Album> readAlbums();

  /**
   * Get a specific album from an endpoint.
   *
   * @param albumId Album's id to search for.
   * @return Album's data.
   */
  @Get("/albums/{albumId}")
  Album readAlbum(@Path("albumId") Integer albumId);

  /**
   * Creates a new album.
   *
   * @param album Album's data to be created.
   * @return New album's data.
   */
  @Post("/albums")
  Album createAlbum(@Body Album album);

  /**
   * Updates an existing album.
   *
   * @param albumId Album's id to be modified.
   * @param album   New album's data.
   * @return Updated album's data.
   */
  @Put("/albums/{albumId}")
  Album updateAlbum(@Path("albumId") Integer albumId, @Body Album album);

  /**
   * Deletes an album.
   *
   * @param albumId Album's id to be deleted.
   * @return Empty album.
   */
  @Delete("/albums/{albumId}")
  Album deleteAlbum(@Path("albumId") Integer albumId);

}
```

```java
public class AlbumInvocationHandler implements InvocationHandler {

  private TinyRestClient restClient;

  /**
   * Class constructor. It instantiates a TinyRestClient object.
   *
   * @param baseUrl Root url for endpoints.
   */
  public AlbumInvocationHandler(String baseUrl) {
    this.restClient = new TinyRestClient(baseUrl);
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

    System.out.println();
    System.out.println("Calling the method " + method.getDeclaringClass().getSimpleName()
        + "." + method.getName() + "()");
    System.out.println("-".repeat(50));

    return restClient.send(method, args);
  }

}
```

```java
```


## Class diagram

![Class diagram](./etc/dynamic-proxy-classes.png)

## Applicability

## Tutorials

## Known uses

## Consequences

## Related patterns
Proxy

## Credits
