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

// ABOUTME: Unit tests for the Dispatcher singleton.
// ABOUTME: Tests singleton behavior, store registration, and action dispatching.
package com.iluwatar.flux.dispatcher

import com.iluwatar.flux.action.Action
import com.iluwatar.flux.action.ActionType
import com.iluwatar.flux.action.Content
import com.iluwatar.flux.action.ContentAction
import com.iluwatar.flux.action.MenuAction
import com.iluwatar.flux.action.MenuItem
import com.iluwatar.flux.store.Store
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/** DispatcherTest */
class DispatcherTest {

    /**
     * Dispatcher is a singleton with no way to reset its internal state back to the beginning.
     * Replace the instance with a fresh one before each test to make sure test cases have no
     * influence on each other.
     */
    @BeforeEach
    fun setUp() {
        val constructor = Dispatcher::class.java.getDeclaredConstructor()
        constructor.isAccessible = true

        val field = Dispatcher::class.java.getDeclaredField("instance")
        field.isAccessible = true
        field.set(Dispatcher.instance, constructor.newInstance())
    }

    @Test
    fun testGetInstance() {
        assertNotNull(Dispatcher.instance)
        assertSame(Dispatcher.instance, Dispatcher.instance)
    }

    @Test
    fun testMenuItemSelected() {
        val dispatcher = Dispatcher.instance

        val capturedActions = mutableListOf<Action>()
        val store = mockk<Store>(relaxed = true)

        dispatcher.registerStore(store)
        dispatcher.menuItemSelected(MenuItem.HOME)
        dispatcher.menuItemSelected(MenuItem.COMPANY)

        // We expect 4 events, 2 menu selections and 2 content change actions
        val actionSlot = slot<Action>()
        verify(exactly = 4) { store.onAction(capture(capturedActions)) }

        val menuActions = capturedActions
            .filter { it.type == ActionType.MENU_ITEM_SELECTED }
            .map { it as MenuAction }

        val contentActions = capturedActions
            .filter { it.type == ActionType.CONTENT_CHANGED }
            .map { it as ContentAction }

        assertEquals(2, menuActions.size)
        assertEquals(
            1,
            menuActions.map { it.menuItem }.count { it == MenuItem.HOME }
        )
        assertEquals(
            1,
            menuActions.map { it.menuItem }.count { it == MenuItem.COMPANY }
        )

        assertEquals(2, contentActions.size)
        assertEquals(
            1,
            contentActions.map { it.content }.count { it == Content.PRODUCTS }
        )
        assertEquals(
            1,
            contentActions.map { it.content }.count { it == Content.COMPANY }
        )
    }
}
