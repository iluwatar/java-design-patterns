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
package com.iluwatar.publish.subscribe.model

// ABOUTME: Test class for Topic functionality including subscriber management.
// ABOUTME: Verifies topic creation, subscriber add/remove operations using reflection.

import com.iluwatar.publish.subscribe.subscriber.WeatherSubscriber
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Test

class TopicTest {

    companion object {
        private const val TOPIC_WEATHER = "WEATHER"
    }

    @Test
    fun testTopic() {
        val topic = Topic(TOPIC_WEATHER)
        assertEquals(TOPIC_WEATHER, topic.topicName)
    }

    @Test
    fun testSubscribing() {
        val topic = Topic(TOPIC_WEATHER)
        val sub = WeatherSubscriber()
        topic.addSubscriber(sub)

        val field = topic::class.java.getDeclaredField("subscribers")
        field.isAccessible = true
        val value = field.get(topic)
        assertInstanceOf(Set::class.java, value)

        @Suppress("UNCHECKED_CAST")
        val subscribers = value as Set<*>
        assertEquals(1, subscribers.size)

        topic.removeSubscriber(sub)
        assertEquals(0, subscribers.size)
    }
}
