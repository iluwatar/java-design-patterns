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
package com.iluwatar.leaderelection.ring

import com.iluwatar.leaderelection.Instance
import com.iluwatar.leaderelection.Message
import com.iluwatar.leaderelection.MessageType

// ABOUTME: Entry point demonstrating the Ring leader election algorithm with 5 instances.
// ABOUTME: Creates instances, starts heartbeat, and simulates leader failure to trigger election.

/**
 * Example of how to use ring leader election. Initially 5 instances is created in the cloud
 * system, and the instance with ID 1 is set as leader. After the system is started stop the leader
 * instance, and the new leader will be elected.
 */
fun main() {
    val instanceMap = mutableMapOf<Int, Instance>()
    val messageManager = RingMessageManager(instanceMap)

    val instance1 = RingInstance(messageManager, 1, 1)
    val instance2 = RingInstance(messageManager, 2, 1)
    val instance3 = RingInstance(messageManager, 3, 1)
    val instance4 = RingInstance(messageManager, 4, 1)
    val instance5 = RingInstance(messageManager, 5, 1)

    instanceMap[1] = instance1
    instanceMap[2] = instance2
    instanceMap[3] = instance3
    instanceMap[4] = instance4
    instanceMap[5] = instance5

    instance2.onMessage(Message(MessageType.HEARTBEAT_INVOKE, ""))

    val thread1 = Thread(instance1)
    val thread2 = Thread(instance2)
    val thread3 = Thread(instance3)
    val thread4 = Thread(instance4)
    val thread5 = Thread(instance5)

    thread1.start()
    thread2.start()
    thread3.start()
    thread4.start()
    thread5.start()

    instance1.setAlive(false)
}
