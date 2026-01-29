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
// ABOUTME: JPA entity representing a request with UUID and status.
// ABOUTME: The status follows a state machine pattern: PENDING -> STARTED -> COMPLETED.
package com.iluwatar.idempotentconsumer

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.UUID

/**
 * The [Request] class represents a request with a unique UUID and a status. The status of a
 * request can be one of four values: PENDING, STARTED, COMPLETED, or INERROR.
 */
@Entity
open class Request(
    @Id
    open var uuid: UUID? = null,
    open var status: Status? = null,
) {
    enum class Status {
        PENDING,
        STARTED,
        COMPLETED,
    }

    constructor(uuid: UUID) : this(uuid, Status.PENDING)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Request) return false
        return uuid == other.uuid && status == other.status
    }

    override fun hashCode(): Int {
        var result = uuid?.hashCode() ?: 0
        result = 31 * result + (status?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String = "Request(uuid=$uuid, status=$status)"
}
