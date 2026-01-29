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
package com.iluwatar.publish.subscribe.subscriber

// ABOUTME: Comprehensive test class for subscriber behavior in the pub-sub pattern.
// ABOUTME: Tests multi-topic subscription, message filtering, unsubscription, and error handling.

import com.iluwatar.publish.subscribe.LoggerExtension
import com.iluwatar.publish.subscribe.model.Message
import com.iluwatar.publish.subscribe.model.Topic
import com.iluwatar.publish.subscribe.publisher.Publisher
import com.iluwatar.publish.subscribe.publisher.PublisherImpl
import io.github.oshai.kotlinlogging.KotlinLogging
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

class SubscriberTest {

    @JvmField
    @RegisterExtension
    val loggerExtension = LoggerExtension()

    companion object {
        private const val TOPIC_WEATHER = "WEATHER"
        private const val TOPIC_TEMPERATURE = "TEMPERATURE"
        private const val TOPIC_CUSTOMER_SUPPORT = "CUSTOMER_SUPPORT"
    }

    @Test
    fun testSubscribeToMultipleTopics() {
        val topicWeather = Topic(TOPIC_WEATHER)
        val topicTemperature = Topic(TOPIC_TEMPERATURE)
        val weatherSubscriber = WeatherSubscriber()

        topicWeather.addSubscriber(weatherSubscriber)
        topicTemperature.addSubscriber(weatherSubscriber)

        val publisher: Publisher = PublisherImpl()
        publisher.registerTopic(topicWeather)
        publisher.registerTopic(topicTemperature)

        publisher.publish(topicWeather, Message("earthquake"))
        publisher.publish(topicTemperature, Message("-2C"))

        waitForOutput()
        assertEquals(2, loggerExtension.getFormattedMessages().size)
    }

    @Test
    fun testOnlyReceiveSubscribedTopic() {
        val weatherTopic = Topic(TOPIC_WEATHER)
        val weatherSubscriber = WeatherSubscriber()
        weatherTopic.addSubscriber(weatherSubscriber)

        val customerSupportTopic = Topic(TOPIC_CUSTOMER_SUPPORT)
        val publisher: Publisher = PublisherImpl()
        publisher.registerTopic(weatherTopic)
        publisher.registerTopic(customerSupportTopic)

        publisher.publish(customerSupportTopic, Message("support@test.de"))

        waitForOutput()
        assertEquals(0, loggerExtension.getFormattedMessages().size)
    }

    @Test
    fun testMultipleSubscribersOnSameTopic() {
        val weatherTopic = Topic(TOPIC_WEATHER)
        val weatherSubscriber1 = WeatherSubscriber()
        weatherTopic.addSubscriber(weatherSubscriber1)

        val weatherSubscriber2 = WeatherSubscriber()
        weatherTopic.addSubscriber(weatherSubscriber2)

        val publisher: Publisher = PublisherImpl()
        publisher.registerTopic(weatherTopic)

        publisher.publish(weatherTopic, Message("tornado"))

        waitForOutput()
        assertEquals(2, loggerExtension.getFormattedMessages().size)
        assertEquals(
            "Weather Subscriber: ${weatherSubscriber1.hashCode()} issued message: tornado",
            getMessage(weatherSubscriber1.hashCode())
        )
        assertEquals(
            "Weather Subscriber: ${weatherSubscriber2.hashCode()} issued message: tornado",
            getMessage(weatherSubscriber2.hashCode())
        )
    }

    @Test
    fun testMultipleSubscribersOnDifferentTopics() {
        val weatherTopic = Topic(TOPIC_WEATHER)
        val weatherSubscriber = WeatherSubscriber()
        weatherTopic.addSubscriber(weatherSubscriber)

        val customerSupportTopic = Topic(TOPIC_CUSTOMER_SUPPORT)
        val customerSupportSubscriber = CustomerSupportSubscriber()
        customerSupportTopic.addSubscriber(customerSupportSubscriber)

        val publisher: Publisher = PublisherImpl()
        publisher.registerTopic(weatherTopic)
        publisher.registerTopic(customerSupportTopic)

        publisher.publish(weatherTopic, Message("flood"))
        publisher.publish(customerSupportTopic, Message("support@test.at"))

        waitForOutput()
        assertEquals(2, loggerExtension.getFormattedMessages().size)
        assertEquals(
            "Weather Subscriber: ${weatherSubscriber.hashCode()} issued message: flood",
            getMessage(weatherSubscriber.hashCode())
        )
        assertEquals(
            "Customer Support Subscriber: ${customerSupportSubscriber.hashCode()} sent the email to: support@test.at",
            getMessage(customerSupportSubscriber.hashCode())
        )
    }

    @Test
    fun testInvalidContentOnTopics() {
        val weatherTopic = Topic(TOPIC_WEATHER)
        val weatherSubscriber = WeatherSubscriber()
        weatherTopic.addSubscriber(weatherSubscriber)

        val customerSupportTopic = Topic(TOPIC_CUSTOMER_SUPPORT)
        val customerSupportSubscriber = CustomerSupportSubscriber()
        customerSupportTopic.addSubscriber(customerSupportSubscriber)

        val publisher: Publisher = PublisherImpl()
        publisher.registerTopic(weatherTopic)
        publisher.registerTopic(customerSupportTopic)

        publisher.publish(weatherTopic, Message(123))
        publisher.publish(customerSupportTopic, Message(34.56))

        waitForOutput()
        assertTrue(loggerExtension.getFormattedMessages().first().contains("Unknown content type"))
        assertTrue(loggerExtension.getFormattedMessages()[1].contains("Unknown content type"))
    }

    @Test
    fun testUnsubscribe() {
        val weatherTopic = Topic(TOPIC_WEATHER)
        val weatherSubscriber = WeatherSubscriber()
        weatherTopic.addSubscriber(weatherSubscriber)

        val publisher: Publisher = PublisherImpl()
        publisher.registerTopic(weatherTopic)

        publisher.publish(weatherTopic, Message("earthquake"))

        weatherTopic.removeSubscriber(weatherSubscriber)
        publisher.publish(weatherTopic, Message("tornado"))

        waitForOutput()
        assertEquals(1, loggerExtension.getFormattedMessages().size)
        assertTrue(loggerExtension.getFormattedMessages().first().contains("earthquake"))
        assertFalse(loggerExtension.getFormattedMessages().first().contains("tornado"))
    }

    private fun getMessage(subscriberHash: Int): String {
        val message = loggerExtension.getFormattedMessages()
            .firstOrNull { it.contains(subscriberHash.toString()) }
        assertTrue(message != null)
        return message!!
    }

    private fun waitForOutput() {
        try {
            TimeUnit.SECONDS.sleep(1)
        } catch (e: InterruptedException) {
            logger.error(e) { "Interrupted!" }
            Thread.currentThread().interrupt()
        }
    }
}
