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
package com.iluwatar.business.delegate

import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

// ABOUTME: Tests for the BusinessDelegate class verifying proper service delegation.
// ABOUTME: Uses MockK to verify interactions between client, delegate, and services.

/**
 * Tests for the [BusinessDelegate]
 */
class BusinessDelegateTest {
    private lateinit var netflixService: NetflixService
    private lateinit var youTubeService: YouTubeService
    private lateinit var businessDelegate: BusinessDelegate

    /**
     * This method sets up the instance variables of this test class. It is executed before the
     * execution of every test.
     */
    @BeforeEach
    fun setup() {
        netflixService = spyk(NetflixService())
        youTubeService = spyk(YouTubeService())

        val businessLookup = spyk(BusinessLookup())
        businessLookup.netflixService = netflixService
        businessLookup.youTubeService = youTubeService

        businessDelegate = spyk(BusinessDelegate())
        businessDelegate.lookupService = businessLookup
    }

    /**
     * In this example the client ([MobileClient]) utilizes a business delegate ([BusinessDelegate])
     * to execute a task. The Business Delegate then selects the appropriate service and makes the
     * service call.
     */
    @Test
    fun testBusinessDelegate() {
        // setup a client object
        val client = MobileClient(businessDelegate)

        // action
        client.playbackMovie("Die hard")

        // verifying that the businessDelegate was used by client during playbackMovie() method.
        verify { businessDelegate.playbackMovie(any()) }
        verify { netflixService.doProcessing() }

        // action
        client.playbackMovie("Maradona")

        // verifying that the businessDelegate was used by client during doTask() method.
        verify(exactly = 2) { businessDelegate.playbackMovie(any()) }
        verify { youTubeService.doProcessing() }
    }
}