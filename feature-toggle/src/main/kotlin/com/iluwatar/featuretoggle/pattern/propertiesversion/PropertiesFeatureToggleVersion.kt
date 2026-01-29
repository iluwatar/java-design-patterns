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

// ABOUTME: Properties-based feature toggle that enables/disables features at instantiation time.
// ABOUTME: Reads a boolean property to determine if enhanced welcome messages are shown.
package com.iluwatar.featuretoggle.pattern.propertiesversion

import com.iluwatar.featuretoggle.pattern.Service
import com.iluwatar.featuretoggle.user.User
import java.util.Properties

/**
 * This example of the Feature Toggle pattern is less dynamic version than
 * [com.iluwatar.featuretoggle.pattern.tieredversion.TieredFeatureToggleVersion] where the feature is
 * turned on or off at the time of creation of the service. This example uses simple Java [Properties]
 * however it could as easily be done with an external configuration file loaded by Spring and so on.
 * A good example of when to use this version of the feature toggle is when new features are being
 * developed. So you could have a configuration property boolean named development or some sort of
 * system environment variable.
 *
 * @see Service
 * @see com.iluwatar.featuretoggle.pattern.tieredversion.TieredFeatureToggleVersion
 * @see User
 */
class PropertiesFeatureToggleVersion(properties: Properties?) : Service {

    /**
     * True if the welcome message to be returned is the enhanced version or not. For this service it
     * will see the value of the boolean that was set in the constructor.
     */
    override val isEnhanced: Boolean

    init {
        if (properties == null) {
            throw IllegalArgumentException("No Properties Provided.")
        } else {
            try {
                isEnhanced = properties["enhancedWelcome"] as Boolean
            } catch (e: Exception) {
                throw IllegalArgumentException("Invalid Enhancement Settings Provided.")
            }
        }
    }

    /**
     * Generate a welcome message based on the user being passed and the status of the feature toggle.
     * If the enhanced version is enabled, then the message will be personalised with the name of the
     * passed [User]. However, if disabled then a generic version of the message is returned.
     *
     * @param user the [User] to be displayed in the message if the enhanced version is enabled
     *     see [isEnhanced]. If the enhanced version is enabled, then the message will be
     *     personalised with the name of the passed [User]. However, if disabled then a generic
     *     version of the message is returned.
     * @return Resulting welcome message.
     * @see User
     */
    override fun getWelcomeMessage(user: User): String {
        return if (isEnhanced) {
            "Welcome $user. You're using the enhanced welcome message."
        } else {
            "Welcome to the application."
        }
    }
}
