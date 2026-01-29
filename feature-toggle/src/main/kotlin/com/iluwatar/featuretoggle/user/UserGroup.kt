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

// ABOUTME: Manages user groups (free and paid) for the tiered feature toggle example.
// ABOUTME: Provides static-like functions to add users to groups and check paid status.
package com.iluwatar.featuretoggle.user

/**
 * Contains the lists of users of different groups paid and free. Used to demonstrate the tiered
 * example of feature toggle. Allowing certain features to be available to only certain groups of
 * users.
 *
 * @see User
 */
object UserGroup {

    private val freeGroup: MutableList<User> = mutableListOf()
    private val paidGroup: MutableList<User> = mutableListOf()

    /**
     * Add the passed [User] to the free user group list.
     *
     * @param user [User] to be added to the free group
     * @throws IllegalArgumentException when user is already added to the paid group
     * @see User
     */
    @JvmStatic
    fun addUserToFreeGroup(user: User) {
        if (user in paidGroup) {
            throw IllegalArgumentException("User already member of paid group.")
        } else {
            if (user !in freeGroup) {
                freeGroup.add(user)
            }
        }
    }

    /**
     * Add the passed [User] to the paid user group list.
     *
     * @param user [User] to be added to the paid group
     * @throws IllegalArgumentException when the user is already added to the free group
     * @see User
     */
    @JvmStatic
    fun addUserToPaidGroup(user: User) {
        if (user in freeGroup) {
            throw IllegalArgumentException("User already member of free group.")
        } else {
            if (user !in paidGroup) {
                paidGroup.add(user)
            }
        }
    }

    /**
     * Method to take a [User] to determine if the user is in the [paidGroup].
     *
     * @param user [User] to check if they are in the [paidGroup]
     * @return true if the [User] is in [paidGroup]
     */
    @JvmStatic
    fun isPaid(user: User): Boolean = user in paidGroup
}
