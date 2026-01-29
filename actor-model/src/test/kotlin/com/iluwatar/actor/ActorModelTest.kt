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

// ABOUTME: Unit tests for the actor model pattern implementation.
// ABOUTME: Tests message passing between actors and the main application entry point.
package com.iluwatar.actor

import com.iluwatar.actormodel.ActorSystem
import com.iluwatar.actormodel.ExampleActor
import com.iluwatar.actormodel.ExampleActor2
import com.iluwatar.actormodel.Message
import com.iluwatar.actormodel.main
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ActorModelTest {

    @Test
    fun testMainMethod() {
        main()
    }

    @Test
    fun testMessagePassing() {
        val system = ActorSystem()

        val srijan = ExampleActor(system)
        val ansh = ExampleActor2(system)

        system.startActor(srijan)
        system.startActor(ansh)

        // Ansh receives a message from Srijan
        ansh.send(Message("Hello ansh", srijan.actorId))

        // Wait briefly to allow async processing
        Thread.sleep(200)

        // Check that ansh received the message
        assertTrue(
            ansh.receivedMessages.contains("Hello ansh"),
            "ansh should receive the message from Srijan"
        )
    }
}
