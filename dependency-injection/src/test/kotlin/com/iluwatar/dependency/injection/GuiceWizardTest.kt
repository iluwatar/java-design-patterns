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

// ABOUTME: Tests for GuiceWizard that validates Guice-based dependency injection.
// ABOUTME: Verifies both direct constructor injection and Guice framework injection.
package com.iluwatar.dependency.injection

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.iluwatar.dependency.injection.utils.InMemoryAppender
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/** GuiceWizardTest */
internal class GuiceWizardTest {

    private lateinit var appender: InMemoryAppender

    @BeforeEach
    fun setUp() {
        appender = InMemoryAppender(Tobacco::class.java)
    }

    @AfterEach
    fun tearDown() {
        appender.stop()
    }

    /**
     * Test if the [GuiceWizard] smokes whatever instance of [Tobacco] is passed to him
     * through the constructor parameter
     */
    @Test
    fun testSmokeEveryThingThroughConstructor() {
        val tobaccos = listOf(OldTobyTobacco(), RivendellTobacco(), SecondBreakfastTobacco())

        // Verify if the wizard is smoking the correct tobacco ...
        tobaccos.forEach { tobacco ->
            val guiceWizard = GuiceWizard(tobacco)
            guiceWizard.smoke()
            val lastMessage = appender.getLastMessage()
            assertEquals("GuiceWizard smoking ${tobacco.javaClass.simpleName}", lastMessage)
        }

        // ... and nothing else is happening.
        assertEquals(tobaccos.size, appender.getLogSize())
    }

    /**
     * Test if the [GuiceWizard] smokes whatever instance of [Tobacco] is passed to him
     * through the Guice google inject framework
     */
    @Test
    fun testSmokeEveryThingThroughInjectionFramework() {
        val tobaccos = listOf(
            OldTobyTobacco::class.java,
            RivendellTobacco::class.java,
            SecondBreakfastTobacco::class.java
        )

        // Configure the tobacco in the injection framework ...
        // ... and create a new wizard with it
        // Verify if the wizard is smoking the correct tobacco ...
        tobaccos.forEach { tobaccoClass ->
            val injector = Guice.createInjector(object : AbstractModule() {
                override fun configure() {
                    bind(Tobacco::class.java).to(tobaccoClass)
                }
            })
            val guiceWizard = injector.getInstance(GuiceWizard::class.java)
            guiceWizard.smoke()
            val lastMessage = appender.getLastMessage()
            assertEquals("GuiceWizard smoking ${tobaccoClass.simpleName}", lastMessage)
        }

        // ... and nothing else is happening.
        assertEquals(tobaccos.size, appender.getLogSize())
    }
}
