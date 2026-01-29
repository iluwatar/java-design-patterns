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
package com.iluwatar.singleton

// ABOUTME: Entry point demonstrating various singleton implementation approaches in Kotlin.
// ABOUTME: Shows eager, lazy, enum-style, double-checked locking, holder idiom, and Bill Pugh patterns.

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * Singleton pattern ensures that the class can have only one existing instance per Java classloader
 * instance and provides global access to it.
 *
 * One of the risks of this pattern is that bugs resulting from setting a singleton up in a
 * distributed environment can be tricky to debug since it will work fine if you debug with a single
 * classloader. Additionally, these problems can crop up a while after the implementation of a
 * singleton, since they may start synchronous and only become async with time, so it may not be
 * clear why you are seeing certain changes in behavior.
 *
 * There are many ways to implement the Singleton. The first one is the eagerly initialized
 * instance in [IvoryTower]. Eager initialization implies that the implementation is thread
 * safe. If you can afford to give up control of the instantiation moment, then this implementation
 * will suit you fine.
 *
 * The other option to implement eagerly initialized Singleton is enum-based Singleton. The
 * example is found in [EnumIvoryTower]. At first glance, the code looks short and simple.
 * However, you should be aware of the downsides including committing to implementation strategy,
 * extending the enum class, serializability, and restrictions to coding. These are extensively
 * discussed in Stack Overflow:
 * http://programmers.stackexchange.com/questions/179386/what-are-the-downsides-of-implementing
 * -a-singleton-with-javas-enum
 *
 * [ThreadSafeLazyLoadedIvoryTower] is a Singleton implementation that is initialized on
 * demand. The downside is that it is very slow to access since the whole access method is
 * synchronized.
 *
 * Another Singleton implementation that is initialized on demand is found in
 * [ThreadSafeDoubleCheckLocking]. It is somewhat faster than [ThreadSafeLazyLoadedIvoryTower]
 * since it doesn't synchronize the whole access method but only the method internals on specific
 * conditions.
 *
 * Yet another way to implement thread-safe lazily initialized Singleton can be found in
 * [InitializingOnDemandHolderIdiom]. However, this implementation requires at least Java 8 API
 * level to work.
 */
fun main() {

    // eagerly initialized singleton
    val ivoryTower1 = IvoryTower.getInstance()
    val ivoryTower2 = IvoryTower.getInstance()
    logger.info { "ivoryTower1=$ivoryTower1" }
    logger.info { "ivoryTower2=$ivoryTower2" }

    // lazily initialized singleton
    val threadSafeIvoryTower1 = ThreadSafeLazyLoadedIvoryTower.getInstance()
    val threadSafeIvoryTower2 = ThreadSafeLazyLoadedIvoryTower.getInstance()
    logger.info { "threadSafeIvoryTower1=$threadSafeIvoryTower1" }
    logger.info { "threadSafeIvoryTower2=$threadSafeIvoryTower2" }

    // enum singleton (Kotlin object)
    val enumIvoryTower1 = EnumIvoryTower
    val enumIvoryTower2 = EnumIvoryTower
    logger.info { "enumIvoryTower1=$enumIvoryTower1" }
    logger.info { "enumIvoryTower2=$enumIvoryTower2" }

    // double-checked locking
    val dcl1 = ThreadSafeDoubleCheckLocking.getInstance()
    logger.info { dcl1.toString() }
    val dcl2 = ThreadSafeDoubleCheckLocking.getInstance()
    logger.info { dcl2.toString() }

    // initialize on demand holder idiom
    val demandHolderIdiom = InitializingOnDemandHolderIdiom.getInstance()
    logger.info { demandHolderIdiom.toString() }
    val demandHolderIdiom2 = InitializingOnDemandHolderIdiom.getInstance()
    logger.info { demandHolderIdiom2.toString() }

    // initialize singleton using Bill Pugh's implementation
    val billPughSingleton = BillPughImplementation.getInstance()
    logger.info { billPughSingleton.toString() }
    val billPughSingleton2 = BillPughImplementation.getInstance()
    logger.info { billPughSingleton2.toString() }
}
