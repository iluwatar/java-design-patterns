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
package com.iluwatar.publish.subscribe.publisher

// ABOUTME: Test class for Publisher implementation functionality.
// ABOUTME: Verifies topic registration, message publishing, and error handling for unregistered topics.

import com.iluwatar.publish.subscribe.LoggerExtension
import com.iluwatar.publish.subscribe.model.Message
import com.iluwatar.publish.subscribe.model.Topic
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class PublisherTest {

    @JvmField
    @RegisterExtension
    val loggerExtension = LoggerExtension()

    companion object {
        private const val TOPIC_WEATHER = "WEATHER"
        private const val TOPIC_CUSTOMER_SUPPORT = "CUSTOMER_SUPPORT"
    }

    @Test
    fun testRegisterTopic() {
        val topic = Topic(TOPIC_CUSTOMER_SUPPORT)
        val publisher: Publisher = PublisherImpl()
        publisher.registerTopic(topic)

        val field = publisher::class.java.getDeclaredField("topics")
        field.isAccessible = true
        val value = field.get(publisher)
        assertInstanceOf(Set::class.java, value)

        @Suppress("UNCHECKED_CAST")
        val topics = value as Set<*>
        assertEquals(1, topics.size)
    }

    @Test
    fun testPublish() {
        val topic = Topic(TOPIC_WEATHER)
        val publisher: Publisher = PublisherImpl()
        publisher.registerTopic(topic)

        val message = Message("weather")
        assertDoesNotThrow { publisher.publish(topic, message) }
    }

    @Test
    fun testPublishUnregisteredTopic() {
        val topic = Topic(TOPIC_WEATHER)
        val publisher: Publisher = PublisherImpl()
        publisher.registerTopic(topic)

        val topicUnregistered = Topic(TOPIC_CUSTOMER_SUPPORT)
        val message = Message("support")
        publisher.publish(topicUnregistered, message)
        assertEquals(
            "This topic is not registered: CUSTOMER_SUPPORT",
            loggerExtension.getFormattedMessages().first()
        )
    }
}
