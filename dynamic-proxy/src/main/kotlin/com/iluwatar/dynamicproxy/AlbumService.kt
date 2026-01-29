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

// ABOUTME: Interface defining REST API endpoints for Album resource operations.
// ABOUTME: Methods are annotated with HTTP method annotations and processed via dynamic proxy.
package com.iluwatar.dynamicproxy

import com.iluwatar.dynamicproxy.tinyrestclient.annotation.Body
import com.iluwatar.dynamicproxy.tinyrestclient.annotation.Delete
import com.iluwatar.dynamicproxy.tinyrestclient.annotation.Get
import com.iluwatar.dynamicproxy.tinyrestclient.annotation.Path
import com.iluwatar.dynamicproxy.tinyrestclient.annotation.Post
import com.iluwatar.dynamicproxy.tinyrestclient.annotation.Put

/**
 * Every method in this interface is annotated with the necessary metadata to represent an endpoint
 * that we can call to communicate with a host server which is serving a resource by REST API. This
 * interface is focused on the resource Album.
 */
interface AlbumService {

    /**
     * Get a list of albums from an endpoint.
     *
     * @return List of albums' data.
     */
    @Get("/albums")
    fun readAlbums(): List<Album>

    /**
     * Get a specific album from an endpoint.
     *
     * @param albumId Album's id to search for.
     * @return Album's data.
     */
    @Get("/albums/{albumId}")
    fun readAlbum(@Path("albumId") albumId: Int): Album

    /**
     * Creates a new album.
     *
     * @param album Album's data to be created.
     * @return New album's data.
     */
    @Post("/albums")
    fun createAlbum(@Body album: Album): Album

    /**
     * Updates an existing album.
     *
     * @param albumId Album's id to be modified.
     * @param album New album's data.
     * @return Updated album's data.
     */
    @Put("/albums/{albumId}")
    fun updateAlbum(@Path("albumId") albumId: Int, @Body album: Album): Album

    /**
     * Deletes an album.
     *
     * @param albumId Album's id to be deleted.
     * @return Empty album.
     */
    @Delete("/albums/{albumId}")
    fun deleteAlbum(@Path("albumId") albumId: Int): Album
}
