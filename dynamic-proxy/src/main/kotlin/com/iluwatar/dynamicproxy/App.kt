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

// ABOUTME: Application demonstrating the Dynamic Proxy pattern with a REST API client.
// ABOUTME: Uses Java Proxy to create implementations of AlbumService that delegate to TinyRestClient.
package com.iluwatar.dynamicproxy

import io.github.oshai.kotlinlogging.KotlinLogging
import java.lang.reflect.Proxy
import java.net.http.HttpClient

private val logger = KotlinLogging.logger {}

/**
 * Application to demonstrate the Dynamic Proxy pattern. This application allows us to hit the public
 * fake API https://jsonplaceholder.typicode.com for the resource Album through an interface. The
 * call to Proxy.newProxyInstance creates a new dynamic proxy for the AlbumService interface and
 * sets the AlbumInvocationHandler class as the handler to intercept all the interface's methods.
 * Every time that we call an AlbumService's method, the handler's method "invoke" will be called
 * automatically, and it will pass all the method's metadata and arguments to other specialized
 * class - TinyRestClient - to prepare the REST API call accordingly. In this demo, the Dynamic
 * Proxy pattern helps us to run business logic through interfaces without an explicit implementation
 * of the interfaces and supported on the Java Reflection approach.
 *
 * @param baseUrl Root URL for endpoints.
 * @param httpClient Handle the HTTP communication.
 */
class App(
    private val baseUrl: String,
    private val httpClient: HttpClient
) {

    private lateinit var albumServiceProxy: AlbumService

    /**
     * Create the Dynamic Proxy linked to the AlbumService interface and to the
     * AlbumInvocationHandler.
     */
    fun createDynamicProxy() {
        val albumInvocationHandler = AlbumInvocationHandler(baseUrl, httpClient)

        albumServiceProxy = Proxy.newProxyInstance(
            App::class.java.classLoader,
            arrayOf(AlbumService::class.java),
            albumInvocationHandler
        ) as AlbumService
    }

    /**
     * Call the methods of the Dynamic Proxy, in other words, the AlbumService interface's methods and
     * receive the responses from the REST API.
     */
    fun callMethods() {
        val albumId = 17
        val userId = 3

        val albums = albumServiceProxy.readAlbums()
        albums.forEach { album -> logger.info { "$album" } }

        val album = albumServiceProxy.readAlbum(albumId)
        logger.info { "$album" }

        val newAlbum = albumServiceProxy.createAlbum(Album(title = "Big World", userId = userId))
        logger.info { "$newAlbum" }

        val editAlbum = albumServiceProxy.updateAlbum(
            albumId,
            Album(title = "Green Valley", userId = userId)
        )
        logger.info { "$editAlbum" }

        val removedAlbum = albumServiceProxy.deleteAlbum(albumId)
        logger.info { "$removedAlbum" }
    }

    companion object {
        const val REST_API_URL = "https://jsonplaceholder.typicode.com"
    }
}

/**
 * Application entry point.
 *
 * @param args External arguments to be passed. Optional.
 */
fun main(args: Array<String>?) {
    val app = App(App.REST_API_URL, HttpClient.newHttpClient())
    app.createDynamicProxy()
    app.callMethods()
}
