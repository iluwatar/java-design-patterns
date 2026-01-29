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

package com.iluwatar.strangler

// ABOUTME: Entry point demonstrating the Strangler design pattern for incremental system migration.
// ABOUTME: Shows old, half-migrated, and fully-migrated arithmetic systems side by side.

/**
 * The Strangler pattern is a software design pattern that incrementally migrates a legacy system by
 * gradually replacing specific pieces of functionality with new applications and services. As
 * features from the legacy system are replaced, the new system eventually replaces all of the old
 * system's features, strangling the old system and allowing you to decommission it.
 *
 * This pattern is not only about updating but also enhancement.
 *
 * In this example, [OldArithmetic] indicates old system and its implementation depends on
 * its source ([OldSource]). Now we tend to update system with new techniques and new
 * features. In reality, the system may be too complex, so usually needs gradual migration. [HalfArithmetic]
 * indicates system in the process of migration, its implementation depends on old
 * one ([OldSource]) and under development one ([HalfSource]). The [HalfSource]
 * covers part of [OldSource] and adds new functionality. You can release this version system
 * with new features, which also supports old version system functionalities. After whole migration,
 * the new system ([NewArithmetic]) only depends on new source ([NewSource]).
 */
fun main() {
    val nums = intArrayOf(1, 2, 3, 4, 5)
    // Before migration
    val oldSystem = OldArithmetic(OldSource())
    oldSystem.sum(*nums)
    oldSystem.mul(*nums)
    // In process of migration
    val halfSystem = HalfArithmetic(HalfSource(), OldSource())
    halfSystem.sum(*nums)
    halfSystem.mul(*nums)
    halfSystem.ifHasZero(*nums)
    // After migration
    val newSystem = NewArithmetic(NewSource())
    newSystem.sum(*nums)
    newSystem.mul(*nums)
    newSystem.ifHasZero(*nums)
}
