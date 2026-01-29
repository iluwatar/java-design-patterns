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

import io.github.oshai.kotlinlogging.KotlinLogging

// ABOUTME: Implementation of the unit of work pattern for weapon entities.
// ABOUTME: Tracks weapon changes in a context map and commits all changes to the database in a batch.

private val logger = KotlinLogging.logger {}

/**
 * [ArmsDealer] Weapon repository that supports unit of work for weapons.
 */
class ArmsDealer(
    private val context: MutableMap<String, MutableList<Weapon>>?,
    private val weaponDatabase: WeaponDatabase
) : UnitOfWork<Weapon> {

    override fun registerNew(weapon: Weapon) {
        logger.info { "Registering ${weapon.name} for insert in context." }
        register(weapon, UnitActions.INSERT.actionValue)
    }

    override fun registerModified(weapon: Weapon) {
        logger.info { "Registering ${weapon.name} for modify in context." }
        register(weapon, UnitActions.MODIFY.actionValue)
    }

    override fun registerDeleted(weapon: Weapon) {
        logger.info { "Registering ${weapon.name} for delete in context." }
        register(weapon, UnitActions.DELETE.actionValue)
    }

    private fun register(weapon: Weapon, operation: String) {
        val weaponsToOperate = context?.getOrPut(operation) { mutableListOf() }
        weaponsToOperate?.add(weapon)
    }

    /**
     * All UnitOfWork operations are batched and executed together on commit only.
     */
    override fun commit() {
        if (context.isNullOrEmpty()) {
            return
        }
        logger.info { "Commit started" }
        if (context.containsKey(UnitActions.INSERT.actionValue)) {
            commitInsert()
        }
        if (context.containsKey(UnitActions.MODIFY.actionValue)) {
            commitModify()
        }
        if (context.containsKey(UnitActions.DELETE.actionValue)) {
            commitDelete()
        }
        logger.info { "Commit finished." }
    }

    private fun commitInsert() {
        val weaponsToBeInserted = context?.get(UnitActions.INSERT.actionValue) ?: return
        for (weapon in weaponsToBeInserted) {
            logger.info { "Inserting a new weapon ${weapon.name} to sales rack." }
            weaponDatabase.insert(weapon)
        }
    }

    private fun commitModify() {
        val modifiedWeapons = context?.get(UnitActions.MODIFY.actionValue) ?: return
        for (weapon in modifiedWeapons) {
            logger.info { "Scheduling ${weapon.name} for modification work." }
            weaponDatabase.modify(weapon)
        }
    }

    private fun commitDelete() {
        val deletedWeapons = context?.get(UnitActions.DELETE.actionValue) ?: return
        for (weapon in deletedWeapons) {
            logger.info { "Scrapping ${weapon.name}." }
            weaponDatabase.delete(weapon)
        }
    }
}
