/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.dynamicproxy;

import java.lang.reflect.Proxy;
import java.net.http.HttpClient;
import lombok.extern.slf4j.Slf4j;

/**
 * Application to demonstrate the Dynamic Proxy pattern. This application allow us to hit the public
 * fake API https://jsonplaceholder.typicode.com for the resource Album through an interface.
 * The call to Proxy.newProxyInstance creates a new dynamic proxy for the AlbumService interface and
 * sets the AlbumInvocationHandler class as the handler to intercept all the interface's methods.
 * Everytime that we call an AlbumService's method, the handler's method "invoke" will be call
 * automatically, and it will pass all the method's metadata and arguments to other specialized
 * class - TinyRestClient - to prepare the Rest API call accordingly.
 * In this demo, the Dynamic Proxy pattern help us to run business logic through interfaces without
 * an explicit implementation of the interfaces and supported on the Java Reflection approach.
 */
@Slf4j
public class App {

  static final String REST_API_URL = "https://jsonplaceholder.typicode.com";

  private String baseUrl;
  private HttpClient httpClient;
  private AlbumService albumServiceProxy;

  /**
   * Class constructor.
   *
   * @param baseUrl    Root url for endpoints.
   * @param httpClient Handle the http communication.
   */
  public App(String baseUrl, HttpClient httpClient) {
    this.baseUrl = baseUrl;
    this.httpClient = httpClient;
  }

  /**
   * Application entry point.
   *
   * @param args External arguments to be passed. Optional.
   */
  public static void main(String[] args) {
    App app = new App(App.REST_API_URL, HttpClient.newHttpClient());
    app.createDynamicProxy();
    app.callMethods();
  }

  /**
   * Create the Dynamic Proxy linked to the AlbumService interface and to the AlbumInvocationHandler.
   */
  public void createDynamicProxy() {
    AlbumInvocationHandler albumInvocationHandler = new AlbumInvocationHandler(baseUrl, httpClient);

    albumServiceProxy = (AlbumService) Proxy.newProxyInstance(
        App.class.getClassLoader(), new Class<?>[]{AlbumService.class}, albumInvocationHandler);
  }

  /**
   * Call the methods of the Dynamic Proxy, in other words, the AlbumService interface's methods
   * and receive the responses from the Rest API.
   */
  public void callMethods() {
    int albumId = 17;
    int userId = 3;

    var albums = albumServiceProxy.readAlbums();
    albums.forEach(album -> LOGGER.info("{}", album));

    var album = albumServiceProxy.readAlbum(albumId);
    LOGGER.info("{}", album);

    var newAlbum = albumServiceProxy.createAlbum(Album.builder()
        .title("Big World").userId(userId).build());
    LOGGER.info("{}", newAlbum);

    var editAlbum = albumServiceProxy.updateAlbum(albumId, Album.builder()
        .title("Green Valley").userId(userId).build());
    LOGGER.info("{}", editAlbum);

    var removedAlbum = albumServiceProxy.deleteAlbum(albumId);
    LOGGER.info("{}", removedAlbum);
  }

}
