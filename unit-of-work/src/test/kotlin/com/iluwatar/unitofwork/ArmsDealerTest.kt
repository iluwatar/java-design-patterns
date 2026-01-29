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
package com.iluwatar.unitofwork

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

// ABOUTME: Unit tests for the ArmsDealer unit of work implementation.
// ABOUTME: Tests registration of entities and commit behavior with mocked database.

/**
 * tests [ArmsDealer]
 */
class ArmsDealerTest {
    private val weapon1 = Weapon(1, "battle ram")
    private val weapon2 = Weapon(1, "wooden lance")

    private val context: MutableMap<String, MutableList<Weapon>> = mutableMapOf()
    private val weaponDatabase: WeaponDatabase = mockk(relaxed = true)
    private val armsDealer = ArmsDealer(context, weaponDatabase)

    @Test
    fun shouldSaveNewStudentWithoutWritingToDb() {
        armsDealer.registerNew(weapon1)
        armsDealer.registerNew(weapon2)

        assertEquals(2, context[UnitActions.INSERT.actionValue]?.size)
        verify(exactly = 0) { weaponDatabase.insert(any()) }
        verify(exactly = 0) { weaponDatabase.modify(any()) }
        verify(exactly = 0) { weaponDatabase.delete(any()) }
    }

    @Test
    fun shouldSaveDeletedStudentWithoutWritingToDb() {
        armsDealer.registerDeleted(weapon1)
        armsDealer.registerDeleted(weapon2)

        assertEquals(2, context[UnitActions.DELETE.actionValue]?.size)
        verify(exactly = 0) { weaponDatabase.insert(any()) }
        verify(exactly = 0) { weaponDatabase.modify(any()) }
        verify(exactly = 0) { weaponDatabase.delete(any()) }
    }

    @Test
    fun shouldSaveModifiedStudentWithoutWritingToDb() {
        armsDealer.registerModified(weapon1)
        armsDealer.registerModified(weapon2)

        assertEquals(2, context[UnitActions.MODIFY.actionValue]?.size)
        verify(exactly = 0) { weaponDatabase.insert(any()) }
        verify(exactly = 0) { weaponDatabase.modify(any()) }
        verify(exactly = 0) { weaponDatabase.delete(any()) }
    }

    @Test
    fun shouldSaveAllLocalChangesToDb() {
        context[UnitActions.INSERT.actionValue] = mutableListOf(weapon1)
        context[UnitActions.MODIFY.actionValue] = mutableListOf(weapon1)
        context[UnitActions.DELETE.actionValue] = mutableListOf(weapon1)

        armsDealer.commit()

        verify(exactly = 1) { weaponDatabase.insert(weapon1) }
        verify(exactly = 1) { weaponDatabase.modify(weapon1) }
        verify(exactly = 1) { weaponDatabase.delete(weapon1) }
    }

    @Test
    fun shouldNotWriteToDbIfContextIsNull() {
        val weaponRepository = ArmsDealer(null, weaponDatabase)

        weaponRepository.commit()

        verify(exactly = 0) { weaponDatabase.insert(any()) }
        verify(exactly = 0) { weaponDatabase.modify(any()) }
        verify(exactly = 0) { weaponDatabase.delete(any()) }
    }

    @Test
    fun shouldNotWriteToDbIfNothingToCommit() {
        val weaponRepository = ArmsDealer(mutableMapOf(), weaponDatabase)

        weaponRepository.commit()

        verify(exactly = 0) { weaponDatabase.insert(any()) }
        verify(exactly = 0) { weaponDatabase.modify(any()) }
        verify(exactly = 0) { weaponDatabase.delete(any()) }
    }

    @Test
    fun shouldNotInsertToDbIfNoRegisteredStudentsToBeCommitted() {
        context[UnitActions.MODIFY.actionValue] = mutableListOf(weapon1)
        context[UnitActions.DELETE.actionValue] = mutableListOf(weapon1)

        armsDealer.commit()

        verify(exactly = 0) { weaponDatabase.insert(weapon1) }
    }

    @Test
    fun shouldNotModifyToDbIfNotRegisteredStudentsToBeCommitted() {
        context[UnitActions.INSERT.actionValue] = mutableListOf(weapon1)
        context[UnitActions.DELETE.actionValue] = mutableListOf(weapon1)

        armsDealer.commit()

        verify(exactly = 0) { weaponDatabase.modify(weapon1) }
    }

    @Test
    fun shouldNotDeleteFromDbIfNotRegisteredStudentsToBeCommitted() {
        context[UnitActions.INSERT.actionValue] = mutableListOf(weapon1)
        context[UnitActions.MODIFY.actionValue] = mutableListOf(weapon1)

        armsDealer.commit()

        verify(exactly = 0) { weaponDatabase.delete(weapon1) }
    }
}
