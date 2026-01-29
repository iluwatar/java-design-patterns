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

// ABOUTME: Interface for feature toggle services that provide welcome messages.
// ABOUTME: Defines methods to get welcome messages and check if enhanced features are enabled.
package com.iluwatar.featuretoggle.pattern

import com.iluwatar.featuretoggle.user.User

/**
 * Simple interface to allow the calling of the method to generate the welcome message for a given
 * user. While there is a helper method to gather the status of the feature toggle. In some cases
 * there is no need for the [isEnhanced] in
 * [com.iluwatar.featuretoggle.pattern.tieredversion.TieredFeatureToggleVersion] where the toggle is
 * determined by the actual [User].
 *
 * @see com.iluwatar.featuretoggle.pattern.propertiesversion.PropertiesFeatureToggleVersion
 * @see com.iluwatar.featuretoggle.pattern.tieredversion.TieredFeatureToggleVersion
 * @see User
 */
interface Service {

    /**
     * Generates a welcome message for the passed user.
     *
     * @param user the [User] to be used if the message is to be personalised.
     * @return Generated [String] welcome message
     */
    fun getWelcomeMessage(user: User): String

    /**
     * Returns if the welcome message to be displayed will be the enhanced version.
     *
     * @return Boolean `true` if enhanced.
     */
    val isEnhanced: Boolean
}
