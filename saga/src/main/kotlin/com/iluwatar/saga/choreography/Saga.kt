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

// ABOUTME: Saga representation for choreography pattern.
// ABOUTME: Consists of chapters that are executed by services in a chain.
package com.iluwatar.saga.choreography

/**
 * Saga representation. Saga consists of chapters. Every ChoreographyChapter is executed a certain
 * service.
 */
class Saga private constructor() {

    private val chapters: MutableList<Chapter> = mutableListOf()
    private var pos: Int = 0
    internal var isForward: Boolean = true
        private set
    private var finished: Boolean = false

    /**
     * Get result of saga.
     *
     * @return result of saga [SagaResult]
     */
    val result: SagaResult
        get() = when {
            finished -> if (isForward) SagaResult.FINISHED else SagaResult.ROLLBACKED
            else -> SagaResult.PROGRESS
        }

    /**
     * Add chapter to saga.
     *
     * @param name chapter name
     * @return this
     */
    fun chapter(name: String): Saga {
        chapters.add(Chapter(name))
        return this
    }

    /**
     * Set value to last chapter.
     *
     * @param value invalue
     * @return this
     */
    fun setInValue(value: Any?): Saga {
        if (chapters.isEmpty()) {
            return this
        }
        chapters[chapters.size - 1].inValue = value
        return this
    }

    /**
     * Get value from current chapter.
     *
     * @return value
     */
    val currentValue: Any?
        get() = chapters[pos].inValue

    /**
     * Set value to current chapter.
     *
     * @param value to set
     */
    fun setCurrentValue(value: Any?) {
        chapters[pos].inValue = value
    }

    /**
     * Set status for current chapter.
     *
     * @param result to set
     */
    fun setCurrentStatus(result: ChapterResult) {
        chapters[pos].result = result
    }

    internal fun setFinished(finished: Boolean) {
        this.finished = finished
    }

    internal fun forward(): Int {
        return ++pos
    }

    internal fun back(): Int {
        isForward = false
        return --pos
    }

    internal val current: Chapter
        get() = chapters[pos]

    internal val isPresent: Boolean
        get() = pos >= 0 && pos < chapters.size

    internal val isCurrentSuccess: Boolean
        get() = chapters[pos].isSuccess

    /**
     * Class presents a chapter status and incoming parameters(incoming parameter transforms to
     * outcoming parameter).
     */
    class Chapter(val name: String) {
        var result: ChapterResult = ChapterResult.INIT
        var inValue: Any? = null

        /**
         * The result for chapter is good.
         *
         * @return true if is good otherwise bad
         */
        val isSuccess: Boolean
            get() = result == ChapterResult.SUCCESS
    }

    /** Result for chapter. */
    enum class ChapterResult {
        INIT,
        SUCCESS,
        ROLLBACK
    }

    /** Result for saga. */
    enum class SagaResult {
        PROGRESS,
        FINISHED,
        ROLLBACKED
    }

    override fun toString(): String {
        return "Saga{chapters=${chapters.toTypedArray().contentToString()}, pos=$pos, forward=$isForward}"
    }

    companion object {
        fun create(): Saga = Saga()
    }
}
