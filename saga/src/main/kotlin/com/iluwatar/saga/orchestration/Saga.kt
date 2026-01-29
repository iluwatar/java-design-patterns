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

// ABOUTME: Saga representation for orchestration pattern.
// ABOUTME: Consists of chapters that are executed by an orchestrator.
package com.iluwatar.saga.orchestration

/**
 * Saga representation. Saga consists of chapters. Every ChoreographyChapter is executed by a
 * certain service.
 */
class Saga private constructor() {

    private val chapters: MutableList<Chapter> = mutableListOf()

    fun chapter(name: String): Saga {
        chapters.add(Chapter(name))
        return this
    }

    operator fun get(idx: Int): Chapter = chapters[idx]

    fun isPresent(idx: Int): Boolean = idx >= 0 && idx < chapters.size

    /** Result for saga. */
    enum class Result {
        FINISHED,
        ROLLBACK,
        CRASHED
    }

    /** Class represents chapter name. */
    data class Chapter(val name: String)

    companion object {
        fun create(): Saga = Saga()
    }
}
