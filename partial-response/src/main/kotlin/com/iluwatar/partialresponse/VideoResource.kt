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

package com.iluwatar.partialresponse

// ABOUTME: Resource class that serves video information, acting as the server in the demo.
// ABOUTME: Returns full or partial video details depending on the requested fields.

/**
 * The resource class which serves video information. This class acts as server in the demo.
 * It has all video details.
 *
 * @param fieldJsonMapper map object to json.
 * @param videos initialize resource with existing videos. Acts as database.
 */
data class VideoResource(
    val fieldJsonMapper: FieldJsonMapper,
    val videos: Map<Int, Video>
) {
    /**
     * Get Details.
     *
     * @param id video id
     * @param fields fields to get information about
     * @return full response if no fields specified else partial response for given field.
     */
    fun getDetails(id: Int, vararg fields: String): String {
        if (fields.isEmpty()) {
            return videos[id].toString()
        }
        return fieldJsonMapper.toJson(videos[id]!!, arrayOf(*fields))
    }
}
