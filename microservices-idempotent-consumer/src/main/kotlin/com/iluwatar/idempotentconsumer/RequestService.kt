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
// ABOUTME: Service layer for idempotent request operations using state machine transitions.
// ABOUTME: Provides create (idempotent), start, and complete operations on requests.
package com.iluwatar.idempotentconsumer

import org.springframework.stereotype.Service
import java.util.UUID

/**
 * This service is responsible for handling request operations including creation, start, and
 * completion of requests.
 */
@Service
open class RequestService(
    private val requestRepository: RequestRepository,
    private val requestStateMachine: RequestStateMachine,
) {
    /**
     * Creates a new Request or returns an existing one by its UUID. This operation is idempotent:
     * performing it once or several times successively leads to an equivalent result.
     *
     * @param uuid The unique identifier for the Request.
     * @return Return existing Request or save and return a new Request.
     */
    fun create(uuid: UUID): Request {
        val optReq = requestRepository.findById(uuid)
        return optReq.orElseGet { requestRepository.save(Request(uuid)) }
    }

    /**
     * Starts the Request assigned with the given UUID.
     *
     * @param uuid The unique identifier for the Request.
     * @return The started Request.
     * @throws RequestNotFoundException if a Request with the given UUID is not found.
     */
    fun start(uuid: UUID): Request {
        val optReq = requestRepository.findById(uuid)
        if (optReq.isEmpty) {
            throw RequestNotFoundException(uuid)
        }
        return requestRepository.save(requestStateMachine.next(optReq.get(), Request.Status.STARTED))
    }

    /**
     * Complete the Request assigned with the given UUID.
     *
     * @param uuid The unique identifier for the Request.
     * @return The completed Request.
     * @throws RequestNotFoundException if a Request with the given UUID is not found.
     */
    fun complete(uuid: UUID): Request {
        val optReq = requestRepository.findById(uuid)
        if (optReq.isEmpty) {
            throw RequestNotFoundException(uuid)
        }
        return requestRepository.save(requestStateMachine.next(optReq.get(), Request.Status.COMPLETED))
    }
}
