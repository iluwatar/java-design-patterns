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
// ABOUTME: Unit tests for RequestService covering create, start, and complete operations.
// ABOUTME: Tests idempotency behavior and exception handling for invalid state transitions.
package com.iluwatar.idempotentconsumer

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Optional
import java.util.UUID

internal class RequestServiceTests {
    private lateinit var requestService: RequestService
    private lateinit var requestRepository: RequestRepository

    @BeforeEach
    fun setUp() {
        requestRepository = mockk()
        val requestStateMachine = RequestStateMachine()
        requestService = RequestService(requestRepository, requestStateMachine)
    }

    @Test
    fun createRequest_whenNotExists() {
        val uuid = UUID.randomUUID()
        val request = Request(uuid)
        every { requestRepository.findById(any<UUID>()) } returns Optional.empty()
        every { requestRepository.save(request) } returns request
        assertEquals(request, requestService.create(uuid))
        verify(exactly = 1) { requestRepository.findById(uuid) }
        verify(exactly = 1) { requestRepository.save(any()) }
    }

    @Test
    fun createRequest_whenExists() {
        val uuid = UUID.randomUUID()
        val request = Request(uuid)
        every { requestRepository.findById(any<UUID>()) } returns Optional.of(request)
        assertEquals(request, requestService.create(uuid))
        verify(exactly = 1) { requestRepository.findById(uuid) }
        verify(exactly = 0) { requestRepository.save(any()) }
    }

    @Test
    fun startRequest_whenNotExists_shouldThrowError() {
        val uuid = UUID.randomUUID()
        every { requestRepository.findById(any<UUID>()) } returns Optional.empty()
        assertThrows(RequestNotFoundException::class.java) { requestService.start(uuid) }
        verify(exactly = 1) { requestRepository.findById(uuid) }
        verify(exactly = 0) { requestRepository.save(any()) }
    }

    @Test
    fun startRequest_whenIsPending() {
        val uuid = UUID.randomUUID()
        val request = Request(uuid)
        val startedEntity = Request(uuid, Request.Status.STARTED)
        every { requestRepository.findById(any<UUID>()) } returns Optional.of(request)
        every { requestRepository.save(any()) } returns startedEntity
        assertEquals(startedEntity, requestService.start(uuid))
        verify(exactly = 1) { requestRepository.findById(uuid) }
        verify(exactly = 1) { requestRepository.save(startedEntity) }
    }

    @Test
    fun startRequest_whenIsStarted_shouldThrowError() {
        val uuid = UUID.randomUUID()
        val requestStarted = Request(uuid, Request.Status.STARTED)
        every { requestRepository.findById(any<UUID>()) } returns Optional.of(requestStarted)
        assertThrows(InvalidNextStateException::class.java) { requestService.start(uuid) }
        verify(exactly = 1) { requestRepository.findById(uuid) }
        verify(exactly = 0) { requestRepository.save(any()) }
    }

    @Test
    fun startRequest_whenIsCompleted_shouldThrowError() {
        val uuid = UUID.randomUUID()
        val requestStarted = Request(uuid, Request.Status.COMPLETED)
        every { requestRepository.findById(any<UUID>()) } returns Optional.of(requestStarted)
        assertThrows(InvalidNextStateException::class.java) { requestService.start(uuid) }
        verify(exactly = 1) { requestRepository.findById(uuid) }
        verify(exactly = 0) { requestRepository.save(any()) }
    }

    @Test
    fun completeRequest_whenStarted() {
        val uuid = UUID.randomUUID()
        val request = Request(uuid, Request.Status.STARTED)
        val completedEntity = Request(uuid, Request.Status.COMPLETED)
        every { requestRepository.findById(any<UUID>()) } returns Optional.of(request)
        every { requestRepository.save(any()) } returns completedEntity
        assertEquals(completedEntity, requestService.complete(uuid))
        verify(exactly = 1) { requestRepository.findById(uuid) }
        verify(exactly = 1) { requestRepository.save(completedEntity) }
    }

    @Test
    fun completeRequest_whenNotInprogress() {
        val uuid = UUID.randomUUID()
        val request = Request(uuid)
        every { requestRepository.findById(any<UUID>()) } returns Optional.of(request)
        assertThrows(InvalidNextStateException::class.java) { requestService.complete(uuid) }
        verify(exactly = 1) { requestRepository.findById(uuid) }
        verify(exactly = 0) { requestRepository.save(any()) }
    }
}
