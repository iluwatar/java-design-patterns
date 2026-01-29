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

// ABOUTME: Concrete view for displaying the menu in the Flux pattern.
// ABOUTME: Renders menu items, highlights selection, and dispatches menu click actions.
package com.iluwatar.flux.view

import com.iluwatar.flux.action.MenuItem
import com.iluwatar.flux.dispatcher.Dispatcher
import com.iluwatar.flux.store.MenuStore
import com.iluwatar.flux.store.Store
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/** MenuView is a concrete view. */
class MenuView : View {

    private var selected: MenuItem = MenuItem.HOME

    override fun storeChanged(store: Store) {
        val menuStore = store as MenuStore
        selected = menuStore.selected
        render()
    }

    override fun render() {
        for (item in MenuItem.entries) {
            if (selected == item) {
                logger.info { "* $item" }
            } else {
                logger.info { item.toString() }
            }
        }
    }

    fun itemClicked(item: MenuItem) {
        Dispatcher.instance.menuItemSelected(item)
    }
}
