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

// ABOUTME: Unit tests for the ContentStore.
// ABOUTME: Verifies that ContentStore correctly handles content actions and ignores menu actions.
package com.iluwatar.flux.store

import com.iluwatar.flux.action.Content
import com.iluwatar.flux.action.ContentAction
import com.iluwatar.flux.action.MenuAction
import com.iluwatar.flux.action.MenuItem
import com.iluwatar.flux.view.View
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/** ContentStoreTest */
class ContentStoreTest {

    @Test
    fun testOnAction() {
        val contentStore = ContentStore()

        val view = mockk<View>(relaxed = true)
        contentStore.registerView(view)

        confirmVerified(view)

        // Content should not react on menu action ...
        contentStore.onAction(MenuAction(MenuItem.PRODUCTS))
        confirmVerified(view)

        // ... but it should react on a content action
        contentStore.onAction(ContentAction(Content.COMPANY))
        verify(exactly = 1) { view.storeChanged(contentStore) }
        confirmVerified(view)
        assertEquals(Content.COMPANY, contentStore.content)
    }
}
