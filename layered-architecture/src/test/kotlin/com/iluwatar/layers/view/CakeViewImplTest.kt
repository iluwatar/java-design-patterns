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
// ABOUTME: Unit tests for CakeViewImpl using MockK for mocking and logback for log capture.
// ABOUTME: Verifies that render() logs cake information correctly.
package com.iluwatar.layers.view

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.AppenderBase
import dto.CakeInfo
import dto.CakeLayerInfo
import dto.CakeToppingInfo
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import service.CakeBakingService
import view.CakeViewImpl

/**
 * This class contains unit tests for the CakeViewImpl class. It tests the functionality of
 * rendering cakes using the CakeViewImpl class. It also tests the logging functionality of the
 * CakeViewImpl class.
 */
class CakeViewImplTest {
    private lateinit var appender: InMemoryAppender

    @BeforeEach
    fun setUp() {
        appender = InMemoryAppender(CakeViewImpl::class.java)
    }

    @AfterEach
    fun tearDown() {
        appender.stop()
    }

    /** Verify if the cake view renders the expected result. */
    @Test
    fun testRender() {
        val layers =
            listOf(
                CakeLayerInfo("layer1", 1000),
                CakeLayerInfo("layer2", 2000),
                CakeLayerInfo("layer3", 3000),
            )

        val cake = CakeInfo(CakeToppingInfo("topping", 1000), layers)
        val cakes = listOf(cake)

        val bakingService = mockk<CakeBakingService>()
        every { bakingService.getAllCakes() } returns cakes

        val cakeView = CakeViewImpl(bakingService)

        assertEquals(0, appender.getLogSize())

        cakeView.render()
        assertEquals(cake.toString(), appender.getLastMessage())
    }

    private class InMemoryAppender(
        clazz: Class<*>,
    ) : AppenderBase<ILoggingEvent>() {
        private val log = mutableListOf<ILoggingEvent>()

        init {
            (LoggerFactory.getLogger(clazz) as Logger).addAppender(this)
            start()
        }

        override fun append(eventObject: ILoggingEvent) {
            log.add(eventObject)
        }

        fun getLastMessage(): String = log[log.size - 1].formattedMessage

        fun getLogSize(): Int = log.size
    }
}
