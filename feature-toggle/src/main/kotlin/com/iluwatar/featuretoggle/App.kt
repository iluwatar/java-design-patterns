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

// ABOUTME: Main application demonstrating the Feature Toggle pattern with two implementations.
// ABOUTME: Shows both properties-based and user-tier-based feature toggle approaches.
package com.iluwatar.featuretoggle

import com.iluwatar.featuretoggle.pattern.propertiesversion.PropertiesFeatureToggleVersion
import com.iluwatar.featuretoggle.pattern.tieredversion.TieredFeatureToggleVersion
import com.iluwatar.featuretoggle.user.User
import com.iluwatar.featuretoggle.user.UserGroup
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.Properties

private val logger = KotlinLogging.logger {}

/**
 * The Feature Toggle pattern allows for complete code executions to be turned on or off with ease.
 * This allows features to be controlled by either dynamic methods just as [User] information
 * or by [Properties]. In the App below there are two examples. Firstly the [Properties]
 * version of the feature toggle, where the enhanced version of the welcome message which is
 * personalised is turned either on or off at instance creation. This method is not as dynamic as
 * the [User] driven version where the feature of the personalised welcome message is
 * dependent on the [UserGroup] the [User] is in. So if the user is a member of the
 * [UserGroup.isPaid] then they get an enhanced version of the welcome message.
 *
 * Note that this pattern can easily introduce code complexity, and if not kept in check can
 * result in redundant unmaintained code within the codebase.
 */

/**
 * Block 1 shows the [PropertiesFeatureToggleVersion] being run with [Properties]
 * setting the feature toggle to enabled.
 *
 * Block 2 shows the [PropertiesFeatureToggleVersion] being run with [Properties]
 * setting the feature toggle to disabled. Notice the difference with the printed welcome message
 * the username is not included.
 *
 * Block 3 shows the [TieredFeatureToggleVersion] being set up with two users on who is on the
 * free level, while the other is on the paid level. When the [Service.getWelcomeMessage] is
 * called with the paid [User] note that the welcome message contains their username, while the
 * same service call with the free tier user is more generic. No username is printed.
 *
 * @see User
 * @see UserGroup
 * @see Service
 * @see PropertiesFeatureToggleVersion
 * @see TieredFeatureToggleVersion
 */
fun main() {
    // Demonstrates the PropertiesFeatureToggleVersion running with properties
    // that set the feature toggle to enabled.

    val properties = Properties()
    properties["enhancedWelcome"] = true
    val service = PropertiesFeatureToggleVersion(properties)
    val welcomeMessage = service.getWelcomeMessage(User("Jamie No Code"))
    logger.info { welcomeMessage }

    // Demonstrates the PropertiesFeatureToggleVersion running with properties
    // that set the feature toggle to disabled. Note the difference in the printed welcome message
    // where the username is not included.

    val turnedOff = Properties()
    turnedOff["enhancedWelcome"] = false
    val turnedOffService = PropertiesFeatureToggleVersion(turnedOff)
    val welcomeMessageTurnedOff = turnedOffService.getWelcomeMessage(User("Jamie No Code"))
    logger.info { welcomeMessageTurnedOff }

    // Demonstrates the TieredFeatureToggleVersion setup with
    // two users: one on the free tier and the other on the paid tier. When the
    // Service#getWelcomeMessage(User) method is called with the paid user, the welcome
    // message includes their username. In contrast, calling the same service with the free tier
    // user results in a more generic welcome message without the username.

    val service2 = TieredFeatureToggleVersion()

    val paidUser = User("Jamie Coder")
    val freeUser = User("Alan Defect")

    UserGroup.addUserToPaidGroup(paidUser)
    UserGroup.addUserToFreeGroup(freeUser)

    val welcomeMessagePaidUser = service2.getWelcomeMessage(paidUser)
    val welcomeMessageFreeUser = service2.getWelcomeMessage(freeUser)
    logger.info { welcomeMessageFreeUser }
    logger.info { welcomeMessagePaidUser }
}
