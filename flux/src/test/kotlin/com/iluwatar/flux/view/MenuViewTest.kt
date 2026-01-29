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

// ABOUTME: Unit tests for the MenuView.
// ABOUTME: Verifies that MenuView correctly handles store changes and item clicks.
package com.iluwatar.flux.view

import com.iluwatar.flux.action.Action
import com.iluwatar.flux.action.MenuItem
import com.iluwatar.flux.dispatcher.Dispatcher
import com.iluwatar.flux.store.MenuStore
import com.iluwatar.flux.store.Store
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

/** MenuViewTest */
class MenuViewTest {

    @Test
    fun testStoreChanged() {
        val store = mockk<MenuStore>()
        every { store.selected } returns MenuItem.HOME

        val view = MenuView()
        view.storeChanged(store)

        verify(exactly = 1) { store.selected }
        confirmVerified(store)
    }

    @Test
    fun testItemClicked() {
        val store = mockk<Store>(relaxed = true)
        Dispatcher.instance.registerStore(store)

        val view = MenuView()
        view.itemClicked(MenuItem.PRODUCTS)

        // We should receive a menu click action and a content changed action
        verify(exactly = 2) { store.onAction(any<Action>()) }
    }
}
