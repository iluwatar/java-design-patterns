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
package com.iluwatar.observer.generic

// ABOUTME: Abstract generic observable base class that manages a list of typed observers.
// ABOUTME: Provides thread-safe observer registration, removal, and notification via CopyOnWriteArrayList.

import java.util.concurrent.CopyOnWriteArrayList

/**
 * Generic observer inspired by Java Generics and Collection by Naftalin & Wadler.
 *
 * @param S Subject
 * @param O Observer
 * @param A Argument type
 */
abstract class Observable<S : Observable<S, O, A>, O : Observer<S, O, A>, A> {

    protected val observers: MutableList<O> = CopyOnWriteArrayList()

    fun addObserver(observer: O) {
        observers.add(observer)
    }

    fun removeObserver(observer: O) {
        observers.remove(observer)
    }

    /** Notify observers. */
    @Suppress("UNCHECKED_CAST")
    fun notifyObservers(argument: A) {
        for (observer in observers) {
            observer.update(this as S, argument)
        }
    }
}
