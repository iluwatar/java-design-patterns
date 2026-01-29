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

// ABOUTME: Tests for VideoResource.
// ABOUTME: Verifies full and partial video detail retrieval, using MockK for the field mapper.

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/** Tests [VideoResource]. */
class VideoResourceTest {

    private val fieldJsonMapper: FieldJsonMapper = mockk()
    private lateinit var resource: VideoResource

    @BeforeEach
    fun setUp() {
        val videos = mapOf(
            1 to Video(1, "Avatar", 178, "epic science fiction film", "James Cameron", "English"),
            2 to Video(2, "Godzilla Resurgence", 120, "Action & drama movie|", "Hideaki Anno", "Japanese"),
            3 to Video(3, "Interstellar", 169, "Adventure & Sci-Fi", "Christopher Nolan", "English")
        )
        resource = VideoResource(fieldJsonMapper, videos)
    }

    @Test
    fun shouldGiveVideoDetailsById() {
        val actualDetails = resource.getDetails(1)

        val expectedDetails =
            """{"id": 1,"title": "Avatar","length": 178,"description": "epic science fiction film","director": "James Cameron","language": "English"}"""
        assertEquals(expectedDetails, actualDetails)
    }

    @Test
    fun shouldGiveSpecifiedFieldsInformationOfVideo() {
        val fields = arrayOf("id", "title", "length")

        val expectedDetails = """{"id": 1,"title": "Avatar","length": 178}"""
        every { fieldJsonMapper.toJson(any(), any()) } returns expectedDetails

        val actualFieldsDetails = resource.getDetails(2, *fields)

        assertEquals(expectedDetails, actualFieldsDetails)
    }

    @Test
    fun shouldAllSpecifiedFieldsInformationOfVideo() {
        val fields = arrayOf("id", "title", "length", "description", "director", "language")

        val expectedDetails =
            """{"id": 1,"title": "Avatar","length": 178,"description": "epic science fiction film","director": "James Cameron","language": "English"}"""
        every { fieldJsonMapper.toJson(any(), any()) } returns expectedDetails

        val actualFieldsDetails = resource.getDetails(1, *fields)

        assertEquals(expectedDetails, actualFieldsDetails)
    }
}
