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
// ABOUTME: Main entry point for the idempotent-consumer Spring Boot application.
// ABOUTME: Demonstrates the Idempotent Consumer pattern ensuring exactly-once message processing.
package com.iluwatar.idempotentconsumer

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import java.util.UUID

private val logger = KotlinLogging.logger {}

/**
 * The main entry point for the idempotent-consumer application. This application demonstrates the
 * use of the Idempotent Consumer pattern which ensures that a message is processed exactly once in
 * scenarios where the same message can be delivered multiple times.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Idempotence">Idempotence (Wikipedia)</a>
 * @see <a
 *     href="https://camel.apache.org/components/latest/eips/idempotentConsumer-eip.html">Idempotent
 *     Consumer Pattern (Apache Camel)</a>
 */
@SpringBootApplication
open class App {
    /**
     * The starting point of the CommandLineRunner where the main program is run.
     *
     * @param requestService idempotent request service
     * @param requestRepository request jpa repository
     */
    @Bean
    open fun run(
        requestService: RequestService,
        requestRepository: RequestRepository,
    ): CommandLineRunner =
        CommandLineRunner { _ ->
            var req = requestService.create(UUID.randomUUID())
            requestService.create(req.uuid!!)
            requestService.create(req.uuid!!)
            logger.info {
                "Nb of requests : ${requestRepository.count()}"
            } // 1, processRequest is idempotent
            req = requestService.start(req.uuid!!)
            try {
                req = requestService.start(req.uuid!!)
            } catch (ex: InvalidNextStateException) {
                logger.error { "Cannot start request twice!" }
            }
            req = requestService.complete(req.uuid!!)
            logger.info { "Request: $req" }
        }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(App::class.java, *args)
        }
    }
}
