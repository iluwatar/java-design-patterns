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

/**
 * The Actor Model is a design pattern used to handle concurrency in a safe, scalable, and
 * message-driven way.
 *
 * <p>In the Actor Model: - An **Actor** is an independent unit that has its own state and behavior.
 * - Actors **communicate only through messages** — they do not share memory. - An **ActorSystem**
 * is responsible for creating, starting, and managing the lifecycle of actors. - Messages are
 * delivered asynchronously, and each actor processes them one at a time.
 *
 * <p>💡 Key benefits: - No shared memory = no need for complex thread-safety - Easy to scale with
 * many actors - Suitable for highly concurrent or distributed systems
 *
 * <p>🔍 This example demonstrates the Actor Model: - `ActorSystem` starts two actors: `srijan` and
 * `ansh`. - `ExampleActor` and `ExampleActor2` extend the `Actor` class and override the
 * `onReceive()` method to handle messages. - Actors communicate using `send()` to pass `Message`
 * objects that include the message content and sender's ID. - The actors process messages
 * **asynchronously in separate threads**, and we allow a short delay (`Thread.sleep`) to let them
 * run. - The system is shut down gracefully at the end.
 */
package com.iluwatar.actormodel;

public class App {
  public static void main(String[] args) throws InterruptedException {
    ActorSystem system = new ActorSystem();
    Actor srijan = new ExampleActor(system);
    Actor ansh = new ExampleActor2(system);

    system.startActor(srijan);
    system.startActor(ansh);
    ansh.send(new Message("Hello ansh", srijan.getActorId()));
    srijan.send(new Message("Hello srijan!", ansh.getActorId()));

    Thread.sleep(1000); // Give time for messages to process

    srijan.stop(); // Stop the actor gracefully
    ansh.stop();
    system.shutdown(); // Stop the actor system
  }
}
