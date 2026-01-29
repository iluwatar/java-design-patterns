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
package com.iluwatar.publish.subscribe

// ABOUTME: Main entry point demonstrating the Publish-Subscribe design pattern.
// ABOUTME: Creates publishers, topics, and subscribers to show decoupled event-driven communication.

import com.iluwatar.publish.subscribe.model.Message
import com.iluwatar.publish.subscribe.model.Topic
import com.iluwatar.publish.subscribe.publisher.PublisherImpl
import com.iluwatar.publish.subscribe.subscriber.CustomerSupportSubscriber
import com.iluwatar.publish.subscribe.subscriber.DelayedWeatherSubscriber
import com.iluwatar.publish.subscribe.subscriber.WeatherSubscriber
import java.util.concurrent.TimeUnit

/**
 * The Publish and Subscribe pattern is a messaging paradigm used in software architecture with
 * several key points:
 * - Decoupling of publishers and subscribers: Publishers and subscribers operate independently,
 *   and there's no direct link between them. This enhances the scalability and modularity of
 *   applications.
 * - Event-driven communication: The pattern facilitates event-driven architectures by allowing
 *   publishers to broadcast events without concerning themselves with who receives the events.
 * - Dynamic subscription: Subscribers can dynamically choose to listen for specific events or
 *   messages they are interested in, often by subscribing to a particular topic or channel.
 * - Asynchronous processing: The pattern inherently supports asynchronous message processing,
 *   enabling efficient handling of events and improving application responsiveness.
 * - Scalability: By decoupling senders and receivers, the pattern can support a large number of
 *   publishers and subscribers, making it suitable for scalable systems.
 * - Flexibility and adaptability: New subscribers or publishers can be added to the system
 *   without significant changes to the existing components, making the system highly adaptable to
 *   evolving requirements.
 *
 * In this example we will create three topics WEATHER, TEMPERATURE and CUSTOMER_SUPPORT.
 * Then we will register those topics in the [PublisherImpl]. After that we will create two
 * [WeatherSubscriber]s, one [DelayedWeatherSubscriber] and two [CustomerSupportSubscriber].
 * The subscribers will subscribe to the relevant topics. One [WeatherSubscriber] will subscribe
 * to two topics (WEATHER, TEMPERATURE). [DelayedWeatherSubscriber] has a delay in message
 * processing. Now we can publish the three [Topic]s with different content in the [Message]s.
 * And we can observe the output in the log where, one [WeatherSubscriber] will output the message
 * with weather and the other [WeatherSubscriber] will output weather and temperature.
 * [CustomerSupportSubscriber]s will output the message with customer support email.
 * [DelayedWeatherSubscriber] has a delay in processing and will output the message at last.
 * Each subscriber is only listening to the subscribed topics.
 */
fun main() {
    val topicWeather = "WEATHER"
    val topicTemperature = "TEMPERATURE"
    val topicCustomerSupport = "CUSTOMER_SUPPORT"

    // 1. create the publisher.
    val publisher = PublisherImpl()

    // 2. define the topics and register on publisher
    val weatherTopic = Topic(topicWeather)
    publisher.registerTopic(weatherTopic)

    val temperatureTopic = Topic(topicTemperature)
    publisher.registerTopic(temperatureTopic)

    val supportTopic = Topic(topicCustomerSupport)
    publisher.registerTopic(supportTopic)

    // 3. Create the subscribers and subscribe to the relevant topics
    // weatherSub1 will subscribe to two topics WEATHER and TEMPERATURE.
    val weatherSub1 = WeatherSubscriber()
    weatherTopic.addSubscriber(weatherSub1)
    temperatureTopic.addSubscriber(weatherSub1)

    // weatherSub2 will subscribe to WEATHER topic
    val weatherSub2 = WeatherSubscriber()
    weatherTopic.addSubscriber(weatherSub2)

    // delayedWeatherSub will subscribe to WEATHER topic
    // NOTE :: DelayedWeatherSubscriber has a 0.2 sec delay of processing message.
    val delayedWeatherSub = DelayedWeatherSubscriber()
    weatherTopic.addSubscriber(delayedWeatherSub)

    // subscribe the customer support subscribers to the CUSTOMER_SUPPORT topic.
    val supportSub1 = CustomerSupportSubscriber()
    supportTopic.addSubscriber(supportSub1)
    val supportSub2 = CustomerSupportSubscriber()
    supportTopic.addSubscriber(supportSub2)

    // 4. publish message from each topic
    publisher.publish(weatherTopic, Message("earthquake"))
    publisher.publish(temperatureTopic, Message("23C"))
    publisher.publish(supportTopic, Message("support@test.de"))

    // 5. unregister subscriber from TEMPERATURE topic
    temperatureTopic.removeSubscriber(weatherSub1)

    // 6. publish message under TEMPERATURE topic
    publisher.publish(temperatureTopic, Message("0C"))

    /*
     * Finally, we wait for the subscribers to consume messages to check the output.
     * The output can change on each run, depending on how long the execution on each
     * subscriber would take
     * Expected behavior:
     * - weatherSub1 will consume earthquake and 23C
     * - weatherSub2 will consume earthquake
     * - delayedWeatherSub will take longer and consume earthquake
     * - supportSub1, supportSub2 will consume support@test.de
     * - the message 0C will not be consumed because weatherSub1 unsubscribed from TEMPERATURE topic
     */
    TimeUnit.SECONDS.sleep(2)
}
